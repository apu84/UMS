package org.ums.academic.resource.fee.report;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.payment.StudentPaymentManager;

@Component
@Path("/fee-receipt")
public class FeeReceiptResource {
  @Autowired
  FeeReceipt mFeeReceipt;

  @Autowired
  StudentPaymentManager mStudentPaymentManager;

  @GET
  @Produces({"application/pdf"})
  @Path("/{transaction-id}")
  public StreamingOutput get(final @Context Request pRequest, final @PathParam("transaction-id") String txId) {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mFeeReceipt.createPdf(mStudentPaymentManager.getTransactionDetails(txId), output);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
