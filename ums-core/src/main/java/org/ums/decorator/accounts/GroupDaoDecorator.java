package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Company;
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
  public List<Group> getAll(Company pCompany) {
    return getManager().getAll(pCompany);
  }

  @Override
  public List<Group> getByMainGroup(Group pGroup, Company pCompany) {
    return getManager().getByMainGroup(pGroup, pCompany);
  }

  @Override
  public List<Group> getExcludingMainGroupList(List<String> pMainGroupCodeList, Company pCompany) {
    return getManager().getExcludingMainGroupList(pMainGroupCodeList, pCompany);
  }

  @Override
  public List<Group> getIncludingMainGroupList(List<String> pMainGroupCodeList, Company pCompany) {
    return getManager().getIncludingMainGroupList(pMainGroupCodeList, pCompany);
  }
}
