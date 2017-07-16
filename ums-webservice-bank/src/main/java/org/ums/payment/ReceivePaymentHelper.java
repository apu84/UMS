package org.ums.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.FeeType;
import org.ums.fee.FeeTypeManager;
import org.ums.fee.accounts.*;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.manager.ContentManager;
import org.ums.manager.StudentManager;
import org.ums.resource.ResourceHelper;

@Component
public class ReceivePaymentHelper extends ResourceHelper<StudentPayment, MutableStudentPayment, Long> {
  @Autowired
  private ReceivePaymentBuilder mBuilder;
  @Autowired
  private StudentPaymentManager mStudentPaymentManager;
  @Autowired
  private FeeTypeManager mFeeTypeManager;
  @Autowired
  private PaymentAccountsMappingManager mPaymentAccountsMappingManager;
  @Autowired
  private StudentManager mStudentManager;
  @Autowired
  private PaymentStatusManager mPaymentStatusManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  /**
   * Transaction always needs to be in a public method
   * 
   * @param pStudentId
   * @param pJsonObject
   * @return
   * @throws Exception
   */
  @Transactional
  public Response receivePayment(String pStudentId, JsonObject pJsonObject) {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    List<MutableStudentPayment> payments = new ArrayList<>();
    List<StudentPayment> latestPayments = new ArrayList<>();
    LocalCache cache = new LocalCache();

    Validate.notNull(pJsonObject.get("methodOfPayment"));
    int mop = Integer.parseInt(pJsonObject.getString("methodOfPayment"));
    Validate.notNull(pJsonObject.get("receiptNo"));
    String receiptNo = pJsonObject.getString("receiptNo");
    String paymentDetails = null;
    if(pJsonObject.containsKey("paymentDetails")) {
      paymentDetails = pJsonObject.getString("paymentDetails");
    }

    for(JsonValue entry : entries) {
      MutableStudentPayment payment = new PersistentStudentPayment();
      getBuilder().build(payment, (JsonObject) entry, cache);
      payment.setStudentId(pStudentId);
      StudentPayment latestPayment = mStudentPaymentManager.get(payment.getId());
      payment.setFeeCategoryId(latestPayment.getFeeCategoryId());
      payment.setSemesterId(latestPayment.getSemesterId());
      payment.setTransactionId(latestPayment.getTransactionId());
      if(mop == PaymentStatus.PaymentMethod.CASH.getId()) {
        payment.setStatus(StudentPayment.Status.VERIFIED);
      }
      else {
        payment.setStatus(StudentPayment.Status.RECEIVED);
      }
      payments.add(payment);
      latestPayments.add(latestPayment);
    }

    validatePayment(pStudentId, latestPayments, payments);
    mStudentPaymentManager.update(payments);
    updatePaymentStatus(latestPayments, mop, receiptNo, paymentDetails, pStudentId);
    return Response.ok().build();
  }

  private void updatePaymentStatus(List<StudentPayment> pStudentPayments, int pPaymentMethod, String receiptNo,
      String pPaymentDetails, String pStudentId) {
    int faculty = mStudentManager.get(pStudentId).getProgram().getFacultyId();
    Map<Integer, List<StudentPayment>> feeTypePaymentMap =
        pStudentPayments.stream().collect(Collectors.groupingBy(StudentPayment::getFeeTypeId));

    for(Integer feeTypeId : feeTypePaymentMap.keySet()) {
      List<PaymentAccountsMapping> mappings = mPaymentAccountsMappingManager.getAll().stream()
          .filter((mapping) -> mapping.getFeeTypeId().intValue() == feeTypeId && mapping.getFacultyId() == faculty)
          .collect(Collectors.toList());
      if(mappings.size() > 0) {
        Map<String, List<StudentPayment>> transactionPaymentMap =
            feeTypePaymentMap.get(feeTypeId).stream().collect(Collectors.groupingBy(StudentPayment::getTransactionId));
        for(String transactionId : transactionPaymentMap.keySet()) {
          MutablePaymentStatus paymentStatus = new PersistentPaymentStatus();
          paymentStatus.setAccount(mappings.get(0).getAccount());
          paymentStatus.setTransactionId(transactionId);
          PaymentStatus.PaymentMethod paymentMethod = PaymentStatus.PaymentMethod.get(pPaymentMethod);
          paymentStatus.setMethodOfPayment(paymentMethod);
          paymentStatus.setReceivedOn(new Date());
          if(paymentMethod == PaymentStatus.PaymentMethod.CASH) {
            paymentStatus.setPaymentComplete(true);
            paymentStatus.setCompletedOn(new Date());
          }
          paymentStatus.setPaymentDetails(pPaymentDetails);
          List<StudentPayment> payments = transactionPaymentMap.get(transactionId);
          paymentStatus.setAmount(payments.stream().map(StudentPayment::getAmount)
              .reduce(BigDecimal.ZERO, BigDecimal::add));
          paymentStatus.setReceiptNo(receiptNo);
          paymentStatus.create();
        }
      }
    }
  }

  JsonObject getStudentPayments(String pStudentId, UriInfo pUriInfo) throws Exception {
    Validate.notNull(pStudentId);
    List<StudentPayment> payments = mStudentPaymentManager.getPayments(pStudentId).stream()
        .filter((payment) -> payment.getStatus() == StudentPayment.Status.APPLIED)
        .collect(Collectors.toList());
    Map<Integer, List<StudentPayment>> feeTypePaymentMap =
        payments.stream().collect(Collectors.groupingBy(StudentPayment::getFeeTypeId));

    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    for(Integer feeTypeId : feeTypePaymentMap.keySet()) {
      JsonObjectBuilder feeTypeObject = Json.createObjectBuilder();
      FeeType feeType = mFeeTypeManager.get(feeTypeId);
      feeTypeObject.add("feeType", feeType.getId());
      feeTypeObject.add("feeTypeName", feeType.getName());
      feeTypeObject.add("feeTypeDescription", feeType.getDescription());

      JsonArrayBuilder transactionsArrayBuilder = Json.createArrayBuilder();
      Map<String, List<StudentPayment>> transactionPaymentMap =
          feeTypePaymentMap.get(feeTypeId).stream().collect(Collectors.groupingBy(StudentPayment::getTransactionId));
      for(String transactionId : transactionPaymentMap.keySet()) {
        JsonObjectBuilder transactionObjectBuilder = Json.createObjectBuilder();
        JsonObject transactionWiseJson = buildJsonResponse(transactionPaymentMap.get(transactionId), pUriInfo);
        transactionObjectBuilder.add("id", transactionId);
        transactionObjectBuilder.add("entries", transactionWiseJson.getJsonArray("entries"));
        transactionsArrayBuilder.add(transactionObjectBuilder);
      }
      feeTypeObject.add("transactions", transactionsArrayBuilder);
      arrayBuilder.add(feeTypeObject);
    }
    objectBuilder.add("entries", arrayBuilder);
    return objectBuilder.build();
  }

  JsonObject getStudentPayments(String pStudentId, Integer pFeeType, UriInfo pUriInfo) throws Exception {
    Validate.notNull(pStudentId);
    Validate.notNull(pFeeType);
    return buildJsonResponse(
        mStudentPaymentManager.getPayments(pStudentId, mFeeTypeManager.get(pFeeType)).stream()
            .filter((payment) -> payment.getStatus() == StudentPayment.Status.APPLIED).collect(Collectors.toList()),
        pUriInfo);
  }

  private void validatePayment(String pStudentId, List<StudentPayment> latestPayments,
      List<MutableStudentPayment> updates) {
    Validate.isTrue(isValidUpdateOfEntities(latestPayments, updates));

    latestPayments.forEach((latestPayment) -> {
      List<StudentPayment> matchedPayments =
          updates.stream().filter((update) -> update.getId().longValue() == latestPayment.getId().longValue()
              && pStudentId.equalsIgnoreCase(latestPayment.getStudentId())).collect(Collectors.toList());
      Validate.isTrue(matchedPayments.size() == 1);
    });

    Validate.isTrue(
        latestPayments.stream().allMatch((latestPayment) -> latestPayment.getTransactionValidTill().after(new Date())));
    Validate.isTrue(latestPayments.stream()
        .allMatch((latestPayment) -> latestPayment.getStatus() == StudentPayment.Status.APPLIED));

    Map<String, List<StudentPayment>> paymentMap =
        latestPayments.stream().collect(Collectors.groupingBy(StudentPayment::getTransactionId));
    // Validate.isTrue(paymentMap.keySet().size() == 1);

    List<StudentPayment> desiredTransactionPayments =
        mStudentPaymentManager.getTransactionDetails(pStudentId, paymentMap.keySet().iterator().next());

    desiredTransactionPayments.forEach((latestPayment) -> {
      List<StudentPayment> matchedPayments =
          updates.stream().filter((update) -> update.getId().longValue() == latestPayment.getId().longValue()
              && update.getStudentId().equalsIgnoreCase(latestPayment.getStudentId())).collect(Collectors.toList());
      Validate.isTrue(matchedPayments.size() == 1);
    });

    Validate.isTrue(mPaymentStatusManager.getByTransactionId(latestPayments.get(0).getTransactionId()).size() == 0,
        "Transaction is already received");
  }

  @Override
  protected ContentManager<StudentPayment, MutableStudentPayment, Long> getContentManager() {
    return mStudentPaymentManager;
  }

  @Override
  protected Builder<StudentPayment, MutableStudentPayment> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(StudentPayment pReadonly) {
    return pReadonly.getLastModified();
  }

}
