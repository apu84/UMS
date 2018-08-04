package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.QuestionCorrectionResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public class MutableQuestionCorrectionResource extends Resource {

  @Autowired
  QuestionCorrectionResourceHelper mQuestionCorrectionResourceHelper;

  @POST
  @Path("/addRecords")
  @Produces({MediaType.APPLICATION_JSON})
  public Response addQuestionCorrectionRecords(final JsonObject pJsonObject) throws Exception {
    return mQuestionCorrectionResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/deleteRecords")
  @Produces({MediaType.APPLICATION_JSON})
  public Response deleteExpelRecords(final JsonObject pJsonObject) {
    return mQuestionCorrectionResourceHelper.deleteRecords(pJsonObject, mUriInfo);
  }

}
