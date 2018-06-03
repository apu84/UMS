package org.ums.accounts.resource.definitions.group;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.GroupManager;
import org.ums.manager.accounts.SystemGroupMapManager;
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
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private SystemGroupMapManager mSystemGroupMapManager;
  @Autowired
  private CompanyManager mCompanyManager;

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
    List<Group> groupList = getContentManager().getByMainGroup(pGroup);
    int savedGroupSize = groupList.size();
    String newGroupId = "";
    newGroupId = getNewGroupCodeSequence(groupList, savedGroupSize, newGroupId);
    if(newGroupId.length() == 1 && savedGroupSize != 9)
      newGroupId = pGroup.getMainGroup() + "00" + newGroupId;
    else if(newGroupId.length() == 2) {
      newGroupId = pGroup.getMainGroup() + "0" + newGroupId;
    }
    else {
      newGroupId = pGroup.getMainGroup() + newGroupId;
    }
    pGroup.setGroupCode(newGroupId);
    pGroup.setDefaultComp("01");
    pGroup.setCompCode(mCompanyManager.getDefaultCompany().getId());
    pGroup.setModifiedDate(new Date());
    pGroup.setModifiedBy(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    if(pGroup.getId() == null) {
      pGroup.setId(mIdGenerator.getNumericId());
      getContentManager().create(pGroup);
    }
    else {
      getContentManager().update(pGroup);
    }
    return getContentManager().getAll();
  }

  private String getNewGroupCodeSequence(List<Group> pGroupList, int pSavedGroupSize, String pNewGroupId) {
    if(pSavedGroupSize > 0) {
      Group lastGroup = pGroupList.get(pGroupList.size() - 1);
      String groupCodeOfTheLastGroup = lastGroup.getGroupCode();
      groupCodeOfTheLastGroup = groupCodeOfTheLastGroup.substring(lastGroup.getMainGroup().length());
      pNewGroupId = (Integer.parseInt(groupCodeOfTheLastGroup) + 1) + "";
    }
    else {
      pNewGroupId = "1";
    }
    return pNewGroupId;
  }

  public List<Group> deleteAndReturnUpdatedGroups(MutableGroup pGroup) {
    getContentManager().delete(pGroup);
    mSystemGroupMapManager.delete(pGroup, mCompanyManager.getDefaultCompany());
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
      List<Group> groupsOfMap = groupMapWithMainGroup.get(g.getMainGroup());
      if (String.valueOf(groupsOfMap.size()).length() == 1)
        newGroupId = g.getMainGroup() + "00" + (groupsOfMap.size() + 1);
      else if (String.valueOf(groupsOfMap.size()).length() == 2)
        newGroupId = g.getMainGroup() + "0" + (groupsOfMap.size() + 1);
      else
        newGroupId = g.getMainGroup() + (groupsOfMap.size() + 1);

      g.setGroupCode(newGroupId);
      g.setModifiedDate(new Date());
      g.setModifiedBy(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
      groupsOfMap.add(g);
      groupMapWithMainGroup.put(g.getMainGroup(),groupsOfMap);
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
