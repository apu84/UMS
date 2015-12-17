package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.domain.model.MutableProgramType;
import org.ums.domain.model.ProgramType;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/academic/programtype")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ProgramTypeResource extends MutableProgramTypeResource {
  @Autowired
  @Qualifier("programTypeManager")
  ContentManager<ProgramType, MutableProgramType, Integer> mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    List<ProgramType> programTypes = mManager.getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (ProgramType programType : programTypes) {
      children.add(mResourceHelper.toJson(programType, mUriInfo));
    }
    object.add("entries", children);
    return object.build();
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public JsonObject get(final @PathParam("object-id") int pObjectId) throws Exception {
    ProgramType programType = mManager.get(pObjectId);
    return mResourceHelper.toJson(programType, mUriInfo);
  }
}
