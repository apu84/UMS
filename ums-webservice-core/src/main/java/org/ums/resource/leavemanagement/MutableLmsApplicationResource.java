package org.ums.resource.leavemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 14-May-17.
 */
public class MutableLmsApplicationResource extends Resource {

  @Autowired
  protected LmsApplicationResourceHelper mHelper;

  @POST
  @Path("/save")
  @Produces(MediaType.MULTIPART_FORM_DATA)
  public Response saveApplication(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }

}
