package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.FCMTokenResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

public class MutableFCMTokenResource extends Resource {

  @Autowired
  private FCMTokenResourceHelper mResourceHelper;

  @POST
  public Response save(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }
}
