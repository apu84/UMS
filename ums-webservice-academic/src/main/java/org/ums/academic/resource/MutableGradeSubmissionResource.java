package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.GradeSubmissionResourceHelper;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;
import org.ums.twofa.TwoFA;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Created by ikh on 4/29/2016.
 */
public class MutableGradeSubmissionResource extends Resource {

  @Autowired
  GradeSubmissionResourceHelper mResourceHelper;

  @PUT
  @TwoFA(type = "marks_submission")
  @UmsLogMessage(message = "Save/Submit Grade Sheet")
  public Response saveGradeSheet(@Context HttpServletRequest pHttpServletRequest, JsonObject pJsonObject) {
    return mResourceHelper.saveGradeSheet(pJsonObject);
  }

  @PUT
  @Path("/recheckApprove")
  @UmsLogMessage(message = "Grade Sheet Recheck/Approval")
  public Response recheckApprove(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.updateGradeStatus(pJsonObject);
  }

  @PUT
  @Path("/vc/recheckApprove")
  public Response recheckRequestApprove(final JsonObject pJsonObject) {
    return mResourceHelper.recheckRequestApprove(pJsonObject);
  }

  @PUT
  @Path("/gradeSubmissionDeadLine")
  @UmsLogMessage(message = "Grade Submission Deadline Update")
  public Response updateGradeSubmissionDeadLine(final JsonObject pJsonObject) {
    return mResourceHelper.updateGradeSubmissionDeadLine(pJsonObject, mUriInfo);
  }

}
