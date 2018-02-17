package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.TransactionResponse;
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
  @Path("/voucher-number")
  public TransactionResponse getVoucherNumber() throws Exception {
    return mJournalVoucherResourceHelper.getJournalVoucherNo();
  }

  @GET
  @Path("/item-per-page/{item-per-page}/page-number/{page-number}")
  public String getAllPaginated(@PathParam("item-per-page") int itemPerPage, @PathParam("page-number") int pageNumber)
      throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PaginatedVouchers paginatedVouchers = mJournalVoucherResourceHelper.getAll(itemPerPage, pageNumber);
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    String json = gson.toJson(paginatedVouchers);
    return json;
  }

}
