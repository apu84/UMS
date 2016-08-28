package org.ums.exception;

import org.apache.shiro.authz.UnauthenticatedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthenticatedExceptionMapper implements ExceptionMapper<UnauthenticatedException> {
  @Override
  public Response toResponse(UnauthenticatedException exception) {
    exception.printStackTrace();
    return Response.status(Response.Status.UNAUTHORIZED).build();
  }
}
