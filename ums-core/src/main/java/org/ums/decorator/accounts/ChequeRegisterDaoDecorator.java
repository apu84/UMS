package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.manager.accounts.ChequeRegisterManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Feb-18.
 */
public class ChequeRegisterDaoDecorator extends
    ContentDaoDecorator<ChequeRegister, MutableChequeRegister, Long, ChequeRegisterManager> implements
    ChequeRegisterManager {
  @Override
  public List<ChequeRegister> getByTransactionIdList(List<Long> pTransactionIdList) {
    return getManager().getByTransactionIdList(pTransactionIdList);
  }
}
