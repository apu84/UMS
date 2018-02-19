package org.ums.accounts.resource.definitions.account;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.mutable.accounts.MutableReceipt;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 12-Feb-18.
 */
@Component
public class ReceiptBuilder implements Builder<Receipt, MutableReceipt> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Receipt pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  public void build(JsonObjectBuilder pJsonObjectBuilder, Receipt pReceipt) {
    pJsonObjectBuilder.add("id", pReceipt.getId().toString());
    pJsonObjectBuilder.add("name", pReceipt.getName());
    pJsonObjectBuilder.add("shortName", pReceipt.getShortName());
  }

  @Override
  public void build(MutableReceipt pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }

  public void build(MutableReceipt pMutableReceipt, JsonObject pJsonObject) {
    if(pJsonObject.containsKey("id"))
      pMutableReceipt.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("name"))
      pMutableReceipt.setName(pJsonObject.getString("name"));
    if(pJsonObject.containsKey("shortName"))
      pMutableReceipt.setShortName(pJsonObject.getString("shortName"));
  }
}
