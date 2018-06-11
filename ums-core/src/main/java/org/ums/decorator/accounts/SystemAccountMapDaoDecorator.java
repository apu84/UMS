package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.enums.accounts.definitions.account.balance.AccountType;
import org.ums.manager.accounts.SystemAccountMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
public class SystemAccountMapDaoDecorator extends
    ContentDaoDecorator<SystemAccountMap, MutableSystemAccountMap, Long, SystemAccountMapManager> implements
    SystemAccountMapManager {

  @Override
  public List<SystemAccountMap> getAll(Company pCompany) {
    return getManager().getAll(pCompany);
  }

  @Override
  public SystemAccountMap get(AccountType pAccountType, Company pCompany) {
    return getManager().get(pAccountType, pCompany);
  }
}
