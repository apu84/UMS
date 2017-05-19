package org.ums.academic.resource.fee.dues;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/student-dues")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class StudentDuesResource extends Resource {
  @Autowired
  StudentDuesHelper mStudentDuesHelper;

  @GET
  @Path("/{studentId}")
  public JsonObject getDues(final @PathParam("studentId") String pStudentId) throws Exception {
    return mStudentDuesHelper.getDues(pStudentId, mUriInfo);
  }

  @POST
  public Response addDue(final JsonObject pJsonObject) throws Exception {
    return mStudentDuesHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/{studentId}")
  public Response payDues(final @PathParam("studentId") String pStudentId, final JsonObject pJsonObject) {
    return mStudentDuesHelper.payDues(pStudentId, pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/updateDues/{studentId}")
  public Response updateDues(final @PathParam("studentId") String pStudentId, final JsonObject pJsonObject)
      throws Exception {
    return mStudentDuesHelper.updateDues(pStudentId, pJsonObject, mUriInfo);
  }
}
