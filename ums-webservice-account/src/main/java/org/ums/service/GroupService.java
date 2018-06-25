package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.GroupManager;
import org.ums.persistent.model.accounts.PersistentGroup;
import org.ums.resource.helper.UserResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 23-Jun-18.
 */
@Service
public class GroupService {

  @Autowired
  UserResourceHelper mUserResourceHelper;
  @Autowired
  IdGenerator mIdGenerator;
  @Autowired
  GroupManager mGroupManager;

  @Transactional
  public List<Group> createFundamentalGroups() {
    User user = mUserResourceHelper.getLoggedUser();
    Company company = Utils.getCompany();
    List<MutableGroup> groups = new ArrayList<>();
    MutableGroup group = new PersistentGroup();
    group.setId(mIdGenerator.getNumericId());
    group.setDisplayCode("100000");
    group.setGroupName("ASSET");
    group.setGroupCode("1");
    group.setMainGroup("0");
    group.setCompCode(company.getId());
    group.setReservedFlag("Y");
    group.setModifiedBy(user.getEmployeeId());
    group.setModifiedDate(new Date());
    groups.add(group);

    group = new PersistentGroup();
    group.setId(mIdGenerator.getNumericId());
    group.setDisplayCode("200000");
    group.setGroupName("FUNDS & LIABILITIES");
    group.setGroupCode("2");
    group.setMainGroup("0");
    group.setCompCode(company.getId());
    group.setReservedFlag("Y");
    group.setModifiedBy(user.getEmployeeId());
    group.setModifiedDate(new Date());
    groups.add(group);

    group = new PersistentGroup();
    group.setId(mIdGenerator.getNumericId());
    group.setDisplayCode("300000");
    group.setGroupName("INCOME");
    group.setGroupCode("3");
    group.setMainGroup("0");
    group.setCompCode(Utils.getCompany().getId());
    group.setReservedFlag("Y");
    group.setModifiedBy(user.getEmployeeId());
    group.setModifiedDate(new Date());
    groups.add(group);

    group = new PersistentGroup();
    group.setId(mIdGenerator.getNumericId());
    group.setDisplayCode("400000");
    group.setGroupName("EXPENDITURE");
    group.setGroupCode("4");
    group.setMainGroup("0");
    group.setCompCode(Utils.getCompany().getId());
    group.setReservedFlag("Y");
    group.setModifiedBy(user.getEmployeeId());
    group.setModifiedDate(new Date());
    groups.add(group);

    mGroupManager.create(groups);
    return new ArrayList<>(groups);
  }
}
