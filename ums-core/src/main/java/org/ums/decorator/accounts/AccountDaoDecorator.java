package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.manager.accounts.AccountManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class AccountDaoDecorator extends ContentDaoDecorator<Account, MutableAccount, Long, AccountManager> implements
    AccountManager {

  @Override
  public Integer getSize() {
    return getManager().getSize();
  }

  @Override
  public List<Account> getAllPaginated(int itemPerPage, int pageNumber) {
    return getManager().getAllPaginated(itemPerPage, pageNumber);
  }

  @Override
  public List<Account> getAccounts(String pAccountName) {
    return getManager().getAccounts(pAccountName);
  }
}
