package org.ums.accounts.resource.definitions.account.helper;

import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;

public class AccountBalanceResponse {
  private PersistentAccount account;
  private PersistentAccountBalance accountBalance;

  public AccountBalanceResponse() {}

  public PersistentAccount getAccount() {
    return account;
  }

  public void setAccount(PersistentAccount account) {
    this.account = account;
  }

  public PersistentAccountBalance getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(PersistentAccountBalance accountBalance) {
    this.accountBalance = accountBalance;
  }
}
