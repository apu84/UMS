package org.ums.academic.resource;

import javax.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ApplicationTESManager;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */

@Component
@Path("academic/applicationTES")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ApplicationTESResource extends MutableApplicationTESResource {
  @Autowired
  ApplicationTESManager mApplicationTESManager;

  @GET
  @Path("/getAllQuestions")
  public JsonObject getAllQuestions(@Context Request pRequest) {
    return mHelper.getAllQuestions(pRequest, mUriInfo);
  }

  @GET
  @Path("/getReviewEligibleCourses/courseType/{course-type}")
  public JsonObject getReviewEligibleCourses(@Context Request pRequest, @PathParam("course-type") String pCourseType) {
    return mHelper.getReviewEligibleCourses(pCourseType, pRequest, mUriInfo);
  }

}
