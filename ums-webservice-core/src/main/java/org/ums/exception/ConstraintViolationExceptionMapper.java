package org.ums.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLIntegrityConstraintViolationException;

@Provider
public class ConstraintViolationExceptionMapper implements
    ExceptionMapper<SQLIntegrityConstraintViolationException> {

  @Override
  public Response toResponse(SQLIntegrityConstraintViolationException e) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(new ExceptionResponse("Message", e.getMessage())).build();
  }

}
