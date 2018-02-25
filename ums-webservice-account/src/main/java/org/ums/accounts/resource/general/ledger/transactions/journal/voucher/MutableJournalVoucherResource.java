package org.ums.accounts.resource.general.ledger.transactions.journal.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.accounts.tracer.AccountLogMessage;
import org.ums.domain.model.immutable.accounts.AccountTransaction;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
public class MutableJournalVoucherResource {
  @Autowired
  protected JournalVoucherResourceHelper mJournalVoucherResourceHelper;

  @POST
  @Path("/save")
  public List<AccountTransaction> save(JsonArray pJsonArray) throws Exception {
    return mJournalVoucherResourceHelper.save(pJsonArray);
  }

  @POST
  @Path("/post")
  @AccountLogMessage(message = "Posting")
  public List<AccountTransaction> post(@Context HttpServletRequest pHttpServletRequest, JsonArray pJsonArray)
      throws Exception {
    return mJournalVoucherResourceHelper.postTransactions(pJsonArray);
  }

}
