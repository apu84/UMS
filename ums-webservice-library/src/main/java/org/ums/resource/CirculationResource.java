package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("circulation")
public class CirculationResource extends MutableCirculationResource {

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/id/{patron-id}")
  public JsonObject get(final @PathParam("patron-id") String pPatronId, final @Context Request pRequest)
      throws Exception {
    return mHelper.getCirculation(pPatronId, mUriInfo);
  }

  @GET
  @Path("/id/{patron-id}/checkedInItems")
  public JsonObject getCirculationCheckedInItems(final @PathParam("patron-id") String pPatronId,
      final @Context Request pRequest) throws Exception {
    return mHelper.getCirculationCheckedInItems(pPatronId, mUriInfo);
  }

  @GET
  @Path("/all/id/{patron-id}")
  public JsonObject getAllCirculations(final @PathParam("patron-id") String pPatronId, final @Context Request pRequest)
      throws Exception {
    return mHelper.getAllCirculation(pPatronId, mUriInfo);
  }
}
