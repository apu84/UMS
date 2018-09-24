package org.ums.academic.resource.optCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.optCourse.optCourseHelper.OptOfferedGroupResourceHelper;
import org.ums.manager.optCourse.OptOfferedGroupManager;
import org.ums.report.optReports.OfferedOptCourseList;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class MutableOptOfferedGroupResource extends Resource {
  @Autowired
  OptOfferedGroupResourceHelper mHelper;

  @POST
  @Path("/addRecords/programId/{program-id}/year/{year}/semester/{semester}")
  @Produces({MediaType.APPLICATION_JSON})
  public Response addRecords(@PathParam("program-id") Integer pProgramId, @PathParam("year") Integer pYear,
      @PathParam("semester") Integer pSemester, List<OfferedOptCourseList> list) throws Exception {
    return mHelper.addInfo(pProgramId, pYear, pSemester, list);
  }

}
