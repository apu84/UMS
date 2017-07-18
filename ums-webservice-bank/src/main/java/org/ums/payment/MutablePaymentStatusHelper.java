package org.ums.payment;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.fee.accounts.MutablePaymentStatus;
import org.ums.fee.accounts.PaymentStatus;
import org.ums.fee.accounts.PersistentPaymentStatus;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
public class MutablePaymentStatusHelper extends PaymentStatusHelper {
  @Autowired
  PaymentStatusBuilder mMutablePaymentStatusBuilder;

  @Override
  protected Builder<PaymentStatus, MutablePaymentStatus> getBuilder() {
    return mMutablePaymentStatusBuilder;
  }

  @Transactional
  public Response updatePaymentStatus(JsonObject pJsonObject, PaymentStatus.Status pStatus) {
    Validate.notEmpty(pJsonObject);
    Validate.notEmpty(pJsonObject.getJsonArray("entries"));
    JsonArray entries = pJsonObject.getJsonArray("entries");
    List<MutablePaymentStatus> paymentStatusList = new ArrayList<>();
    for(JsonValue entry : entries) {
      MutablePaymentStatus paymentStatus = new PersistentPaymentStatus();
      getBuilder().build(paymentStatus, (JsonObject) entry, null);
      PaymentStatus latestPayment = mPaymentStatusManager.get(paymentStatus.getId());
      Validate.isTrue(paymentStatus.getLastModified().equals(latestPayment.getLastModified()));
      Validate.isTrue(latestPayment.getStatus() != PaymentStatus.Status.VERIFIED
          && latestPayment.getStatus() != PaymentStatus.Status.REJECTED);
      Validate.isTrue(latestPayment.getStatus() != PaymentStatus.Status.RECEIVED);
      paymentStatus.setStatus(pStatus);
      paymentStatus.setTransactionId(latestPayment.getTransactionId());
      paymentStatusList.add(paymentStatus);
    }
    mPaymentStatusManager.update(paymentStatusList);
    return Response.ok().build();
  }
}
