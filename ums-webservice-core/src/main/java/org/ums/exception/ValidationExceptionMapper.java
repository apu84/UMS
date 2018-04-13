package org.ums.exception;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
  private static final Logger mLogger = LoggerFactory.getLogger(ValidationExceptionMapper.class);

  @Override
  public Response toResponse(ValidationException e) {
    String errorMessage =
        "[" + SecurityUtils.getSubject().getPrincipal().toString() + "]: Validation exception:" + e.getMessage();
    mLogger.error(errorMessage, e);
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new ExceptionResponse("ValidationException", e.getMessage())).build();
  }
}
