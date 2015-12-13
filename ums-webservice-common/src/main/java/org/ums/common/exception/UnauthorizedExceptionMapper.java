package org.ums.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.authz.UnauthorizedException;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
  @Override
  public Response toResponse(UnauthorizedException exception) {
    return Response.status(Response.Status.UNAUTHORIZED).build();
  }
}