package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import org.springframework.beans.factory.annotation.Autowired;

import javax.json.JsonArray;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
public class MutableJournalVoucherResource {
  @Autowired
  protected JournalVoucherResourceHelper mJournalVoucherResourceHelper;

  @POST
  @Path("/save")
  public Response save(JsonArray pJsonArray) throws Exception {
    return mJournalVoucherResourceHelper.save(pJsonArray);
  }

}
