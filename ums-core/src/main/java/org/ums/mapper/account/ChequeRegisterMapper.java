package org.ums.mapper.account;

import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;
import org.ums.persistent.model.accounts.PersistentChequeRegister;

public class ChequeRegisterMapper {
  public static PersistentChequeRegister convertAccountTransactionToChequeRegister(AccountTransaction accountTransaction) {
    PersistentChequeRegister chequeRegister = new PersistentChequeRegister();
    chequeRegister.setCompanyId(accountTransaction.getCompanyId());
    chequeRegister.setAccountTransactionId(accountTransaction.getId());
    chequeRegister.setChequeNo(accountTransaction.getChequeNo());
    chequeRegister.setChequeDate(accountTransaction.getChequeDate());
    return chequeRegister;
  }
}
