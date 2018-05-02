package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.manager.StudentManager;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  public JsonObject getStudentInfoById() {
    return mResourceHelper.getStudentInfoById(mUriInfo);
  }

  @GET
  @Path("/getStudentsByDept")
  @GetLog(message = "Get students list by department")
  public JsonObject getActiveStudentsByDepartment() {
    return mResourceHelper.getActiveStudentsByDepartment(mUriInfo);
  }

  @GET
  @Path("/getStudents/adviser/{teacher-id}")
  @GetLog(message = "Get advising students")
  public JsonObject getActiveStudentsByTeacher(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("teacher-id") String pTeacherId) {
    return mResourceHelper.getActiveStudentsByAdviser(pTeacherId, mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  public Response get(final @Context Request pRequest) throws Exception {
    return mResourceHelper.get(SecurityUtils.getSubject().getPrincipal().toString(), pRequest, mUriInfo);
  }
}
