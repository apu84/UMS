package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.NotificationResourceHelper;
import org.ums.resource.helper.UserGuideResourceHelper;
import org.ums.services.UserHomeService;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;
import java.util.Map;

/**
 * Created by Ifti on 13-Dec-16.
 */
@Component
@Path("/userGuide")
@Produces(Resource.MIME_TYPE_JSON)
public class UserGuideResource extends Resource {

  @Autowired
  UserGuideResourceHelper mUserGuideResourceHelper;

  @GET
  public JsonObject getAdditionalRolePermissions(final @Context Request pRequest) {
    return mUserGuideResourceHelper.getUserGuides(SecurityUtils.getSubject().getPrincipal()
        .toString(), mUriInfo);
  }
}
