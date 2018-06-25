package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public interface GroupManager extends ContentManager<Group, MutableGroup, Long> {

  List<Group> getAll(Company pCompany);

  List<Group> getByMainGroup(Group pGroup, Company pCompany);

  List<Group> getExcludingMainGroupList(List<String> pMainGroupCodeList, Company pCompany);

  List<Group> getIncludingMainGroupList(List<String> pMainGroupCodeList, Company pCompany);
}
