package org.ums.accounts.resource.general.ledger.transactions.receipt.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Mar-18.
 */
@Component
@Path("/account/general-ledger/transaction/receipt-voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ReceiptVoucherResource extends MutableReceiptVoucherResource {
  @GET
  @Path("/voucher-number")
  public TransactionResponse getVoucherNo() throws Exception {
    return mHelper.getReceiptVoucherNo();
  }

  @GET
  @Path("paginated")
  public PaginatedVouchers getAllPaginated(@QueryParam("itemPerPage") Integer itemPerPage,
      @QueryParam("pageNumber") Integer pageNumber, @QueryParam("voucherNo") String pVoucherNo) throws Exception {
    PaginatedVouchers paginatedVouchers = mHelper.getAllReceiptVouchers(itemPerPage, pageNumber, pVoucherNo);
    return paginatedVouchers;
  }

  @GET
  @Path("/voucher-no/{voucher-no}/date/{date}")
  public List<AccountTransaction> getVouchers(@PathParam("voucher-no") String pVoucherNo,
      @PathParam("date") String pDate) throws Exception {
    List<AccountTransaction> accountTransactions = mHelper.getByVoucherNoAndDate(pVoucherNo, pDate);
    return accountTransactions;
  }
}
