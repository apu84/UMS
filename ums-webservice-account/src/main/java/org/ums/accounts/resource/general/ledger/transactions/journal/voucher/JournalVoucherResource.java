package org.ums.accounts.resource.general.ledger.transactions.journal.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
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
  @Path("paginated")
  public PaginatedVouchers getAllPaginated(@QueryParam("itemPerPage") Integer itemPerPage,
      @QueryParam("pageNumber") Integer pageNumber, @QueryParam("voucherNo") String pVoucherNo) throws Exception {
    PaginatedVouchers paginatedVouchers =
        mJournalVoucherResourceHelper.getAllJournalVouchers(itemPerPage, pageNumber, pVoucherNo);
    return paginatedVouchers;
  }

  @GET
  @Path("/voucher-no/{voucher-no}/date/{date}")
  public List<AccountTransaction> getVouchers(@PathParam("voucher-no") String pVoucherNo,
      @PathParam("date") String pDate) throws Exception {
    return mJournalVoucherResourceHelper.getByVoucherNoAndDate(pVoucherNo, pDate);
  }

}
