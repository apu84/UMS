package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface AccountManager extends ContentManager<Account, MutableAccount, Long> {
  Integer getSize();

  List<Account> getAllPaginated(int itemPerPage, int pageNumber);

  List<Account> getAccounts(String pAccountName);

  List<Account> getExcludingGroups(List<Long> groupIdList);

  /**
   * @param pGroupFlag
   * @return will return accounts based on group flag.
   */
  List<Account> getAccounts(GroupFlag pGroupFlag);
}
