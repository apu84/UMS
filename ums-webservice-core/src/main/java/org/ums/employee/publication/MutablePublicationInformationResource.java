package org.ums.employee.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.DeleteLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public class MutablePublicationInformationResource extends Resource {

  @Autowired
  private PublicationInformationResourceHelper mHelper;

  @POST
  @PostLog(message = "Created an publication information")
  public Response create(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return mHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @PutLog(message = "Updated an publication information")
  public Response update(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return mHelper.update(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  @DeleteLog(message = "Deleted an publication information")
  public Response delete(@Context HttpServletRequest pHttpServletRequest, final @PathParam("object-id") String pObjectId) {
    System.out.println(pObjectId);
    return mHelper.delete(Long.parseLong(pObjectId), mUriInfo);
  }
}
