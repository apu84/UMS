package org.ums.accounts.resource.definitions.voucher;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableVoucher;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 12-Feb-18.
 */
@Component
public class VoucherBuilder implements Builder<Voucher, MutableVoucher> {

  @Override
  public void build(JsonObjectBuilder pBuilder, Voucher pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  public void build(JsonObjectBuilder pBuilder, Voucher pVoucher) {
    pBuilder.add("id", pVoucher.getId().toString());
    pBuilder.add("name", pVoucher.getName());
    pBuilder.add("shortName", pVoucher.getShortName());
  }

  @Override
  public void build(MutableVoucher pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }

  public void buil(MutableVoucher pMutableVoucher, JsonObject pJsonObject) {
    if(pJsonObject.containsKey("id"))
      pMutableVoucher.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("name"))
      pMutableVoucher.setName(pJsonObject.getString("name"));
    if(pJsonObject.containsKey("shortName"))
      pMutableVoucher.setShortName(pJsonObject.getString("shortName"));
  }
}
