package org.ums.accounts.resource.debtor.ledger;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.DebtorLedger;
import org.ums.domain.model.mutable.accounts.MutableDebtorLedger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 14-Mar-18.
 */
@Component
public class DebtorLedgerBuilder implements Builder<DebtorLedger, MutableDebtorLedger> {
  @Override
  public void build(JsonObjectBuilder pBuilder, DebtorLedger pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    new NotImplementedException();
  }

  @Override
  public void build(MutableDebtorLedger pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    new NotImplementedException();
  }

  public void build(MutableDebtorLedger pMutableDebtorLedger, JsonObject pJsonObject) {
    if(pJsonObject.containsKey("customerId"))
      pMutableDebtorLedger.setCustomerCode(pJsonObject.getString("customerId"));

  }

}
