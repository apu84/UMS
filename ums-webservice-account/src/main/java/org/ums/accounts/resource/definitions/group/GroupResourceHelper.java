package org.ums.accounts.resource.definitions.group;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.GroupManager;
import org.ums.manager.accounts.SystemGroupMapManager;
import org.ums.resource.ResourceHelper;
import org.ums.service.GroupService;
import org.ums.usermanagement.user.UserManager;
import org.ums.util.Utils;

import javax.json.JsonObject;
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
  private GroupService mGroupService;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public List<Group> getAllGroups(UriInfo pUriInfo) {
    Company company = Utils.getCompany();
    List<Group> groups = getContentManager().getAll(company);
    if(groups.size() == 0)
      groups = mGroupService.createFundamentalGroups();
    return groups;
  }

  public List<Group> saveAndReturnUpdatedGroups(MutableGroup pGroup) {
    List<Group> groupList = getContentManager().getByMainGroup(pGroup, Utils.getCompany());
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
    pGroup.setDefaultComp(Utils.getCompany().getId());
    pGroup.setCompCode(Utils.getCompany().getId());
    pGroup.setModifiedDate(new Date());
    pGroup.setModifiedBy(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    if(pGroup.getId() == null) {
      pGroup.setId(mIdGenerator.getNumericId());
      getContentManager().create(pGroup);
    }
    else {
      getContentManager().update(pGroup);
    }
    return getContentManager().getAll(Utils.getCompany());
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
    mSystemGroupMapManager.delete(pGroup, Utils.getCompany());
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
