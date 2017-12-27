package org.ums.accounts.resource.group;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.accounts.GroupManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 24-Dec-17.
 */

@Component
public class GroupResourceHelper extends ResourceHelper<Group, MutableGroup, Long> {
  @Autowired
  private GroupManager mGroupManager;
  @Autowired
  private GroupBuilder mBuilder;
  @Autowired
  private UserManager mUserManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject getAllGroups(UriInfo pUriInfo) {
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    List<Group> groups = getContentManager().getAll();
    groups.forEach(g -> {
      array.add(toJson(g, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
    return jsonObjectBuilder.build();
  }

  public List<Group> saveAndReturnUpdatedGroups(MutableGroup pGroup) {
    int savedGroupSize = getContentManager().getGroups(pGroup).size();
    String newGroupId = "";
    if (String.valueOf(savedGroupSize).length() == 1)
      newGroupId = pGroup.getMainGroup() + "00" + (savedGroupSize + 1);
    pGroup.setGroupCode(newGroupId);
    pGroup.setDefaultComp("01");
    pGroup.setCompCode("01");
    pGroup.setModifiedDate(new Date());
    pGroup.setModifiedBy(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    getContentManager().create(pGroup);
    return getContentManager().getAll();
  }

  public List<Group> saveGroupListAndReturnUpdatedList(List<MutableGroup> pGroups) {
    pGroups = pGroups.stream().filter(g -> g.getStringId() == null)
        .collect(Collectors.toList());
    List<Group> existingGroup = getContentManager().getAll();
    Map<String, List<Group>> groupMapWithMainGroup = pGroups.stream()
        .collect(Collectors.groupingBy(Group::getMainGroup));
    pGroups.forEach(g -> {
      String newGroupId = "";
      int savedGroupSize = groupMapWithMainGroup.get(g.getMainGroup()).size();
      if (String.valueOf(savedGroupSize).length() == 1)
        newGroupId = g.getMainGroup() + "00" + (savedGroupSize + 1);
      else if (String.valueOf(savedGroupSize).length() == 2)
        newGroupId = g.getMainGroup() + "0" + (savedGroupSize + 1);
      else
        newGroupId = g.getMainGroup() + (savedGroupSize + 1);

      g.setGroupCode(newGroupId);
      g.setModifiedDate(new Date());
      g.setModifiedBy(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    });
    getContentManager().create(pGroups);
    return getContentManager().getAll();
  }

  @Override
  protected GroupManager getContentManager() {
    return mGroupManager;
  }

  @Override
  protected Builder<Group, MutableGroup> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Group pReadonly) {
    return pReadonly.getLastModified().toString();
  }
}
