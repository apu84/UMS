package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.enums.common.AscendingOrDescendingType;
import org.ums.manager.accounts.AccountManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class AccountDaoDecorator extends ContentDaoDecorator<Account, MutableAccount, Long, AccountManager> implements
    AccountManager {

  @Override
  public Integer getSize(Company pCompany) {
    return getManager().getSize(pCompany);
  }

  @Override
  public Account getAccount(Long pAccountCode, Company pCompany) {
    return getManager().getAccount(pAccountCode, pCompany);
  }

  @Override
  public boolean exists(Long pAccountCode, Company pCompany) {
    return getManager().exists(pAccountCode, pCompany);
  }

  @Override
  public List<Account> getAllPaginated(int itemPerPage, int pageNumber,
      AscendingOrDescendingType ascendingOrDescendingType, Company company) {
    return getManager().getAllPaginated(itemPerPage, pageNumber, ascendingOrDescendingType, company);
  }

  @Override
  public List<Account> getAccounts(String pAccountName) {
    return getManager().getAccounts(pAccountName);
  }

  @Override
  public List<Account> getAccounts(GroupFlag pGroupFlag) {
    return getManager().getAccounts(pGroupFlag);
  }

  @Override
  public List<Account> getExcludingGroups(List<String> groupCodeList, Company company) {
    return getManager().getExcludingGroups(groupCodeList, company);
  }

  @Override
  public List<Account> getIncludingGroups(List<String> groupCodeList, Company company) {
    return getManager().getIncludingGroups(groupCodeList, company);
  }

  @Override
  public List<Account> getAccounts(Company pCompany) {
    return getManager().getAccounts(pCompany);
  }
}
