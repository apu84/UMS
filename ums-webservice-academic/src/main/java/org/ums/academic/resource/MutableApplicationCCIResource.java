package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.ApplicationCCIResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 7/14/2016.
 */
public class MutableApplicationCCIResource extends Resource {

  @Autowired
  ApplicationCCIResourceHelper mHelper;

  @POST
  @Produces({MediaType.APPLICATION_JSON})
  public JsonObject createApplicationCCI(final JsonObject pJsonObject) {
    return mHelper.saveAndReturn(pJsonObject, mUriInfo);
  }

  @DELETE
  public Response delete() {
    return mHelper.deleteByStudentId(mUriInfo);
  }
}
