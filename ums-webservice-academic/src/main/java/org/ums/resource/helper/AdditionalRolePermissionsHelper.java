package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AdditionalRolePermissionsBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdditionalRolePermissions;
import org.ums.domain.model.mutable.MutableAdditionalRolePermissions;
import org.ums.manager.AdditionalRolePermissionsManager;
import org.ums.manager.NavigationManager;
import org.ums.persistent.model.PersistentAdditionalRolePermissions;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class AdditionalRolePermissionsHelper extends
    ResourceHelper<AdditionalRolePermissions, MutableAdditionalRolePermissions, Long> {
  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Autowired
  NavigationManager mNavigationManager;

  @Autowired
  AdditionalRolePermissionsBuilder mBuilder;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableAdditionalRolePermissions mutableAdditionalRolePermissions = new PersistentAdditionalRolePermissions();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableAdditionalRolePermissions, pJsonObject, localCache);
    mAdditionalRolePermissionsManager.removeExistingAdditionalRolePermissions(
        mutableAdditionalRolePermissions.getUserId(), mutableAdditionalRolePermissions.getAssignedByUserId());
    mutableAdditionalRolePermissions.commit(false);

    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @Override
  protected AdditionalRolePermissionsManager getContentManager() {
    return mAdditionalRolePermissionsManager;
  }

  @Override
  protected AdditionalRolePermissionsBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AdditionalRolePermissions pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getUserAdditionalRolePermissionsByAssignedBy(final String pUserId, final String pAssignedBy,
      final UriInfo pUriInfo) {
    List<AdditionalRolePermissions> additionalRolePermissions =
        mAdditionalRolePermissionsManager.getUserPermissionsByAssignedUser(pUserId, pAssignedBy);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(AdditionalRolePermissions readOnly : additionalRolePermissions) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }
}
