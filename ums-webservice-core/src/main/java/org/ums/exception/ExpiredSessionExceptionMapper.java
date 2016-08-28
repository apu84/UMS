package org.ums.exception;

import org.apache.shiro.session.ExpiredSessionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExpiredSessionExceptionMapper implements ExceptionMapper<ExpiredSessionException> {
  @Override
  public Response toResponse(ExpiredSessionException e) {
    return Response.status(Response.Status.UNAUTHORIZED)
        .entity(new ExceptionResponse(e.getClass().toString(), e.getMessage())).build();
  }
}
