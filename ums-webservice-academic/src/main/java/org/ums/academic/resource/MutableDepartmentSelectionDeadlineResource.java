package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.DepartmentSelectionDeadlineResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}
