package org.ums.exception;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExpiredSessionExceptionMapper implements ExceptionMapper<ExpiredSessionException> {
  private static final Logger mLogger = LoggerFactory.getLogger(ExpiredSessionExceptionMapper.class);

  @Override
  public Response toResponse(ExpiredSessionException e) {
    String errorMessage =
        "[" + SecurityUtils.getSubject().getPrincipal().toString() + "]:  ExpiredSession exception:" + e.getMessage();
    mLogger.error(errorMessage, e);
    return Response.status(Response.Status.UNAUTHORIZED)
        .entity(new ExceptionResponse(e.getClass().toString(), e.getMessage())).build();
  }
}
