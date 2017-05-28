package org.ums.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

@Component
public class ReceivePaymentHelper extends ResourceHelper<StudentPayment, MutableStudentPayment, Long> {
  @Autowired
  private ReceivePaymentBuilder mBuilder;
  @Autowired
  private StudentPaymentManager mStudentPaymentManager;
  @Autowired
  private FeeTypeManager mFeeTypeManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Transactional
  Response receivePayment(String pStudentId, JsonObject pJsonObject) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    List<MutableStudentPayment> payments = new ArrayList<>();
    List<StudentPayment> latestPayments = new ArrayList<>();
    LocalCache cache = new LocalCache();

    entries.forEach((entry) -> {
      MutableStudentPayment payment = new PersistentStudentPayment();
      getBuilder().build(payment, (JsonObject) entry, cache);
      payment.setStudentId(pStudentId);
      StudentPayment latestPayment = mStudentPaymentManager.get(payment.getId());
      payment.setFeeCategoryId(latestPayment.getFeeCategoryId());
      payment.setSemesterId(latestPayment.getSemesterId());
      payment.setTransactionId(latestPayment.getTransactionId());
      payments.add(payment);
      latestPayments.add(latestPayment);
    });

    validatePayment(pStudentId, latestPayments, payments);
    mStudentPaymentManager.update(payments);

    return Response.ok().build();
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
