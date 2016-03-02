package org.ums.common.navigation.helper;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.domain.model.readOnly.*;
import org.ums.manager.*;
import org.ums.processor.navigation.NavigationProcessor;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

@Component
public class MainNavigationHelper extends ResourceHelper<Navigation, MutableNavigation, Integer> {
  @Autowired
  NavigationManager mNavigationManager;
  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;
  @Autowired
  private List<Builder<Navigation, MutableNavigation>> mBuilders;
  @Autowired
  UserManager mUserManager;
  @Autowired
  PermissionManager mPermissionManager;
  @Autowired
  @Qualifier("navigationProcessor")
  NavigationProcessor mNavigationProcessor;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    //Do nothing
    return null;
  }

  @Override
  protected ContentManager<Navigation, MutableNavigation, Integer> getContentManager() {
    return mNavigationManager;
  }

  @Override
  protected List<Builder<Navigation, MutableNavigation>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(Navigation pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getNavigationItems(final UriInfo pUriInfo) throws Exception {
    Subject subject = SecurityUtils.getSubject();
    String userId = subject.getPrincipal().toString();
    User user = mUserManager.get(userId);

    Role primaryRole = user.getPrimaryRole();
    List<Permission> rolePermissions = mPermissionManager.getPermissionByRole(primaryRole);
    Set<String> permissions = new HashSet<>();
    for (Permission permission : rolePermissions) {
      permissions.addAll(permission.getPermissions());
    }

    JsonObjectBuilder root = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonObjectBuilder typedItems = Json.createObjectBuilder();
    typedItems.add("type", "primaryRole");
    typedItems.add("name", primaryRole.getName());
    typedItems.add("items", buildNavigation(permissions, pUriInfo));
    children.add(typedItems);

    permissions = new HashSet<>();

    List<AdditionalRolePermissions> additionalRolePermissions = mAdditionalRolePermissionsManager.getPermissionsByUser(userId);
    if (additionalRolePermissions.size() > 0) {
      for (AdditionalRolePermissions additionalRolePermission : additionalRolePermissions) {
        permissions.addAll(additionalRolePermission.getPermission());
      }

      typedItems = Json.createObjectBuilder();
      typedItems.add("type", "additionalPermission");
      typedItems.add("name", "AdditionalPermission");
      typedItems.add("items", buildNavigation(permissions, pUriInfo));
      children.add(typedItems);
    }
    root.add("entries", children);
    return root.build();
  }

  private List<Map<String, Object>> insertChildMenu(final List<Navigation> pNavigationList) throws Exception {
    List<Map<String, Object>> navigationList = new ArrayList<>();

    for (Navigation navigation : pNavigationList) {
      Map<String, Object> navigationMap = new HashMap<>();
      navigationMap.put("navigation", navigation);
      navigationMap.put("children", new ArrayList<Navigation>());
      Map<String, Object> parent = findParentNavigation(navigationList, navigation);

      if (parent != null) {
        List<Navigation> children = (List<Navigation>) parent.get("children");
        children.add(navigation);
      } else {
        navigationList.add(navigationMap);
      }
    }

    return navigationList;
  }

  private Map<String, Object> findParentNavigation(final List<Map<String, Object>> pNavigationList,
                                                   final Navigation pTargetNavigation) throws Exception {

    for (Map<String, Object> navigationMap : pNavigationList) {
      Navigation navigation = (Navigation) navigationMap.get("navigation");
      if (navigation.getId().intValue() == pTargetNavigation.getParentId().intValue()) {
        return navigationMap;
      }
    }
    return null;
  }

  private JsonArray buildNavigation(final Set<String> permissions, final UriInfo pUriInfo) throws Exception {
    LocalCache localCache = new LocalCache();
    JsonArrayBuilder items = Json.createArrayBuilder();

    List<Navigation> navigationItems = mNavigationManager.getByPermissions(permissions);
    List<Map<String, Object>> navigationMaps = insertChildMenu(navigationItems);

    for (Map<String, Object> navigationMap : navigationMaps) {
      Navigation navigation = (Navigation) navigationMap.get("navigation");

      navigation = mNavigationProcessor.process(navigation, SecurityUtils.getSubject());

      if (navigation.isActive()) {
        JsonObject jsonObject = toJson(navigation, pUriInfo, localCache);
        JsonObjectBuilder jsonObjectBuilder = toJsonObjectBuilder(jsonObject);

        List<Navigation> childNavigation = (List<Navigation>) navigationMap.get("children");
        if (childNavigation.size() > 0) {
          JsonArrayBuilder childrenNavigation = Json.createArrayBuilder();
          for (Navigation child : childNavigation) {
            childrenNavigation.add(toJson(child, pUriInfo, localCache));
          }
          jsonObjectBuilder.add("children", childrenNavigation);
        }

        items.add(jsonObjectBuilder.build());
      }
    }
    localCache.invalidate();
    return items.build();
  }

  private JsonObjectBuilder toJsonObjectBuilder(final JsonObject pJsonObject) {
    JsonObjectBuilder job = Json.createObjectBuilder();
    for (Map.Entry<String, JsonValue> entry : pJsonObject.entrySet()) {
      job.add(entry.getKey(), entry.getValue());
    }
    return job;
  }
}
