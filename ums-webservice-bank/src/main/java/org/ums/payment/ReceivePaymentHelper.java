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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  /**
   * Transaction always needs to be in a public method
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
    BigDecimal amount = new BigDecimal(0);

    for(JsonValue entry : entries) {
      MutableStudentPayment payment = new PersistentStudentPayment();
      getBuilder().build(payment, (JsonObject) entry, cache);
      payment.setStudentId(pStudentId);
      StudentPayment latestPayment = mStudentPaymentManager.get(payment.getId());
      payment.setFeeCategoryId(latestPayment.getFeeCategoryId());
      payment.setSemesterId(latestPayment.getSemesterId());
      payment.setTransactionId(latestPayment.getTransactionId());
      payments.add(payment);
      latestPayments.add(latestPayment);
      amount = amount.add(latestPayment.getAmount());
    }

    validatePayment(pStudentId, latestPayments, payments);
    mStudentPaymentManager.update(payments);

    Validate.notNull(pJsonObject.get("paymentMethod"));
    String paymentDetails = null;
    if(pJsonObject.containsKey("paymentDetails")) {
      paymentDetails = pJsonObject.getString("paymentDetails");
    }
    // Taking into consideration only one of payment entry, as this would be a part of same
    // transaction
    updatePaymentStatus(latestPayments.get(0), pJsonObject.getInt("paymentMethod"), paymentDetails, pStudentId, amount);
    return Response.ok().build();
  }

  private void updatePaymentStatus(StudentPayment pStudentPayment, int pPaymentMethod, String pPaymentDetails,
      String pStudentId, BigDecimal pAmount) {
    int faculty = mStudentManager.get(pStudentId).getProgram().getFacultyId();
    List<PaymentAccountsMapping> mappings = mPaymentAccountsMappingManager.getAll().stream()
        .filter((mapping) -> mapping.getFeeTypeId().intValue() == pStudentPayment.getFeeTypeId()
            && mapping.getFacultyId() == faculty)
        .collect(Collectors.toList());
    if(mappings.size() > 0) {
      MutablePaymentStatus paymentStatus = new PersistentPaymentStatus();
      paymentStatus.setAccount(mappings.get(0).getAccount());
      paymentStatus.setTransactionId(pStudentPayment.getTransactionId());
      PaymentStatus.PaymentMethod paymentMethod = PaymentStatus.PaymentMethod.get(pPaymentMethod);
      paymentStatus.setMethodOfPayment(paymentMethod);
      paymentStatus.setReceivedOn(new Date());
      if(paymentMethod == PaymentStatus.PaymentMethod.CASH || paymentMethod == PaymentStatus.PaymentMethod.PAYORDER) {
        paymentStatus.setPaymentComplete(true);
        paymentStatus.setCompletedOn(new Date());
      }
      paymentStatus.setPaymentDetails(pPaymentDetails);
      paymentStatus.setAmount(pAmount);
      paymentStatus.create();
    }
  }

  JsonObject getStudentPayments(String pStudentId, UriInfo pUriInfo) throws Exception {
    Validate.notNull(pStudentId);
    List<StudentPayment> payments = mStudentPaymentManager.getPayments(pStudentId).stream()
        .filter((payment) -> payment.getStatus() == StudentPayment.Status.APPLIED).collect(Collectors.toList());
    Map<Integer, List<StudentPayment>> feeTypePaymentMap =
        payments.stream().collect(Collectors.groupingBy(StudentPayment::getFeeTypeId));

    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    for(Integer feeTypeId : feeTypePaymentMap.keySet()) {
      FeeType feeType = mFeeTypeManager.get(feeTypeId);
      JsonObject typeWiseJson = buildJsonResponse(feeTypePaymentMap.get(feeTypeId), pUriInfo);
      objectBuilder.add(feeType.getDescription(), typeWiseJson);
    }
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
    Validate.isTrue(paymentMap.keySet().size() == 1);

    List<StudentPayment> desiredTransactionPayments =
        mStudentPaymentManager.getTransactionDetails(pStudentId, paymentMap.keySet().iterator().next());

    desiredTransactionPayments.forEach((latestPayment) -> {
      List<StudentPayment> matchedPayments =
          updates.stream().filter((update) -> update.getId().longValue() == latestPayment.getId().longValue()
              && update.getStudentId().equalsIgnoreCase(latestPayment.getStudentId())).collect(Collectors.toList());
      Validate.isTrue(matchedPayments.size() == 1);
    });
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
