package org.ums.accounts.resource.general.ledger.transactions.receipt.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.logs.PostLog;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Mar-18.
 */
public class MutableReceiptVoucherResource {
  @Autowired
  protected ReceiptVoucherResourceHelper mHelper;

  @POST
  @Path("/save")
  @PostLog(message = "Saving Receipt Voucher")
  public List<AccountTransaction> save(@Context HttpServletRequest httpServletRequest,
      List<PersistentAccountTransaction> persistentAccountTransactionList) throws Exception {
    return mHelper.save(persistentAccountTransactionList);
  }

  @POST
  @Path("/post")
  @PostLog(message = "Posting into receipt voucher")
  public List<AccountTransaction> post(@Context HttpServletRequest httpServletRequest,
      List<PersistentAccountTransaction> persistentAccountTransactionList) throws Exception {
    return mHelper.postTransactions(persistentAccountTransactionList);
  }
}
