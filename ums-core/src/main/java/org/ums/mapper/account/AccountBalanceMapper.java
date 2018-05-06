package org.ums.mapper.account;

import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;

public class AccountBalanceMapper {
  public static PersistentAccountBalance convertFromAccountToAccountBalance(PersistentAccount pAccount) {

    PersistentAccountBalance persistentAccountBalance = new PersistentAccountBalance();

    return persistentAccountBalance;
  }
}
