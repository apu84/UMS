package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("fcmToken")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FCMTokenResource extends MutableFCMTokenResource {

  @Autowired
  private ResourceHelper<FCMToken, MutableFCMToken, String> mHelper;

  @GET
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }
}
