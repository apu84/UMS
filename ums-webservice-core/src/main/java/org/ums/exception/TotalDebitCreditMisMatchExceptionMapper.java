package org.ums.exception;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.exceptions.MisMatchException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TotalDebitCreditMisMatchExceptionMapper implements ExceptionMapper<MisMatchException> {
  private static final Logger mLogger = LoggerFactory.getLogger(TotalDebitCreditMisMatchExceptionMapper.class);

  @Override
  public Response toResponse(MisMatchException e) {
    String errorMessage = "[" + SecurityUtils.getSubject()
        .getPrincipal().toString() + "]:  DebitCreditMisMatchException exception:" + e.getMessage();
    mLogger.error(errorMessage, e);
    return Response.status(Response.Status.NOT_ACCEPTABLE)
        .entity(new ExceptionResponse(e.getClass().toString(), "Credit and Debit is not equal"))
        .build();
  }
}
