package org.ums.accounts.resource.general.ledger.transactions.contra.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.logs.PostLog;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 22-Feb-18.
 */
public class MutableContraVoucherResource {
  @Autowired
  protected ContraVoucherResourceHelper mHelper;
  @Autowired
  protected AccountTransactionManager mAccountTransactionManager;

  @POST
  @Path("/save")
  @PostLog(message = "Saving contra voucher")
  public List<AccountTransaction> save(@Context HttpServletRequest httpServletRequest,
      List<PersistentAccountTransaction> persistentAccountTransactionList) throws Exception {
    return mHelper.save(persistentAccountTransactionList);
  }

  @PUT
  @Path("/delete")
  public Response delete(final String pTransactionId) {
    AccountTransaction pMutableAccountTransaction = mAccountTransactionManager.get(Long.parseLong(pTransactionId));
    return mHelper.delete(pMutableAccountTransaction);
  }

  @POST
  @Path("/post")
  @PostLog(message = "Posting into Contra Voucher")
  public List<AccountTransaction> post(@Context HttpServletRequest httpServletRequest,
      List<PersistentAccountTransaction> accountTransactionList) throws Exception {
    return mHelper.postTransactions(accountTransactionList);
  }
}
