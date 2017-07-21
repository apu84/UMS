package org.ums.payment;

import javax.json.JsonObject;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.StudentPayment;

@Component
class ReceivePaymentBuilder extends StudentPaymentBuilder {
  @Override
  public void build(MutableStudentPayment pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    Validate.notEmpty(pJsonObject.getString("id"));
    pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    pMutable.setLastModified(pJsonObject.getString("lastModified"));
  }
}
