package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.manager.accounts.AccountTransactionManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class AccountTransactionDaoDecorator extends
    ContentDaoDecorator<AccountTransaction, MutableAccountTransaction, Long, AccountTransactionManager> implements
    AccountTransactionManager {

  @Override
  public Integer getTotalVoucherNumberBasedOnCurrentDay(Voucher pVoucher) {
    return getManager().getTotalVoucherNumberBasedOnCurrentDay(pVoucher);
  }

  @Override
  public Integer getVoucherNumber(Voucher pVoucher, Date pStartDate, Date pEndDate) {
    return getManager().getVoucherNumber(pVoucher, pStartDate, pEndDate);
  }

  @Override
  public List<MutableAccountTransaction> getAllPaginated(int itemPerPage, int pageNumber, Voucher voucher) {
    return getManager().getAllPaginated(itemPerPage, pageNumber, voucher);
  }
}
