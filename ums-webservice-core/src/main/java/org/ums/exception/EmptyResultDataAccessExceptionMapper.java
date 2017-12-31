package org.ums.exception;

import org.apache.shiro.session.ExpiredSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EmptyResultDataAccessExceptionMapper implements ExceptionMapper<EmptyResultDataAccessException> {

  private static final Logger mLogger = LoggerFactory.getLogger(UncaughtExceptionMapper.class);

  @Override
  public Response toResponse(EmptyResultDataAccessException emptyResultDataAccessException) {
    emptyResultDataAccessException.printStackTrace();
    mLogger.error(emptyResultDataAccessException.getMessage());
    CustomReasonPhraseExceptionStatusType error =
        new CustomReasonPhraseExceptionStatusType(Response.Status.INTERNAL_SERVER_ERROR, "No Data Found");
    JsonObjectBuilder errorObject = Json.createObjectBuilder();
    errorObject.add("reason", error.getReasonPhrase());
    return Response.status(error.getStatusCode()).entity(errorObject.build()).type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
