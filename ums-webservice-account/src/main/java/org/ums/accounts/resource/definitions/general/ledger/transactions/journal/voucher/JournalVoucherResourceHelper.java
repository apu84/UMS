package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.general.ledger.transactions.TransactionBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.Transaction;
import org.ums.domain.model.mutable.accounts.MutableTransaction;
import org.ums.manager.accounts.TransactionManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class JournalVoucherResourceHelper extends ResourceHelper<Transaction, MutableTransaction, Long> {
  @Autowired
  private TransactionManager mTransactionManager;
  @Autowired
  private TransactionBuilder mTransactionBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected TransactionManager getContentManager() {
    return mTransactionManager;
  }

  @Override
  protected Builder<Transaction, MutableTransaction> getBuilder() {
    return mTransactionBuilder;
  }

  @Override
  protected String getETag(Transaction pReadonly) {
    return null;
  }
}
