package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.manager.ContentManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public interface AccountTransactionManager extends ContentManager<AccountTransaction, MutableAccountTransaction, Long> {
  Integer getTotalVoucherNumberBasedOnCurrentDay(Voucher pVoucher);

  Integer getVoucherNumber(Voucher pVoucher, Date pStartDate, Date pEndDate);

  List<MutableAccountTransaction> getAllPaginated(int itemPerPage, int pageNumber, Voucher voucher);

}
