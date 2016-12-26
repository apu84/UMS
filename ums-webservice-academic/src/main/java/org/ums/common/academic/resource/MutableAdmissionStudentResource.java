package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.AdmissionStudentResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 17-Dec-16.
 */
public class MutableAdmissionStudentResource extends Resource {

  @Autowired
  AdmissionStudentResourceHelper mHelper;

  @POST
  @Path("/taletalkData/semester/{semester-id}")
  public Response saveTaletalkData(@PathParam("semester-id") int pSemesterId,
      final JsonObject pJsonObject) throws Exception {
    return mHelper.postTaletalkData(pJsonObject, pSemesterId, mUriInfo);
  }

}
