package org.ums.accounts.resource.creditor.ledger;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.CreditorLedger;
import org.ums.domain.model.mutable.accounts.MutableCreditorLedger;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 13-Mar-18.
 */
@Component
public class CreditorLedgerBuilder implements Builder<CreditorLedger, MutableCreditorLedger> {
  @Override
  public void build(JsonObjectBuilder pBuilder, CreditorLedger pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    new NotImplementedException();
  }

  @Override
  public void build(MutableCreditorLedger pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    new NotImplementedException();
  }
}
