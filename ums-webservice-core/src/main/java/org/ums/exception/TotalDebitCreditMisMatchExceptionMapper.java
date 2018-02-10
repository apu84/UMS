package org.ums.exception;

import org.ums.exceptions.MisMatchException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by Monjur-E-Morshed on 08-Feb-18.
 */
public class TotalDebitCreditMisMatchExceptionMapper implements ExceptionMapper<MisMatchException> {
  @Override
  public Response toResponse(MisMatchException mismatchException) {
    return Response.status(Response.Status.NOT_ACCEPTABLE)
        .entity(new ExceptionResponse(mismatchException.getClass().toString(), "Credit and Debit is not equal"))
        .build();
  }
}
