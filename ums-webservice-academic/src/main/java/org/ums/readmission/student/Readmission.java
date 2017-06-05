package org.ums.readmission.student;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/readmission")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class Readmission extends Resource {
  @Autowired
  ReadmissionHelper mReadmissionHelper;

  @GET
  @Path("/{semesterId}")
  public ReadmissionHelper.ReadmissionApplicationStatus getReadmissionStatus(final @Context Request pRequest,
      final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mReadmissionHelper.readmissionApplicationStatus(getUserId(), pSemesterId);
  }

  @GET
  @Path("/application/{semesterId}")
  public JsonObject getReadmissionApplicationStatus(final @Context Request pRequest,
      final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mReadmissionHelper.appliedReadmissionCourses(getUserId(), pSemesterId);
  }

  @GET
  @Path("/{semesterId}/applicable-courses")
  public JsonObject getApplicableCourses(final @Context Request pRequest, final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mReadmissionHelper.failedCoursesForApplication(getUserId(), pSemesterId);
  }

  @POST
  @Path("/apply/{semesterId}")
  public ReadmissionHelper.ReadmissionApplicationStatus apply(final @Context Request pRequest,
      final @PathParam("semesterId") int pSemesterId, final JsonObject pJsonObject) throws Exception {
    return mReadmissionHelper.apply(getUserId(), pSemesterId, pJsonObject);
  }

  private String getUserId() {
    return SecurityUtils.getSubject().getPrincipal().toString();
  }
}
