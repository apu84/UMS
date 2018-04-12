package org.ums.exception;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthenticatedExceptionMapper implements ExceptionMapper<UnauthenticatedException> {
  private static final Logger mLogger = LoggerFactory.getLogger(UnauthenticatedExceptionMapper.class);

  @Override
  public Response toResponse(UnauthenticatedException e) {
    String errorMessage = "[{" + SecurityUtils.getSubject()
        .getPrincipal().toString() + "}]:  Unauthenticated exception :" + e.getMessage();
    mLogger.error(errorMessage, e);
    return Response.status(Response.Status.UNAUTHORIZED).build();
  }
}
