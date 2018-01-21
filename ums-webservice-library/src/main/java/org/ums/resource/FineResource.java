package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Component
@Path("library/fine")
public class FineResource extends MutableFineResource {

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/patronId/{patron-id}")
  public JsonObject getFines(@PathParam("patron-id") String pPatronId) throws Exception {
    return mHelper.getFines(pPatronId, mUriInfo);
  }

}
