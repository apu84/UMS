package org.ums.readmission.student;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.StudentManager;
import org.ums.resource.Resource;

@Component
@Path("/readmission")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class Readmission extends Resource {
  @Autowired
  ReadmissionHelper mReadmissionHelper;
  @Autowired
  StudentManager mStudentManager;

  @GET
  public ReadmissionHelper.ReadmissionApplicationStatus getReadmissionStatus() throws Exception {
    return mReadmissionHelper.readmissionApplicationStatus(getLoggedInUserId(),
        getStudent().getCurrentEnrolledSemesterId());
  }

  @GET
  @Path("/application}")
  public JsonObject getReadmissionApplicationStatus() throws Exception {
    return mReadmissionHelper.appliedReadmissionCourses(getLoggedInUserId(),
        getStudent().getCurrentEnrolledSemesterId());
  }

  @GET
  @Path("/applicable-courses")
  public JsonObject getApplicableCourses() throws Exception {
    return mReadmissionHelper.failedCoursesForApplication(getLoggedInUserId(),
        getStudent().getCurrentEnrolledSemesterId());
  }

  @POST
  @Path("/apply")
  public ReadmissionHelper.ReadmissionApplicationStatus apply(final JsonObject pJsonObject) throws Exception {
    return mReadmissionHelper.apply(getLoggedInUserId(), getStudent().getCurrentEnrolledSemesterId(), pJsonObject);
  }

  private Student getStudent() {
    return mStudentManager.get(getLoggedInUserId());
  }
}
