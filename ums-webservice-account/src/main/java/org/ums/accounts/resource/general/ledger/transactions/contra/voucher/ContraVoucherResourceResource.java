package org.ums.accounts.resource.general.ledger.transactions.contra.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 22-Feb-18.
 */
@Component
@Path("/account/general-ledger/transaction/contra-voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ContraVoucherResourceResource extends MutableContraVoucherResource {
  @GET
  @Path("/voucher-number")
  public TransactionResponse getVoucherNo() throws Exception {
    return mHelper.getContraVoucherNo();
  }

  @GET
  @Path("paginated")
  public PaginatedVouchers getAllPaginated(@QueryParam("itemPerPage") Integer itemPerPage,
      @QueryParam("pageNumber") Integer pageNumber, @QueryParam("voucherNo") String pVoucherNo) throws Exception {
    PaginatedVouchers paginatedVouchers = mHelper.getAllContraVouchers(itemPerPage, pageNumber, pVoucherNo);
    return paginatedVouchers;
  }

  @GET
  @Path("/voucher-no/{voucher-no}/date/{date}")
  public List<AccountTransaction> getVouchers(@PathParam("voucher-no") String pVoucherNo,
      @PathParam("date") String pDate) throws Exception {
    return mHelper.getByVoucherNoAndDate(pVoucherNo, pDate);
  }
}
