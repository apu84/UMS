package org.ums.manager;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.UserCompanyMap;
import org.ums.domain.model.mutable.MutableUserCompanyMap;

import java.util.List;

public interface UserCompanyMapManager extends ContentManager<UserCompanyMap, MutableUserCompanyMap, Long> {
  List<UserCompanyMap> getCompanyList(String pUserId);
}
