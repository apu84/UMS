package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionMeritListResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 11-Dec-16.
 */
public class MutableAdmissionMeritListResource extends Resource {
  @Autowired
  protected AdmissionMeritListResourceHelper mHelper;

  @POST
  public Response saveNewMeritList(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
