package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.OptionalCourseApplicationResourceHelper;
import org.ums.common.academic.resource.helper.SemesterResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;


@Component
@Path("/academic/optional/application")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class OptionalCourseApplicationResource extends Resource {

  @Autowired
  OptionalCourseApplicationResourceHelper mResourceHelper;

  @GET
  @Path("/stat/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getStatistics(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("program-id") Integer pExamTypeId) throws Exception {
    return mResourceHelper.getApplicationStatistics(pSemesterId, pExamTypeId);
  }



}
