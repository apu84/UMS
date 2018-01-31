package org.ums.accounts.resource.definitions.general.ledger.transactions;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Transaction;
import org.ums.domain.model.mutable.accounts.MutableTransaction;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class TransactionBuilder implements Builder<Transaction, MutableTransaction> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Transaction pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableTransaction pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
