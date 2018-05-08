package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public interface SystemGroupMapManager extends ContentManager<SystemGroupMap, MutableSystemGroupMap, Long> {
  List<SystemGroupMap> getAllByCompany(Company pCompany);

  SystemGroupMap get(GroupType pGroupType, Company pCompany);

  boolean exists(GroupType pGroupType, Company pCompany);

  int delete(Group pGroup, Company pCompany);
}
