package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.AdmissionTotalSeatResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class MutableAdmissionTotalSeatResource extends Resource {

  @Autowired
  AdmissionTotalSeatResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response saveAdmissionTotalSeatInfo(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update")
  public Response updateAdmissionTotalSeatInfo(final JsonObject pJsonObject) throws Exception {
    return mHelper.put(pJsonObject, mUriInfo);
  }
}
