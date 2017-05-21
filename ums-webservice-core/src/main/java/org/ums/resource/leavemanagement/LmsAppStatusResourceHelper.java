package org.ums.resource.leavemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.manager.common.LmsAppStatusManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-May-17.
 */
@Component
public class LmsAppStatusResourceHelper extends ResourceHelper<LmsAppStatus, MutableLmsAppStatus, Long> {

  @Autowired
  LmsApplicationManager mLmsApplicationManager;

  @Autowired
  LmsAppStatusManager mLmsAppStatusManager;

  @Autowired
  LmsAppStatusBuilder mLmsAppStatusBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject getApplicationStatus(final Long pApplicationId, UriInfo pUriInfo) {
    List<LmsAppStatus> appStatuses = getContentManager().getAppStatus(pApplicationId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(LmsAppStatus status : appStatuses) {
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      getBuilder().build(objectBuilder, status, pUriInfo, localCache);
      children.add(objectBuilder);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected LmsAppStatusManager getContentManager() {
    return mLmsAppStatusManager;
  }

  @Override
  protected Builder<LmsAppStatus, MutableLmsAppStatus> getBuilder() {
    return mLmsAppStatusBuilder;
  }

  @Override
  protected String getETag(LmsAppStatus pReadonly) {
    return pReadonly.getLastModified();
  }
}
