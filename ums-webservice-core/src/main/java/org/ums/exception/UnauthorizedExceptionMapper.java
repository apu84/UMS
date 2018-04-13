package org.ums.exception;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
  private static final Logger mLogger = LoggerFactory.getLogger(UnauthorizedExceptionMapper.class);

  @Override
  public Response toResponse(UnauthorizedException e) {
    String errorMessage =
        "[" + SecurityUtils.getSubject().getPrincipal().toString() + "]:  Unauthorized exception:" + e.getMessage();
    mLogger.error(errorMessage, e);
    return Response.status(Response.Status.UNAUTHORIZED).build();
  }
}
