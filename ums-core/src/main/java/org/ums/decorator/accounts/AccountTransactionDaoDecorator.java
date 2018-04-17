package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
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
  public List<MutableAccountTransaction> getByVoucherNoAndDate(String pVoucherNo, Date pDate) {
    return getManager().getByVoucherNoAndDate(pVoucherNo, pDate);
  }

  @Override
  public Integer getVoucherNumber(Voucher pVoucher, Date pStartDate, Date pEndDate) {
    return getManager().getVoucherNumber(pVoucher, pStartDate, pEndDate);
  }

  @Override
  public List<MutableAccountTransaction> getAllPaginated(int itemPerPage, int pageNumber, Voucher voucher) {
    return getManager().getAllPaginated(itemPerPage, pageNumber, voucher);
  }

  @Override
  public Integer getTotalNumber(Voucher pVoucher) {
    return getManager().getTotalNumber(pVoucher);
  }

  @Override
  public List<String> getVouchers(Voucher pVoucher, Date pStartDate, Date pEndDate) {
    return getManager().getVouchers(pVoucher, pStartDate, pEndDate);
  }

  @Override
  public List<MutableAccountTransaction> getByVoucherNo(String pVoucherNo) {
    return getManager().getByVoucherNo(pVoucherNo);
  }

  @Override
  public List<MutableAccountTransaction> getAllPaginated(int itemPerPage, int pageNumber, Voucher voucher,
      String voucherNo) {
    return getManager().getAllPaginated(itemPerPage, pageNumber, voucher, voucherNo);
  }

  @Override
  public Integer getTotalNumber(Voucher pVoucher, String voucherNo) {
    return getManager().getTotalNumber(pVoucher, voucherNo);
  }

  @Override
  public List<MutableAccountTransaction> getAccountTransactions(Date pFromDate, Date pToDate, Account pAccount) {
    return getManager().getAccountTransactions(pFromDate, pToDate, pAccount);
  }

  @Override
  public List<MutableAccountTransaction> getAccountTransactions(Date pFromDate, Date pToDate, List<Account> pAccounts) {
    return getManager().getAccountTransactions(pFromDate, pToDate, pAccounts);
  }

  @Override
  public List<MutableAccountTransaction> getAccountTransactions(Date pFromDate, Date pToDate) {
    return getManager().getAccountTransactions(pFromDate, pToDate);
  }
}
