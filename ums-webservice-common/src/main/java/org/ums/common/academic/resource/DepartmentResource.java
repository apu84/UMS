package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.domain.model.Department;
import org.ums.domain.model.MutableDepartment;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/academic/department")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class DepartmentResource extends Resource {
  @Autowired
  ResourceHelper<Department, MutableDepartment, Integer> mResourceHelper;

  @Autowired
  @Qualifier("departmentManager")
  ContentManager<Department, MutableDepartment, Integer> mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    List<Department> departments = mManager.getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (Department department : departments) {
      children.add(mResourceHelper.toJson(department, mUriInfo));
    }
    object.add("entries", children);
    return object.build();
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public JsonObject get(final @PathParam("object-id") int pObjectId) throws Exception {
    Department department = mManager.get(pObjectId);
    return mResourceHelper.toJson(department, mUriInfo);
  }
}
