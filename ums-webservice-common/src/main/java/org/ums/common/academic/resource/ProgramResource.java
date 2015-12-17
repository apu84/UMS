package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.domain.model.Mutable;
import org.ums.domain.model.MutableProgram;
import org.ums.domain.model.Program;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/academic/program")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ProgramResource extends Resource {
  @Autowired
  ResourceHelper<Program, MutableProgram, Integer> mResourceHelper;

  @Autowired
  @Qualifier("programManager")
  ContentManager<Program, MutableProgram, Integer> mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    List<Program> programs = mManager.getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (Program program : programs) {
      children.add(mResourceHelper.toJson(program, mUriInfo));
    }
    object.add("entries", children);
    return object.build();
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public JsonObject get(final @PathParam("object-id") int pObjectId) throws Exception {
    Program program = mManager.get(pObjectId);
    return mResourceHelper.toJson(program, mUriInfo);
  }
}
