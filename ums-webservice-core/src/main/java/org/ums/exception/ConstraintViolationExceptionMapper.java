package org.ums.exception;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLIntegrityConstraintViolationException;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<SQLIntegrityConstraintViolationException> {
  private static final Logger mLogger = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

  @Override
  public Response toResponse(SQLIntegrityConstraintViolationException e) {
    String errorMessage = "[" + SecurityUtils.getSubject()
        .getPrincipal().toString() + "]:  SQLIntegrityConstraintViolation exception:" + e.getMessage();
    mLogger.error(errorMessage, e);
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(new ExceptionResponse("Message", e.getMessage())).build();
  }

}
