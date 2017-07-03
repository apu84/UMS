package org.ums.payment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.accounts.MutablePaymentStatus;
import org.ums.fee.accounts.PaymentStatus;
import org.ums.formatter.DateFormat;

@Component
public class PaymentStatusBuilder implements Builder<PaymentStatus, MutablePaymentStatus> {
  @Autowired
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, PaymentStatus pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("transactionId", pReadOnly.getTransactionId());
    pBuilder.add("account", pReadOnly.getAccount());
    pBuilder.add("methodOfPayment", pReadOnly.getMethodOfPayment().getLabel());
    pBuilder.add("paymentCompleted", pReadOnly.isPaymentComplete());
    pBuilder.add("receivedOn", mDateFormat.format(pReadOnly.getReceivedOn()));
    pBuilder.add("completedOn", pReadOnly.getCompletedOn() != null ? mDateFormat.format(pReadOnly.getCompletedOn())
        : "");
    pBuilder.add("paymentDetails",
        StringUtils.isEmpty(pReadOnly.getPaymentDetails()) ? "" : pReadOnly.getPaymentDetails());
    pBuilder.add("amount", pReadOnly.getAmount());
    pBuilder.add("receiptNo", pReadOnly.getReceiptNo());
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("lastModified", pReadOnly.getLastModified());
  }

  @Override
  public void build(MutablePaymentStatus pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    Validate.notNull(pJsonObject.getString("id"));
    Validate.notNull(pJsonObject.getString("lastModified"));
    Validate.notNull(pJsonObject.getString("completedOn"));
    pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    pMutable.setLastModified(pJsonObject.getString("lastModified"));
    pMutable.setCompletedOn(mDateFormat.parse(pJsonObject.getString("completedOn")));
  }
}
