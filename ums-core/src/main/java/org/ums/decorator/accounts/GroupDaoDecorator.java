package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.accounts.GroupManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public class GroupDaoDecorator extends ContentDaoDecorator<Group, MutableGroup, Long, GroupManager> implements
    GroupManager {

  @Override
  public List<Group> getGroups(Group pGroup) {
    return getManager().getGroups(pGroup);
  }

  @Override
  public List<Group> getExcludingMainGroupList(List<String> pMainGroupCodeList) {
    return getManager().getExcludingMainGroupList(pMainGroupCodeList);
  }

  @Override
  public List<Group> getIncludingMainGroupList(List<String> pMainGroupCodeList) {
    return getManager().getIncludingMainGroupList(pMainGroupCodeList);
  }
}
