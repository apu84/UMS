package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.manager.accounts.AccountTransactionManager;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 23-Mar-18.
 */
@Service
public class AccountTransactionService {
  @Autowired
  private AccountTransactionManager accountTransactionManager;

  public BigDecimal getTotalBalance(final List<AccountTransaction> accountTransactions){
        BigDecimal balance = new BigDecimal(0);
        accountTransactions.forEach(t->{
            if(t.getBalanceType().equals(BalanceType.Dr))
                balance.add(t.getAmount());
            else
                balance.subtract(t.getAmount());
        });
        return balance;
    }
}
