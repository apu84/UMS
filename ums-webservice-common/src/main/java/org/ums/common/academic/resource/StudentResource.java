package org.ums.common.academic.resource;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.StudentManager;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/student")
public class StudentResource extends MutableStudentResource {
  @Autowired
  StudentManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/getStudentInfoById")
  public JsonObject getStudentInfoById() throws Exception {
    return mResourceHelper.getStudentInfoById(mUriInfo);
  }

  //Commented during refactoring of student's class routine
//  @GET
//  @Path(PATH_PARAM_OBJECT_ID)
//  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
//    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
//  }

  @GET
  public Response get(final @Context Request pRequest) throws Exception {

    return mResourceHelper.get(SecurityUtils.getSubject().getPrincipal().toString(), pRequest, mUriInfo);
  }
}
