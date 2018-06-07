package org.ums.accounts.resource.general.ledger.transactions.payment.voucher;

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
 * Created by Monjur-E-Morshed on 19-Feb-18.
 */
@Component
@Path("/account/general-ledger/transaction/payment-voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PaymentVoucherResource extends MutablePaymentVoucherResource {

  @Autowired
  private TransactionReportGenerator mTransactionReportGenerator;

  @GET
  @Path("/voucher-number")
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

  @GET
  @Path("/voucher-no/{voucher-no}/date/{date}")
  public List<AccountTransaction> getVouchers(@PathParam("voucher-no") String pVoucherNo,
      @PathParam("date") String pDate) throws Exception {
    return mHelper.getByVoucherNoAndDate(pVoucherNo, pDate);
  }

  @GET
  @Produces("application/pdf")
  @Path("/paymentVoucherReport/voucherNo/{voucherNo}/voucherDate/{voucherDate}")
  @GetLog(message = "Requested for payment voucher report")
  public StreamingOutput createPaymentVoucherReport(final @Context HttpServletResponse pHttpServletResponse,
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
