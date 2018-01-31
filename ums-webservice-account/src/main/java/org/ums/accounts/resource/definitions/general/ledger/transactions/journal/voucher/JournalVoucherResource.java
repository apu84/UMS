package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.ws.rs.*;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
@Path("/account/general-ledger/transaction/journal-voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class JournalVoucherResource extends MutableJournalVoucherResource {

  @GET
  @Path("/voucher-number/voucher-id/{voucher-id}")
  public String getVoucherNumber(@PathParam("voucher-id") String pVoucherId) {
    return null;
  }

}
