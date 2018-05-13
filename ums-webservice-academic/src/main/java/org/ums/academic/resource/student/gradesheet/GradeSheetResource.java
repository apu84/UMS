package org.ums.academic.resource.student.gradesheet;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/academic/gradesheet")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class GradeSheetResource extends Resource {
  @Autowired
  GradeSheetResourceHelper mGradeSheetResourceHelper;

  @GET
  @Path("/semester" + PATH_PARAM_OBJECT_ID)
  public JsonObject getGradesheet(final @Context Request pRequest, final @PathParam("object-id") Integer pSemesterId) {
    // String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    String studentId = "130108006";
    return mGradeSheetResourceHelper.getGradesheet(studentId, pSemesterId, mUriInfo);
  }
}
