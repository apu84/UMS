package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.configuration.UMSConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.HashMap;
import java.util.Map;

@Component
@Path("/settings")
@Produces(Resource.MIME_TYPE_JSON)
public class Settings extends Resource {
  @Autowired
  private UMSConfiguration mConfiguration;

  @GET
  public Map<String, Object> get(final @Context Request pRequest) throws Exception {
    Map<String, Object> settings = new HashMap<>();
    settings.put("notification.enabled", mConfiguration.isNotificationServiceEnabled());
    settings.put("polling.interval", mConfiguration.getPollingInterval());
    return settings;
  }
}
