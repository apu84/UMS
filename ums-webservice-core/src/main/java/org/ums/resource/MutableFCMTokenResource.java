package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.resource.helper.FCMTokenResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

public class MutableFCMTokenResource extends Resource {

  @Autowired
  ResourceHelper<FCMToken, MutableFCMToken, String> mHelper;

  @Autowired
  FCMTokenResourceHelper mResourceHelper;

  @POST
  public Response save(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  public int update(final JsonObject pJsonObject) throws Exception {
    return 0;
  }
}
