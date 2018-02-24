package org.ums.accounts.resource.general.ledger.transactions.payment.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;
import org.ums.resource.Resource;

import javax.ws.rs.*;

/**
 * Created by Monjur-E-Morshed on 19-Feb-18.
 */
@Component
@Path("/account/general-ledger/transaction/payment-voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PaymentVoucherResource extends MutablePaymentVoucherResource {

  @GET
  @Path("/voucher-no")
  public TransactionResponse getVoucherNo() throws Exception {
    return mHelper.getPaymentVoucherNo();
  }

  @GET
  @Path("paginated")
  public PaginatedVouchers getAllPaginated(@QueryParam("itemPerPage") Integer itemPerPage,
      @QueryParam("pageNumber") Integer pageNumber, @QueryParam("voucherNo") String pVoucherNo) throws Exception {
    PaginatedVouchers paginatedVouchers = mHelper.getAllPaymentVouchers(itemPerPage, pageNumber, pVoucherNo);
    return paginatedVouchers;
  }
}
