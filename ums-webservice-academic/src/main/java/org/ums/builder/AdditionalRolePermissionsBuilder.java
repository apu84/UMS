package org.ums.builder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.*;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdditionalRolePermissions;
import org.ums.domain.model.immutable.Navigation;
import org.ums.domain.model.mutable.MutableAdditionalRolePermissions;
import org.ums.formatter.DateFormat;
import org.ums.manager.NavigationManager;

@Component
public class AdditionalRolePermissionsBuilder implements
    Builder<AdditionalRolePermissions, MutableAdditionalRolePermissions> {
  @Autowired
  NavigationManager mNavigationManager;
  @Autowired
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, AdditionalRolePermissions pReadOnly,
      UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("userId", pReadOnly.getUserId());

    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    List<Navigation> navigationList =
        mNavigationManager.getByPermissions(pReadOnly.getPermission());
    for(Navigation navigation : navigationList) {
      jsonArrayBuilder.add(navigation.getId());
    }
    jsonObjectBuilder.add("entries", jsonArrayBuilder);
    pBuilder.add("permissions", jsonObjectBuilder.build());
    pBuilder.add("start", mDateFormat.format(pReadOnly.getValidFrom()));
    pBuilder.add("end", mDateFormat.format(pReadOnly.getValidTo()));
  }

  @Override
  public void build(MutableAdditionalRolePermissions pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {
    pMutable.setUserId(pJsonObject.getString("user"));
    pMutable.setAssignedByUserId(SecurityUtils.getSubject().getPrincipal().toString());
    if(pJsonObject.containsKey("start") && !StringUtils.isEmpty(pJsonObject.getString("start"))) {
      pMutable.setValidFrom(mDateFormat.parse(pJsonObject.getString("start")));
    }
    if(pJsonObject.containsKey("end") && !StringUtils.isEmpty(pJsonObject.getString("end"))) {
      pMutable.setValidTo(mDateFormat.parse(pJsonObject.getString("end")));
    }
    JsonArray permissions = pJsonObject.getJsonArray("permissions");
    Set<Integer> permissionSet = new HashSet<>();
    for(int i = 0; i < permissions.size(); i++) {
      permissionSet.add(Integer.parseInt(permissions.getString(i)));
    }

    List<Navigation> navigationList = mNavigationManager.getByPermissionsId(permissionSet);
    Set<String> permissionStringSet = new HashSet<>();
    for(Navigation navigation : navigationList) {
      permissionStringSet.add(navigation.getPermission());
    }
    pMutable.setPermission(permissionStringSet);
    pMutable.setActive(true);
  }
}
