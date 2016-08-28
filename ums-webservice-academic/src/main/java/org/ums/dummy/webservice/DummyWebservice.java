package org.ums.dummy.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.dummy.shared.DummyService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Component
@Path("/dummy")
public class DummyWebservice {
  @Autowired
  DummyService dummyService;

  @GET
  @Path("/message")
  public Response getMessage() {
    return Response.status(200).entity(dummyService.getMessage() + dummyService).build();
  }
}
