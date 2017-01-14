package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.mutable.MutableFaculty;
import org.ums.manager.FacultyManager;
import org.ums.resource.Resource;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import static org.ums.resource.Resource.PATH_PARAM_OBJECT_ID;

/**
 * Created by Monjur-E-Morshed on 07-Dec-16.
 */
@Component
@Path("/academic/faculty")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FacultyResource extends Resource {

  @Autowired
  ResourceHelper<Faculty, MutableFaculty, Integer> mResourceHelper;

  @Autowired
  FacultyManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId)
      throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }
}
