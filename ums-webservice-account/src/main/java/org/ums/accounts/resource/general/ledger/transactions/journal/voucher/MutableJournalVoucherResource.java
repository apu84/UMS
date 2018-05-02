package org.ums.accounts.resource.general.ledger.transactions.journal.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.logs.GetLog;
import org.ums.logs.PostLog;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class MutableJournalVoucherResource {
  @Autowired
  protected JournalVoucherResourceHelper mJournalVoucherResourceHelper;
  @Autowired
  protected AccountTransactionManager mAccountTransactionManager;

  @POST
  @Path("/save")
  @PostLog(message = "Saving Journal Voucher")
  public List<AccountTransaction> save(@Context HttpServletRequest httpServletRequest,
      List<PersistentAccountTransaction> persistentAccountTransactionList) throws Exception {
    return mJournalVoucherResourceHelper.save(persistentAccountTransactionList);
  }

  @PUT
  @Path("/delete")
  public Response delete(final String pTransactionId) {
    AccountTransaction pMutableAccountTransaction = mAccountTransactionManager.get(Long.parseLong(pTransactionId));
    return mJournalVoucherResourceHelper.delete(pMutableAccountTransaction);
  }

  @POST
  @Path("/post")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @GetLog(message = "Posting")
  public List<AccountTransaction> post(@Context HttpServletRequest pHttpServletRequest,
      List<PersistentAccountTransaction> accountTransactionList) throws Exception {
    return mJournalVoucherResourceHelper.postTransactions(accountTransactionList);
  }

}
