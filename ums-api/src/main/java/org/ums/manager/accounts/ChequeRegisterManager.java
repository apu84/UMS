package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Feb-18.
 */
public interface ChequeRegisterManager extends ContentManager<ChequeRegister, MutableChequeRegister, Long> {
  List<ChequeRegister> getByTransactionIdList(List<Long> pTransactionIdList);
}
