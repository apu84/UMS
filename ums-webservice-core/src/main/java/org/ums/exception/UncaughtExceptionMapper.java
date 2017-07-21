package org.ums.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.services.academic.SeatPlanServiceImpl;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class UncaughtExceptionMapper extends Throwable implements javax.ws.rs.ext.ExceptionMapper<Throwable> {
  private static final Logger mLogger = LoggerFactory.getLogger(UncaughtExceptionMapper.class);

  @Override
  public Response toResponse(Throwable e) {
    mLogger.error("Uncaught exception: ", e);
    CustomReasonPhraseExceptionStatusType error =
        new CustomReasonPhraseExceptionStatusType(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
    JsonObjectBuilder errorObject = Json.createObjectBuilder();
    errorObject.add("reason", error.getReasonPhrase());
    return Response.status(error.getStatusCode()).entity(errorObject.build()).type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
