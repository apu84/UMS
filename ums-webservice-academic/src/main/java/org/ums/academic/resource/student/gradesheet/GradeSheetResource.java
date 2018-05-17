package org.ums.academic.resource.student.gradesheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.manager.StudentManager;
import org.ums.manager.TaskStatusManager;
import org.ums.resource.Resource;
import org.ums.services.academic.ProcessResult;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/gradesheet")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class GradeSheetResource extends Resource {
  @Autowired
  private GradeSheetResourceHelper mGradeSheetResourceHelper;

  @Autowired
  private TaskStatusManager mTaskStatusManager;

  @Autowired
  private StudentManager mStudentManager;

  @GET
  @Path("/semester" + PATH_PARAM_OBJECT_ID)
  public Response getGradesheet(final @Context Request pRequest, final @PathParam("object-id") Integer pSemesterId) {
    // String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    String studentId = "130108006";
    Student student = mStudentManager.get(studentId);
    String taskSemesterId
        = mTaskStatusManager.buildTaskId(student.getProgramId(), pSemesterId, ProcessResult.PROCESS_GPA_CGPA_PROMOTION);
    String taskYearSemesterId
        = mTaskStatusManager.buildTaskId(student.getProgramId(), pSemesterId, student.getCurrentYear(),
        student.getCurrentAcademicSemester(), ProcessResult.PROCESS_GPA_CGPA_PROMOTION);

    TaskStatus taskStatus = null;
    if (mTaskStatusManager.exists(taskSemesterId)) {
      taskStatus = mTaskStatusManager.get(taskSemesterId);
    }
    else if (mTaskStatusManager.exists(taskYearSemesterId)) {
      taskStatus = mTaskStatusManager.get(taskYearSemesterId);
    }

    if (taskStatus != null && taskStatus.getStatus() == TaskStatus.Status.COMPLETED) {
      return Response.ok().entity(mGradeSheetResourceHelper.getGradesheet(studentId, pSemesterId, mUriInfo)).build();
    }

    return Response.status(404).build();
  }
}
