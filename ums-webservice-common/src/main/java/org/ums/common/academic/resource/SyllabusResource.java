package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.domain.model.MutableSyllabus;
import org.ums.domain.model.Syllabus;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/academic/syllabus")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SyllabusResource extends MutableSyllabusResource {
  @Autowired
  @Qualifier("syllabusManager")
  ContentManager<Syllabus, MutableSyllabus, String> mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    List<Syllabus> syllabuses = mManager.getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (Syllabus syllabus : syllabuses) {
      children.add(mResourceHelper.toJson(syllabus, mUriInfo));
    }
    object.add("entries", children);
    return object.build();
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public JsonObject get(final @PathParam("object-id") String pObjectId) throws Exception {
    Syllabus syllabus = mManager.get(pObjectId);
    return mResourceHelper.toJson(syllabus, mUriInfo);
  }
}

