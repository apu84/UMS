package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
@Path("/account/general-ledger/transaction/journal-voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class JournalVoucherResource extends MutableJournalVoucherResource {

  @GET
  @Path("/voucher-number")
  public TransactionResponse getVoucherNumber() throws Exception {
    return mJournalVoucherResourceHelper.getJournalVoucherNo();
  }

  @GET
  @Path("/item-per-page/{item-per-page}/page-number/{page-number}")
  public List<MutableAccountTransaction> getAllPaginated(@PathParam("item-per-page") int itemPerPage,
      @PathParam("page-number") int pageNumber) {
    return mJournalVoucherResourceHelper.getAll(itemPerPage, pageNumber);
  }

}
