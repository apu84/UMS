package org.ums.academic.resource.optCourse;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.report.optReports.OfferedOptCourseList;
import org.ums.report.optReports.SubGroupList;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
@Path("academic/optOfferedGroup")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class OptOfferedGroupResource extends MutableOptOfferedGroupResource {
  @GET
  @Path("/getOfferedCourses/semesterId/{Semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public List<OfferedOptCourseList> getInfo(@Context Request pRequest, @PathParam("Semester-id") Integer pSemesterId,
      @PathParam("program-id") Integer pProgramId, @PathParam("year") Integer pYear,
      @PathParam("semester") Integer pSemester) {
    return mHelper.getOptOfferedCourseList(pSemesterId, pProgramId, pYear, pSemester);
  }

  @GET
  @Path("/getGroupInfo/semesterId/{Semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getGroupInfo(@Context Request pRequest, @PathParam("Semester-id") Integer pSemesterId,
      @PathParam("program-id") Integer pProgramId, @PathParam("year") Integer pYear,
      @PathParam("semester") Integer pSemester) {
    return mHelper.getGroupInfo(pSemesterId, pProgramId, pYear, pSemester, mUriInfo);
  }

}
