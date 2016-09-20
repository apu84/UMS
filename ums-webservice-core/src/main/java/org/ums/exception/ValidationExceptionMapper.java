package org.ums.exception;

import org.ums.exceptions.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
  @Override
  public Response toResponse(ValidationException e) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new ExceptionResponse("ValidationException", e.getMessage())).build();
  }
}
