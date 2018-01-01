package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface AccountManager extends ContentManager<Account, MutableAccount, Long> {
  Integer getSize();

  List<Account> getAllPaginated(int itemPerPage, int pageNumber);

  List<Account> getAccounts(String pAccountName);
}
