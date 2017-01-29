package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.UserGuideBuilder;
import org.ums.domain.model.immutable.UserGuide;
import org.ums.domain.model.mutable.MutableUserGuide;
import org.ums.manager.ContentManager;
import org.ums.manager.UserGuideManager;
import org.ums.manager.UserManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Ifti on 18-Dec-16.
 */
@Component
public class UserGuideResourceHelper extends ResourceHelper<UserGuide, MutableUserGuide, Integer> {

  @Autowired
  UserGuideBuilder mBuilder;

  @Autowired
  UserGuideManager mUserGuideManager;

  @Autowired
  UserManager mUserManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    return null;
  }

  @Override
  protected ContentManager<UserGuide, MutableUserGuide, Integer> getContentManager() {
    return mUserGuideManager;
  }

  @Override
  protected Builder<UserGuide, MutableUserGuide> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(UserGuide pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getUserGuides(String pUserId, final UriInfo pUriInfo) {
    List<UserGuide> userGuideList =
        mUserGuideManager.getUserGuideList(mUserManager.get(pUserId).getPrimaryRole().getId(),
            pUserId);
    return buildJsonResponse(userGuideList, pUriInfo);
  }

  public JsonObject getUserGuide(Integer pNavigationId) {
    UserGuide userGuide = mUserGuideManager.getUserGuide(pNavigationId);

    return toJson(userGuide, null, null);
  }

}
