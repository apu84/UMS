package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.resource.helper.FCMTokenResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableFCMTokenResource extends Resource {

  @Autowired
  ResourceHelper<FCMToken, MutableFCMToken, Long> mHelper;

  @Autowired
  FCMTokenResourceHelper mResourceHelper;

  @POST
  @Path("/save")
  public Response save(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
