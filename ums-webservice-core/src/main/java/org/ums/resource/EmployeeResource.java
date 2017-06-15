package org.ums.resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.EmployeeManager;

@Component
@Path("academic/employee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class EmployeeResource extends MutableEmployeeResource {
  @Autowired
  EmployeeManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mEmployeeResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/getActiveTeachersByDept")
  public JsonObject getActiveTeachersByDepartment() {
    return mEmployeeResourceHelper.getActiveTeachersByDept(mUriInfo);
  }

  @GET
  @Path("/employeeById")
  public JsonObject getEmployeeById() {
    return mEmployeeResourceHelper.getByEmployeeId(mUriInfo);
  }

  @GET
  @Path("/designation/{designationId}")
  public JsonObject getByDesignation(final @Context Request pRequest,
      final @PathParam("designationId") String designationId) {
    return mEmployeeResourceHelper.getByDesignation(designationId, pRequest, mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return mEmployeeResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  @Path("/search")
  public JsonObject searchUser(@QueryParam("term") String pTerm, @QueryParam("page") int pPage) throws Exception {
    return mEmployeeResourceHelper.searchUserByName(pTerm, pPage, mUriInfo);
  }

  @GET
  @Path("/searchByDepartment")
  public JsonObject searchUserByChildren(@QueryParam("term") String pTerm, @QueryParam("page") int pPage)
      throws Exception {
    return mEmployeeResourceHelper.searchUserByDepartment(pTerm, pPage, mUriInfo);
  }

  @GET
  @Path("/getEmployee/publicationStatus/{publication-status}")
  public JsonObject getEmployees(final @PathParam("publication-status") String pPublicationStatus,
      final @Context Request pRequest) {
    return mEmployeeResourceHelper.getEmployees(pPublicationStatus, pRequest, mUriInfo);
  }

  @GET
  @Path("/employeeById/departmentId/{department-id}")
  public JsonObject getEmployees(final @Context Request pRequest, final @PathParam("department-id") String pDepartmentId) {
    return mEmployeeResourceHelper.getEmployees(pDepartmentId, mUriInfo);
  }
}
