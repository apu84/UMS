package org.ums.decorator;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.UserCompanyMap;
import org.ums.domain.model.mutable.MutableUserCompanyMap;
import org.ums.manager.UserCompanyMapManager;

import java.util.List;

public class UserCompanyMapDaoDecorator extends
    ContentDaoDecorator<UserCompanyMap, MutableUserCompanyMap, Long, UserCompanyMapManager> implements
    UserCompanyMapManager {

  @Override
  public List<UserCompanyMap> getCompanyList(String pUserId) {
    return getManager().getCompanyList(pUserId);
  }
}
