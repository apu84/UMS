package org.ums.resource;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.springframework.stereotype.Component;
import org.ums.logging.applog.UMSLogger;
import org.ums.resource.Resource;

import javax.json.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/logger")
@Produces(Resource.MIME_TYPE_JSON)
public class LoggerResource extends Resource {
  @GET
  @Path("/levels")
  public List<Level> getUsers(final @Context Request pRequest) {
    return UMSLogger.getLogLevels();
  }

  @GET
  @Path("/list")
  public JsonArray getLoggers() {
    JsonArrayBuilder loggerArray = Json.createArrayBuilder();
    List<Logger> loggers = UMSLogger.getLoggers();

    for(Logger logger : loggers) {
      JsonObjectBuilder loggerObject = Json.createObjectBuilder();
      if(logger.getLevel() != null && logger.getLevel() != Level.OFF) {
        loggerObject.add("name", logger.getName());
        loggerObject.add("level", logger.getLevel().levelInt);
        loggerArray.add(loggerObject);
      }

    }

    return loggerArray.build();
  }

  @POST
  public Response addLogger(final JsonObject pJsonObject) {
    String name = pJsonObject.getString("name");
    int level = Integer.parseInt(pJsonObject.getString("level"));
    UMSLogger.setLogger(name, Level.toLevel(level));
    return Response.noContent().build();
  }
}
