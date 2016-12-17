package org.ums.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class UncaughtExceptionMapper extends Throwable implements
    javax.ws.rs.ext.ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable e) {
    return Response
        .status(
            new CustomReasonPhraseExceptionStatusType(Response.Status.INTERNAL_SERVER_ERROR, e
                .getMessage())).type(MediaType.APPLICATION_JSON_TYPE).build();
  }
}
