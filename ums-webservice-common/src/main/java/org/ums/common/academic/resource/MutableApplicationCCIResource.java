package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.ApplicationCCIResourceHelper;
import org.ums.domain.model.immutable.UGRegistrationResult;

import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by My Pc on 7/14/2016.
 */
public class MutableApplicationCCIResource extends Resource{

  @Autowired
  ApplicationCCIResourceHelper mHelper;

  @POST
  @Produces({MediaType.APPLICATION_JSON})
  public JsonObject createApplicationCCI(final JsonObject pJsonObject) throws Exception{
    return mHelper.saveAndReturn(pJsonObject,mUriInfo);
  }

  @DELETE
  public Response delete()throws Exception{
    return mHelper.deleteByStudentId(mUriInfo);
  }
}
