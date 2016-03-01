package org.ums.common.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentAdditionalRolePermissions;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableAdditionalRolePermissions;
import org.ums.domain.model.readOnly.AdditionalRolePermissions;
import org.ums.manager.AdditionalRolePermissionsManager;
import org.ums.manager.ContentManager;
import org.ums.manager.NavigationManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class AdditionalRolePermissionsHelper extends ResourceHelper<AdditionalRolePermissions, MutableAdditionalRolePermissions, Integer> {
  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Autowired
  NavigationManager mNavigationManager;

  @Autowired
  List<Builder<AdditionalRolePermissions, MutableAdditionalRolePermissions>> mBuilders;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableAdditionalRolePermissions mutableAdditionalRolePermissions = new PersistentAdditionalRolePermissions();
    LocalCache localCache = new LocalCache();
    for (Builder<AdditionalRolePermissions, MutableAdditionalRolePermissions> builder : mBuilders) {
      builder.build(mutableAdditionalRolePermissions, pJsonObject, localCache);
    }
    mAdditionalRolePermissionsManager
        .removeExistingAdditionalRolePermissions(mutableAdditionalRolePermissions.getUserId(), mutableAdditionalRolePermissions.getAssignedByUserId());
    mutableAdditionalRolePermissions.commit(false);

    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @Override
  protected ContentManager<AdditionalRolePermissions, MutableAdditionalRolePermissions, Integer> getContentManager() {
    return mAdditionalRolePermissionsManager;
  }

  @Override
  protected List<Builder<AdditionalRolePermissions, MutableAdditionalRolePermissions>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(AdditionalRolePermissions pReadonly) {
    return pReadonly.getLastModified();
  }


  public JsonObject getUserAdditionalRolePermissionsByAssignedBy(final String pUserId,
                                                                 final String pAssignedBy,
                                                                 final UriInfo pUriInfo) throws Exception {
    List<AdditionalRolePermissions> additionalRolePermissions
        = mAdditionalRolePermissionsManager.getUserPermissionsByAssignedUser(pUserId, pAssignedBy);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (AdditionalRolePermissions readOnly : additionalRolePermissions) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }
}
