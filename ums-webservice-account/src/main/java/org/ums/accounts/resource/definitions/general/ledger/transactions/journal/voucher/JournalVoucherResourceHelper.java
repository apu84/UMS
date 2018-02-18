package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.accounts.resource.definitions.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableMonthBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.exceptions.MisMatchException;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;
import org.ums.persistent.model.accounts.PersistentMonthBalance;
import org.ums.usermanagement.user.User;
import org.ums.util.UmsUtils;

import javax.json.JsonArray;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class JournalVoucherResourceHelper extends AccountTransactionCommonResourceHelper {
  Long JOURNAL_VOUCHER = 1L;

  TransactionResponse getJournalVoucherNo() throws Exception {
    return getVoucherNo(JOURNAL_VOUCHER);
  }

  @Transactional
  public List<AccountTransaction> save(JsonArray pJsonValues) throws Exception {
    List<MutableAccountTransaction> transactions = createTransactions(pJsonValues);
    updateAccountBalance(transactions);
    return new ArrayList<>(transactions);
  }

  public PaginatedVouchers getAll(int itemPerPage, int pageNumber, String voucherNO) {
    Voucher voucher = mVoucherManager.get(JOURNAL_VOUCHER);
    List<MutableAccountTransaction> mutableAccountTransactions = new ArrayList<>();
    int totalNumber = 0;
    if (voucherNO.equals("undefined")) {
      mutableAccountTransactions = getContentManager().getAllPaginated(itemPerPage, pageNumber, voucher);
      totalNumber = getContentManager().getTotalNumber(voucher);
    } else {
      Company company = mCompanyManager.getDefaultCompany();
      voucherNO = company.getId() + voucherNO;
      mutableAccountTransactions = getContentManager().getAllPaginated(itemPerPage, pageNumber, voucher, voucherNO);
      totalNumber = getContentManager().getTotalNumber(voucher, voucherNO);
    }
    mutableAccountTransactions.forEach(a -> {
      a.setVoucherNo(a.getVoucherNo().substring(2));
    });
    PaginatedVouchers paginatedVouchers = new PaginatedVouchers();
    List<AccountTransaction> accountTransactions = new ArrayList<>(mutableAccountTransactions);
    paginatedVouchers.setVouchers(accountTransactions);
    paginatedVouchers.setTotalNumber(totalNumber);
    return paginatedVouchers;
  }

  public List<AccountTransaction> getByVoucherNoAndDate(String pVoucherNo, String pDate) throws Exception {
    Date dateObj = UmsUtils.convertToDate(pDate, "yyyy-MM-dd");
    Company company = mCompanyManager.getDefaultCompany();
    List<MutableAccountTransaction> mutableAccountTransactions = getContentManager().getByVoucherNoAndDate(company.getId() + pVoucherNo, dateObj);
    mutableAccountTransactions.forEach(a -> a.setVoucherNo(a.getVoucherNo().substring(2)));
    return new ArrayList<>(mutableAccountTransactions);
  }

  @NotNull
  private List<MutableAccountTransaction> createTransactions(JsonArray pJsonValues) throws Exception {
    List<MutableAccountTransaction> newTransactions = new ArrayList<>();
    List<MutableAccountTransaction> updateTransactions = new ArrayList<>();
    User loggedUser = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    Company company = mCompanyManager.get("01");

    for(int i = 0; i < pJsonValues.size(); i++) {
      PersistentAccountTransaction transaction = new PersistentAccountTransaction();
      mAccountTransactionBuilder.build(transaction, pJsonValues.getJsonObject(i));
      transaction.setModifiedBy(loggedUser.getEmployeeId());
      transaction.setModifiedDate(new Date());
      transaction.setVoucherDate(new Date());
      transaction.setCompanyId(company.getId());
      transaction.setVoucherNo(company.getId() + transaction.getVoucherNo());
      if (transaction.getId() == null) {
        transaction.setId(mIdGenerator.getNumericId());
        newTransactions.add(transaction);
      } else {
        updateTransactions.add(transaction);
      }
    }
    if (newTransactions.size() > 0)
      mAccountTransactionManager.create(newTransactions);
    if (updateTransactions.size() > 0)
      mAccountTransactionManager.update(updateTransactions);
    newTransactions.addAll(updateTransactions);
    return newTransactions;
  }

  public List<AccountTransaction> postTransactions(JsonArray pJsonValues) throws Exception {
    List<MutableAccountTransaction> newTransactions = new ArrayList<>();
    List<MutableAccountTransaction> updateTransactions = new ArrayList<>();
    User loggedUser = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    Company company = mCompanyManager.get("01");

    for (int i = 0; i < pJsonValues.size(); i++) {
      PersistentAccountTransaction transaction = new PersistentAccountTransaction();
      mAccountTransactionBuilder.build(transaction, pJsonValues.getJsonObject(i));
      transaction.setModifiedBy(loggedUser.getEmployeeId());
      transaction.setModifiedDate(new Date());
      transaction.setPostDate(new Date());
      transaction.setCompanyId(company.getId());
      transaction.setVoucherDate(new Date());
      transaction.setVoucherNo(company.getId() + transaction.getVoucherNo());
      if (transaction.getId() == null) {
        transaction.setId(mIdGenerator.getNumericId());
        newTransactions.add(transaction);
      } else {
        updateTransactions.add(transaction);
      }
    }
    if (newTransactions.size() > 0)
      mAccountTransactionManager.create(newTransactions);
    if (updateTransactions.size() > 0)
      mAccountTransactionManager.update(updateTransactions);
    newTransactions.addAll(updateTransactions);
    return new ArrayList<>(newTransactions);
  }

  private void updateAccountBalance(List<MutableAccountTransaction> pTransactions) {
    FinancialAccountYear currentFinancialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    List<Account> accounts = new ArrayList<>();
    Map<Long, MutableAccountTransaction> accountMapWithTransaction = new HashMap<>();
    BigDecimal totalDebit = BigDecimal.valueOf(0.00);
    BigDecimal totalCredit = BigDecimal.valueOf(0.00);
    for(MutableAccountTransaction a : pTransactions) {
      accounts.add(a.getAccount());
      accountMapWithTransaction.put(a.getAccount().getId(), a);

      if(a.getBalanceType().equals(BalanceType.Cr))
        totalCredit = totalCredit.add(a.getAmount());
      else
        totalDebit = totalDebit.add(a.getAmount());
    }
    if(!totalCredit.equals(totalDebit))
      throw new MisMatchException("Credit and Debit should be equal");

    List<MutableAccountBalance> accountBalanceList =
        generateUpdatedAccountBalance(currentFinancialAccountYear, accounts, accountMapWithTransaction);
    mAccountBalanceManager.update(accountBalanceList);

    createOrUpdateMonthBalance(accountMapWithTransaction, accountBalanceList);
  }

  private void createOrUpdateMonthBalance(Map<Long, MutableAccountTransaction> pAccountMapWithTransaction, List<MutableAccountBalance> pAccountBalanceList) {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(Calendar.MONTH);
    List<MutableMonthBalance> existingMonthBalance = mMonthBalanceManager.getExistingMonthBalanceBasedOnAccountBalance(pAccountBalanceList,new Long(month));
    Map<Long, MutableMonthBalance> accountBalanceIdMapWithMonthBalance= existingMonthBalance.stream()
        .collect(Collectors.toMap(i->i.getAccountBalanceId(), i->i));
    List<MutableMonthBalance> newMonthBalance= new ArrayList<>();
    List<MutableMonthBalance> updatedMonthBalance = new ArrayList<>();
    pAccountBalanceList.forEach(a->{
      MutableAccountTransaction transaction= pAccountMapWithTransaction.get(a.getAccountCode());
      MutableMonthBalance monthBalance = new PersistentMonthBalance();
      monthBalance = initializeMonthBalance(month, accountBalanceIdMapWithMonthBalance, a, monthBalance);
      adjustTotalDebitOrCreditBalance(transaction, monthBalance);
      if(accountBalanceIdMapWithMonthBalance.containsKey(a.getId()))
        updatedMonthBalance.add(monthBalance);
      else
        newMonthBalance.add(monthBalance);
    });

    insertNewOrUpdatedMonthBalance(newMonthBalance, updatedMonthBalance);
  }

  private void insertNewOrUpdatedMonthBalance(List<MutableMonthBalance> pNewMonthBalance,
      List<MutableMonthBalance> pUpdatedMonthBalance) {
    if(pNewMonthBalance.size() > 0)
      mMonthBalanceManager.create(pNewMonthBalance);
    if(pUpdatedMonthBalance.size() > 0)
      mMonthBalanceManager.update(pUpdatedMonthBalance);
  }

  private void adjustTotalDebitOrCreditBalance(MutableAccountTransaction pTransaction, MutableMonthBalance pMonthBalance) {
    if(pTransaction.getBalanceType().equals(BalanceType.Cr)) {
      pMonthBalance.setTotalMonthCreditBalance(pMonthBalance.getTotalMonthCreditBalance() == null ? pTransaction
          .getAmount() : pMonthBalance.getTotalMonthCreditBalance().add(pTransaction.getAmount()));
      pMonthBalance.setTotalMonthDebitBalance(pMonthBalance.getTotalMonthDebitBalance() == null ? new BigDecimal(0)
          : pMonthBalance.getTotalMonthDebitBalance());
    }

    else {
      pMonthBalance.setTotalMonthDebitBalance(pMonthBalance.getTotalMonthDebitBalance() == null ? pTransaction
          .getAmount() : pMonthBalance.getTotalMonthDebitBalance().add(pTransaction.getAmount()));
      pMonthBalance.setTotalMonthCreditBalance(pMonthBalance.getTotalMonthCreditBalance() == null ? new BigDecimal(0)
          : pMonthBalance.getTotalMonthCreditBalance());
    }

  }

  private MutableMonthBalance initializeMonthBalance(int pMonth,
      Map<Long, MutableMonthBalance> pAccountBalanceIdMapWithMonthBalance, MutableAccountBalance a,
      MutableMonthBalance pMonthBalance) {
    if(pAccountBalanceIdMapWithMonthBalance.containsKey(a.getId())) {
      pMonthBalance = pAccountBalanceIdMapWithMonthBalance.get(a.getId());
    }
    else {
      pMonthBalance.setId(mIdGenerator.getNumericId());
      pMonthBalance.setMonthId(new Long(pMonth));
      pMonthBalance.setAccountBalanceId(a.getId());
    }
    return pMonthBalance;
  }

  @NotNull
  private List<MutableAccountBalance> generateUpdatedAccountBalance(FinancialAccountYear pCurrentFinancialAccountYear,
      List<Account> pAccounts, Map<Long, MutableAccountTransaction> pAccountMapWithTransaction) {
    List<MutableAccountBalance> accountBalanceList =
        mAccountBalanceManager.getAccountBalance(pCurrentFinancialAccountYear.getCurrentStartDate(),
            pCurrentFinancialAccountYear.getCurrentEndDate(), pAccounts);

    updateTotalBalance(pAccountMapWithTransaction, accountBalanceList);

    return accountBalanceList;
  }

  private void updateTotalBalance(Map<Long, MutableAccountTransaction> pAccountMapWithTransaction,
      List<MutableAccountBalance> pAccountBalanceList) {
    for(MutableAccountBalance a : pAccountBalanceList) {
      MutableAccountTransaction accountTransaction = pAccountMapWithTransaction.get(a.getAccountCode());
      if(accountTransaction.getBalanceType().equals(BalanceType.Cr))
        a.setTotCreditTrans(a.getTotCreditTrans() == null ? accountTransaction.getAmount() : a.getTotCreditTrans().add(
            accountTransaction.getAmount()));
      else
        a.setTotDebitTrans(a.getTotDebitTrans() == null ? accountTransaction.getAmount() : a.getTotDebitTrans().add(
            accountTransaction.getAmount()));
    }
  }

}
