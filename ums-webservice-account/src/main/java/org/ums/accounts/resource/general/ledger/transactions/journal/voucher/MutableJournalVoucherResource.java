package org.ums.accounts.resource.general.ledger.transactions.journal.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.logs.UmsLogMessage;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

  @DELETE
  @Path("/delete")
  public Response delete(final MutableAccountTransaction pMutableAccountTransaction) {
    return mJournalVoucherResourceHelper.delete(pMutableAccountTransaction);
  }

  @POST
  @Path("/post")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @UmsLogMessage(message = "Posting")
  public List<AccountTransaction> post(@Context HttpServletRequest pHttpServletRequest, JsonArray pJsonArray)
      throws Exception {
    return mJournalVoucherResourceHelper.postTransactions(pJsonArray);
  }

}
