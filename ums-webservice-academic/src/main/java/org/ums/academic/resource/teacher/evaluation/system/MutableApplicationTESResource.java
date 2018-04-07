package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.teacher.evaluation.system.ApplicationTESResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
public class MutableApplicationTESResource extends Resource {

  @Autowired
  ApplicationTESResourceHelper mHelper;

  @POST
  @Path("/saveTES")
  @Produces({MediaType.APPLICATION_JSON})
  public Response saveTes(final JsonObject pJsonObject) {
    return mHelper.saveToTes(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/addQuestion")
  @Produces({MediaType.APPLICATION_JSON})
  public Response addQuestion(final JsonObject pJsonObject) {
    return mHelper.addQuestion(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/setQuestion")
  @Produces({MediaType.APPLICATION_JSON})
  public Response setQuestion(final JsonObject pJsonObject) {
    return mHelper.setQuestion(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/saveAssignedCoursesByHead")
  @Produces({MediaType.APPLICATION_JSON})
  public Response saveAssignedCourses(final JsonObject pJsonObject) {
    return mHelper.saveAssignedCourses(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/deleteQuestion")
  @Produces({MediaType.APPLICATION_JSON})
  public Response deleteQuestion(final JsonObject pJsonObject) {
    return mHelper.deleteQuestion(pJsonObject, mUriInfo);
  }
}
