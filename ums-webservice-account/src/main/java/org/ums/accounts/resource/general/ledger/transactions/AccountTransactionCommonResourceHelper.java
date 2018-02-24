package org.ums.accounts.resource.general.ledger.transactions;

import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ums.accounts.resource.general.ledger.AccountTransactionBuilder;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.*;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableMonthBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.voucher.number.control.ResetBasis;
import org.ums.exceptions.MisMatchException;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;
import org.ums.persistent.model.accounts.PersistentMonthBalance;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;
import org.ums.util.UmsUtils;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */

public class AccountTransactionCommonResourceHelper extends
    ResourceHelper<AccountTransaction, MutableAccountTransaction, Long> {

  @Autowired
  protected AccountTransactionManager mAccountTransactionManager;
  @Autowired
  protected AccountTransactionBuilder mAccountTransactionBuilder;
  @Autowired
  protected VoucherManager mVoucherManager;
  @Autowired
  protected FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  protected VoucherNumberControlManager mVoucherNumberControlManager;
  @Autowired
  protected CompanyManager mCompanyManager;
  @Autowired
  protected AccountManager mAccountManager;
  @Autowired
  protected AccountBalanceManager mAccountBalanceManager;
  @Autowired
  protected UserManager mUserManager;
  @Autowired
  protected IdGenerator mIdGenerator;
  @Autowired
  protected MonthBalanceManager mMonthBalanceManager;

  private enum DateCondition {
    Previous,
    Next
  }

  public TransactionResponse getVoucherNo(Long pVoucherId) throws Exception {
    Voucher voucher = mVoucherManager.get(pVoucherId);
    FinancialAccountYear openFinancialYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    Date currentDay = new Date();
    TransactionResponse transactionResponse = new TransactionResponse();
    Company usersCompany = mCompanyManager.get("01");
    if (currentDay.after(getPreviousDate(openFinancialYear.getCurrentStartDate(), DateCondition.Previous))
        && currentDay.before(getPreviousDate(openFinancialYear.getCurrentEndDate(), DateCondition.Next))) {
      VoucherNumberControl voucherNumberControl = mVoucherNumberControlManager.getByVoucher(voucher, usersCompany);
      Calendar calendar = Calendar.getInstance();
      Date currentDate = new Date();
      calendar.setTime(currentDate);
      return createVoucherNumber(voucher, transactionResponse, voucherNumberControl, calendar, currentDate);

    } else {
      transactionResponse.setMessage("Current year is not opened");
      transactionResponse.setVoucherNo("");
      return transactionResponse;
    }
  }

  @NotNull
  private TransactionResponse createVoucherNumber(Voucher pVoucher, TransactionResponse pTransactionResponse,
                                                  VoucherNumberControl pVoucherNumberControl, Calendar pCalendar, Date pCurrentDate) throws Exception {
    if (pVoucherNumberControl.getResetBasis().equals(ResetBasis.YEARLY)) {
      Date firstDate = UmsUtils.convertToDate("01-01-" + pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
      Date lastDate = UmsUtils.convertToDate("31-12-" + pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
      return getVoucherNumber(pVoucher, pTransactionResponse, firstDate, lastDate);
    } else if (pVoucherNumberControl.getResetBasis().equals(ResetBasis.MONTHLY)) {
      Date firstDate =
          UmsUtils.convertToDate("01-" + pCalendar.get(Calendar.MONTH) + "-" + pCalendar.get(Calendar.YEAR),
              "dd-MM-yyyy");
      Date lastDate =
          UmsUtils.convertToDate(pCalendar.get(Calendar.DAY_OF_MONTH) + "-" + pCalendar.get(Calendar.MONTH) + "-"
              + pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
      return getVoucherNumber(pVoucher, pTransactionResponse, firstDate, lastDate);
    } else if (pVoucherNumberControl.getResetBasis().equals(ResetBasis.WEEKLY)) {
      pCalendar.set(Calendar.DAY_OF_WEEK, pCalendar.getFirstDayOfWeek());
      Date firstDate = pCalendar.getTime();
      pCalendar.setTime(pCurrentDate);
      pCalendar.set(Calendar.DAY_OF_WEEK, pCalendar.getFirstDayOfWeek() + 6);
      Date lastDate = pCalendar.getTime();
      return getVoucherNumber(pVoucher, pTransactionResponse, firstDate, lastDate);
    } else if (pVoucherNumberControl.getResetBasis().equals(ResetBasis.DAILY)) {
      return getVoucherNumber(pVoucher, pTransactionResponse, pCurrentDate, pCurrentDate);
    } else {
      Integer nextVoucherNumber = mAccountTransactionManager.getTotalVoucherNumberBasedOnCurrentDay(pVoucher) + 1;
      return getVoucherNumberGenerationResponse(pVoucher, pTransactionResponse, nextVoucherNumber);
    }
  }

  private TransactionResponse getVoucherNumber(Voucher pVoucher, TransactionResponse pTransactionResponse,
                                               Date pFirstDate, Date pLastDate) {
    Integer nextVoucher = getContentManager().getVoucherNumber(pVoucher, pFirstDate, pLastDate) + 1;
    return getVoucherNumberGenerationResponse(pVoucher, pTransactionResponse, nextVoucher);
  }

  @NotNull
  private TransactionResponse getVoucherNumberGenerationResponse(Voucher pVoucher,
                                                                 TransactionResponse pTransactionResponse, Integer pNextVoucher) {
    String voucherNumber = generateVoucherNumber(pVoucher, pNextVoucher);
    pTransactionResponse.setMessage("");
    pTransactionResponse.setVoucherNo(voucherNumber);
    return pTransactionResponse;
  }

  @NotNull
  private String generateVoucherNumber(Voucher pVoucher, Integer pNextVoucher) {
    String voucherNumber = "" + pNextVoucher;
    for (int i = 0; i < (6 - pNextVoucher.toString().length()); i++) {
      voucherNumber = "0" + voucherNumber;
    }
    voucherNumber = pVoucher.getShortName() + voucherNumber;
    return voucherNumber;
  }

  private Date getPreviousDate(Date pDate, DateCondition pDateCondition) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(pDate);
    if (pDateCondition.equals(DateCondition.Previous))
      calendar.add(Calendar.DATE, -1);
    else
      calendar.add(Calendar.DATE, +1);
    return calendar.getTime();
  }

  @Transactional
  public List<AccountTransaction> save(JsonArray pJsonValues) throws Exception {
    List<MutableAccountTransaction> transactions = createTransactions(pJsonValues);
    return new ArrayList<>(transactions);
  }

  public PaginatedVouchers getAll(int itemPerPage, int pageNumber, String voucherNO, Long voucherId) {
    Voucher voucher = mVoucherManager.get(voucherId);
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

    for (int i = 0; i < pJsonValues.size(); i++) {
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
    updateAccountBalance(newTransactions);
    return new ArrayList<>(newTransactions);
  }

  private void updateAccountBalance(List<MutableAccountTransaction> pTransactions) {
    FinancialAccountYear currentFinancialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    List<Account> accounts = new ArrayList<>();
    Map<Long, MutableAccountTransaction> accountMapWithTransaction = new HashMap<>();
    BigDecimal totalDebit = BigDecimal.valueOf(0.00);
    BigDecimal totalCredit = BigDecimal.valueOf(0.00);
    for (MutableAccountTransaction a : pTransactions) {
      accounts.add(a.getAccount());
      accountMapWithTransaction.put(a.getAccount().getId(), a);

      if (a.getBalanceType().equals(BalanceType.Cr))
        totalCredit = totalCredit.add(a.getAmount());
      else
        totalDebit = totalDebit.add(a.getAmount());
    }
    if (!totalCredit.equals(totalDebit))
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
    List<MutableMonthBalance> existingMonthBalance = mMonthBalanceManager.getExistingMonthBalanceBasedOnAccountBalance(pAccountBalanceList, new Long(month));
    Map<Long, MutableMonthBalance> accountBalanceIdMapWithMonthBalance = existingMonthBalance.stream()
        .collect(Collectors.toMap(i -> i.getAccountBalanceId(), i -> i));
    List<MutableMonthBalance> newMonthBalance = new ArrayList<>();
    List<MutableMonthBalance> updatedMonthBalance = new ArrayList<>();
    pAccountBalanceList.forEach(a -> {
      MutableAccountTransaction transaction = pAccountMapWithTransaction.get(a.getAccountCode());
      MutableMonthBalance monthBalance = new PersistentMonthBalance();
      monthBalance = initializeMonthBalance(month, accountBalanceIdMapWithMonthBalance, a, monthBalance);
      adjustTotalDebitOrCreditBalance(transaction, monthBalance);
      if (accountBalanceIdMapWithMonthBalance.containsKey(a.getId()))
        updatedMonthBalance.add(monthBalance);
      else
        newMonthBalance.add(monthBalance);
    });

    insertNewOrUpdatedMonthBalance(newMonthBalance, updatedMonthBalance);
  }

  private void insertNewOrUpdatedMonthBalance(List<MutableMonthBalance> pNewMonthBalance,
                                              List<MutableMonthBalance> pUpdatedMonthBalance) {
    if (pNewMonthBalance.size() > 0)
      mMonthBalanceManager.create(pNewMonthBalance);
    if (pUpdatedMonthBalance.size() > 0)
      mMonthBalanceManager.update(pUpdatedMonthBalance);
  }

  private void adjustTotalDebitOrCreditBalance(MutableAccountTransaction pTransaction, MutableMonthBalance pMonthBalance) {
    if (pTransaction.getBalanceType().equals(BalanceType.Cr)) {
      pMonthBalance.setTotalMonthCreditBalance(pMonthBalance.getTotalMonthCreditBalance() == null ? pTransaction
          .getAmount() : pMonthBalance.getTotalMonthCreditBalance().add(pTransaction.getAmount()));
      pMonthBalance.setTotalMonthDebitBalance(pMonthBalance.getTotalMonthDebitBalance() == null ? new BigDecimal(0)
          : pMonthBalance.getTotalMonthDebitBalance());
    } else {
      pMonthBalance.setTotalMonthDebitBalance(pMonthBalance.getTotalMonthDebitBalance() == null ? pTransaction
          .getAmount() : pMonthBalance.getTotalMonthDebitBalance().add(pTransaction.getAmount()));
      pMonthBalance.setTotalMonthCreditBalance(pMonthBalance.getTotalMonthCreditBalance() == null ? new BigDecimal(0)
          : pMonthBalance.getTotalMonthCreditBalance());
    }

  }

  private MutableMonthBalance initializeMonthBalance(int pMonth,
                                                     Map<Long, MutableMonthBalance> pAccountBalanceIdMapWithMonthBalance, MutableAccountBalance a,
                                                     MutableMonthBalance pMonthBalance) {
    if (pAccountBalanceIdMapWithMonthBalance.containsKey(a.getId())) {
      pMonthBalance = pAccountBalanceIdMapWithMonthBalance.get(a.getId());
    } else {
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
    for (MutableAccountBalance a : pAccountBalanceList) {
      MutableAccountTransaction accountTransaction = pAccountMapWithTransaction.get(a.getAccountCode());
      if (accountTransaction.getBalanceType().equals(BalanceType.Cr))
        a.setTotCreditTrans(a.getTotCreditTrans() == null ? accountTransaction.getAmount() : a.getTotCreditTrans().add(
            accountTransaction.getAmount()));
      else
        a.setTotDebitTrans(a.getTotDebitTrans() == null ? accountTransaction.getAmount() : a.getTotDebitTrans().add(
            accountTransaction.getAmount()));
    }
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  public AccountTransactionManager getContentManager() {
    return mAccountTransactionManager;
  }

  @Override
  protected Builder<AccountTransaction, MutableAccountTransaction> getBuilder() {
    return mAccountTransactionBuilder;
  }

  @Override
  protected String getETag(AccountTransaction pReadonly) {
    return null;
  }
}
