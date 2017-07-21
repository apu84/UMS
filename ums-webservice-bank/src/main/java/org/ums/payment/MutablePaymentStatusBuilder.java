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

@Component("MutablePaymentStatusBuilder")
public class MutablePaymentStatusBuilder extends PaymentStatusBuilder {
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
