package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.SemesterSyllabusMapResourceHelper;
import org.ums.manager.SemesterSyllabusMapManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 08-Jan-16.
 */
public class MutableSemesterSyllabusMapResource extends Resource {

  @Autowired
  SemesterSyllabusMapResourceHelper mResourceHelper;

  @Autowired
  SemesterSyllabusMapManager mManager;

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateMap(final @PathParam("object-id") int pObjectId,
      final @Context Request pRequest, final JsonObject pJsonObject) {

    return mResourceHelper.put(pJsonObject);
  }

  @POST
  public Response copyMapping(final @Context Request pRequest, final JsonObject pJsonObject) {

    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

}
