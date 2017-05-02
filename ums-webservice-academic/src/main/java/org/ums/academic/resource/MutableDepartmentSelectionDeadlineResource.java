package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.DepartmentSelectionDeadlineResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 29-Apr-17.
 */
public class MutableDepartmentSelectionDeadlineResource extends Resource {

  @Autowired
  DepartmentSelectionDeadlineResourceHelper mHelper;

  @POST
  @Path("/")
  public Response saveOrUpdateData(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path("/delete/id/{id}")
  public Response delete(@PathParam("id") int pId) throws Exception {
    return mHelper.delete(pId);
  }

}
