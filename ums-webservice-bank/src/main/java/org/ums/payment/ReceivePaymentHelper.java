package org.ums.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

public class ReceivePaymentHelper extends ResourceHelper<StudentPayment, MutableStudentPayment, Long> {
  @Autowired
  ReceivePaymentBuilder mBuilder;
  @Autowired
  StudentPaymentManager mStudentPaymentManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  public Response receivePayment(String pStudentId, JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    List<MutableStudentPayment> payments = new ArrayList<>();
    List<StudentPayment> latestPayments = new ArrayList<>();
    LocalCache cache = new LocalCache();

    entries.forEach((entry) -> {
      MutableStudentPayment payment = new PersistentStudentPayment();
      getBuilder().build(payment, (JsonObject) entry, cache);
      payments.add(payment);

      StudentPayment latestPayment = mStudentPaymentManager.get(payment.getId());
      latestPayments.add(latestPayment);
    });

    validatePayment(pStudentId, latestPayments, payments);
    mStudentPaymentManager.update(payments);

    return Response.ok().build();
  }

  private void validatePayment(String pStudentId, List<StudentPayment> latestPayments, List<MutableStudentPayment> updates) {
    Validate.isTrue(isValidUpdateOfEntities(latestPayments, updates));

    latestPayments.forEach((latestPayment) -> {
      List<StudentPayment> matchedPayments =
          updates.stream().filter((update) -> update.getId().longValue() == latestPayment.getId().longValue()
              && pStudentId.equalsIgnoreCase(latestPayment.getStudentId())).collect(Collectors.toList());
      Validate.isTrue(matchedPayments.size() == 1);
    });

    Validate.isTrue(latestPayments.stream().allMatch((latestPayment) -> latestPayment.getTransactionValidTill().after(new Date())));
    Validate.isTrue(latestPayments.stream().allMatch((latestPayment) -> latestPayment.getStatus() == StudentPayment.Status.APPLIED));

    Map<String, List<StudentPayment>> paymentMap =
        latestPayments.stream().collect(Collectors.groupingBy(StudentPayment::getTransactionId));
    Validate.isTrue(paymentMap.keySet().size() == 1);

    List<StudentPayment> desiredTransactionPayments = mStudentPaymentManager
        .getTransactionDetails(pStudentId, paymentMap.keySet().iterator().next());

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
