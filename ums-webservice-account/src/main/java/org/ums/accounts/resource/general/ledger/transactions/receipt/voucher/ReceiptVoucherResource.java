package org.ums.accounts.resource.general.ledger.transactions.receipt.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.logs.GetLog;
import org.ums.report.transaction.TransactionReportGenerator;
import org.ums.resource.Resource;
import org.ums.util.UmsUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Mar-18.
 */
@Component
@Path("/account/general-ledger/transaction/receipt-voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ReceiptVoucherResource extends MutableReceiptVoucherResource {

  @Autowired
  private TransactionReportGenerator mTransactionReportGenerator;

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

  @GET
  @Produces("application/pdf")
  @Path("/receiptVoucherReport/voucherNo/{voucherNo}/voucherDate/{voucherDate}")
  @GetLog(message = "Requested for receipt voucher report")
  public StreamingOutput createReceiptVoucherReport(final @Context HttpServletResponse pHttpServletResponse,
      @PathParam("voucherNo") String pVoucherNO, @PathParam("voucherDate") String pVoucherDate) throws Exception {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mTransactionReportGenerator.createVoucherReport(pVoucherNO,
              UmsUtils.convertToDate(pVoucherDate, "dd-MM-yyyy"), output);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
