package org.ums.accounts.resource.definitions.voucher.number.control;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
@Component
public class VoucherNumberControlBuilder implements Builder<VoucherNumberControl, MutableVoucherNumberControl> {
  @Override
  public void build(JsonObjectBuilder pBuilder, VoucherNumberControl pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableVoucherNumberControl pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
