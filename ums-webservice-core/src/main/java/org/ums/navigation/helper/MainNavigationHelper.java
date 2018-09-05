package org.ums.navigation.helper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.NavigationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Navigation;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.manager.NavigationManager;
import org.ums.processor.navigation.NavigationProcessor;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.permission.AdditionalRolePermissions;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;
import org.ums.usermanagement.permission.Permission;
import org.ums.usermanagement.permission.PermissionManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

@Component
public class MainNavigationHelper extends ResourceHelper<Navigation, MutableNavigation, Long> {
  @Autowired
  NavigationManager mNavigationManager;
  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;
  @Autowired
  private NavigationBuilder mBuilder;
  @Autowired
  UserManager mUserManager;
  @Autowired
  PermissionManager mPermissionManager;
  @Autowired
  @Qualifier("navigationProcessor")
  NavigationProcessor mNavigationProcessor;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    // Do nothing
    return null;
  }

  @Override
  protected NavigationManager getContentManager() {
    return mNavigationManager;
  }

  @Override
  protected NavigationBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Navigation pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getNavigationItems(final UriInfo pUriInfo) {
    JsonObjectBuilder root = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    Subject subject = SecurityUtils.getSubject();
    String userId = subject.getPrincipal().toString();
    User user = mUserManager.get(userId);

    Role primaryRole = user.getPrimaryRole();
    children.add(getRoleWisePermission(primaryRole, pUriInfo, "primaryRole"));

    Set<String> permissions = new HashSet<>();

    List<AdditionalRolePermissions> additionalRolePermissions =
        mAdditionalRolePermissionsManager.getPermissionsByUser(userId);
    if(additionalRolePermissions.size() > 0) {
      for(AdditionalRolePermissions additionalRolePermission : additionalRolePermissions) {
        // if there is any additional role
        if(additionalRolePermission.getRoleId() != null) {
          children.add(getRoleWisePermission(additionalRolePermission.getRole(), pUriInfo, "additionalRole"));
        }
        else {
          permissions.addAll(additionalRolePermission.getPermission());
          JsonObjectBuilder typedItems = Json.createObjectBuilder();
          typedItems.add("type", "additionalPermission");
          typedItems.add("name", "AdditionalPermission");
          typedItems.add("items", buildNavigation(permissions, pUriInfo));
          children.add(typedItems);
        }
      }
    }
    root.add("entries", children);
    return root.build();
  }

  private List<Map<String, Object>> insertChildMenu(final List<Navigation> pNavigationList) {
    List<Map<String, Object>> navigationList = new ArrayList<>();

    for(Navigation navigation : pNavigationList) {
      if(navigation.getParentId() <= 0) {
        Map<String, Object> navigationMap = new HashMap<>();
        navigationMap.put("navigation", navigation);
        navigationMap.put("children", new ArrayList<Navigation>());
        navigationList.add(navigationMap);
      }
    }

    for(Navigation navigation : pNavigationList) {
      if(navigation.getParentId() > 0) {
        Map<String, Object> parent = findParentNavigation(navigationList, navigation);
        if(parent != null) {
          List<Navigation> children = (List<Navigation>) parent.get("children");
          children.add(navigation);
        }
        else {
          Map<String, Object> navigationMap = new HashMap<>();
          navigationMap.put("navigation", navigation);
          navigationMap.put("children", new ArrayList<Navigation>());
          navigationList.add(navigationMap);
        }
      }
    }

    return navigationList;
  }

  private Map<String, Object> findParentNavigation(final List<Map<String, Object>> pNavigationList,
      final Navigation pTargetNavigation) {

    for(Map<String, Object> navigationMap : pNavigationList) {
      Navigation navigation = (Navigation) navigationMap.get("navigation");
      if(navigation.getId().intValue() == pTargetNavigation.getParentId().intValue()) {
        return navigationMap;
      }
    }
    return null;
  }

  private JsonArray buildNavigation(final Set<String> permissions, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArrayBuilder items = Json.createArrayBuilder();

    List<Navigation> navigationItems = mNavigationManager.getByPermissions(permissions);
    navigationItems.sort((s1, s2) -> s1.getViewOrder().compareTo(s2.getViewOrder()));
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
    for(Map.Entry<String, JsonValue> entry : pJsonObject.entrySet()) {
      job.add(entry.getKey(), entry.getValue());
    }
    return job;
  }

  private JsonObjectBuilder getRoleWisePermission(final Role pRole, final UriInfo pUriInfo, final String pRoleType) {
    JsonObjectBuilder typedItems = Json.createObjectBuilder();
    List<Permission> rolePermissions = mPermissionManager.getPermissionByRole(pRole);
    Set<String> permissions = new HashSet<>();
    for(Permission permission : rolePermissions) {
      permissions.addAll(permission.getPermissions());
    }

    typedItems.add("type", pRoleType);
    typedItems.add("name", pRole.getName());
    typedItems.add("label", pRole.getLabel());
    typedItems.add("items", buildNavigation(permissions, pUriInfo));
    return typedItems;
  }
}
