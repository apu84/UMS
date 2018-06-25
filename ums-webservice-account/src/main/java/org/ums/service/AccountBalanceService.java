package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.*;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentGroup;
import org.ums.usermanagement.user.User;
import org.ums.util.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 23-Mar-18.
 */
@Service
public class AccountBalanceService {
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;
  @Autowired
  private CurrencyManager currencyManager;
  @Autowired
  private FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private AccountService mAccountService;
  @Autowired
  private SystemGroupMapManager mSystemGroupMapManager;
  @Autowired
  private AccountManager mAccountManger;
  @Autowired
  private GroupManager mGrouopManager;

  public BigDecimal getTillLastMonthBalance(final Account pAccount, final FinancialAccountYear pFinancialAccountYear,
      final Date pDate, final AccountBalance pAccountBalance) {
    AccountBalance accountBalance = pAccountBalance;
    BigDecimal balance = accountBalance.getYearOpenBalance();
    LocalDate localDate = pDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    for(int i = 1; i <= localDate.getMonth().getValue(); i++) {
      balance = getMonthTotalBalance(MonthType.get(i), balance, accountBalance);
    }

    return balance;
  }

  private BigDecimal getMonthTotalBalance(final MonthType pMonthType, BigDecimal pBalance,
      final AccountBalance pAccountBalance) {
    if(pMonthType.equals(MonthType.JANUARY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal01().subtract(pAccountBalance.getTotMonthCrBal01()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.FEBRUARY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal02().subtract(pAccountBalance.getTotMonthCrBal02()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.MARCH)) {
      pBalance = (pAccountBalance.getTotMonthDbBal03().subtract(pAccountBalance.getTotMonthCrBal03()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.APRIL)) {
      pBalance = (pAccountBalance.getTotMonthDbBal04().subtract(pAccountBalance.getTotMonthCrBal04()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.MAY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal05().subtract(pAccountBalance.getTotMonthCrBal05()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.JUNE)) {
      pBalance = (pAccountBalance.getTotMonthDbBal06().subtract(pAccountBalance.getTotMonthCrBal06()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.JULY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal07().subtract(pAccountBalance.getTotMonthCrBal07()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.AUGUST)) {
      pBalance = (pAccountBalance.getTotMonthDbBal08().subtract(pAccountBalance.getTotMonthCrBal08()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.SEPTEMBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal09().subtract(pAccountBalance.getTotMonthCrBal09()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.OCTOBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal10().subtract(pAccountBalance.getTotMonthCrBal10()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.NOVEMBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal11().subtract(pAccountBalance.getTotMonthCrBal11()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.DECEMBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal12().subtract(pAccountBalance.getTotMonthCrBal12()).add(pBalance));
    }
    else {
      return pBalance;
    }

    return pBalance;
  }

  public MutableAccountBalance setMonthAccountBalance(MutableAccountBalance pAccountBalance, Account pAccount) {
    LocalDate localDate = pAccountBalance.getModifiedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Integer month = localDate.getMonthValue();
    if(month.equals(MonthType.JANUARY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal01(pAccountBalance.getTotMonthCrBal01() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal01().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal01(pAccountBalance.getTotMonthDbBal01() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal01().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.FEBRUARY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal02(pAccountBalance.getTotMonthCrBal02() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal02().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal02(pAccountBalance.getTotMonthDbBal02() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal02().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.MARCH.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal03(pAccountBalance.getTotMonthCrBal03() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal03().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal03(pAccountBalance.getTotMonthDbBal03() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal03().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.APRIL.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal04(pAccountBalance.getTotMonthCrBal04() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal04().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal04(pAccountBalance.getTotMonthDbBal04() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal04().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.MAY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal05(pAccountBalance.getTotMonthCrBal05() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal05().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal05(pAccountBalance.getTotMonthDbBal05() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal05().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.JUNE.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal06(pAccountBalance.getTotMonthCrBal06() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal06().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal06(pAccountBalance.getTotMonthDbBal06() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal06().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.JULY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal07(pAccountBalance.getTotMonthCrBal07() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal07().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal07(pAccountBalance.getTotMonthDbBal07() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal07().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.AUGUST.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal08(pAccountBalance.getTotMonthCrBal08() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal08().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal08(pAccountBalance.getTotMonthDbBal08() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal08().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.SEPTEMBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal09(pAccountBalance.getTotMonthCrBal09() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal09().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal09(pAccountBalance.getTotMonthDbBal09() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal09().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.OCTOBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal10(pAccountBalance.getTotMonthCrBal10() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal10().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal10(pAccountBalance.getTotMonthDbBal10() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal10().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.NOVEMBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal11(pAccountBalance.getTotMonthCrBal11() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal11().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal11(pAccountBalance.getTotMonthDbBal11() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal11().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.DECEMBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal12(pAccountBalance.getTotMonthCrBal12() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal12().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal12(pAccountBalance.getTotMonthDbBal12() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal12().add(pAccountBalance.getYearOpenBalance()));
    }

    return pAccountBalance;
  }

  @Transactional
  public MutableAccountBalance createAccountBalance(MutableAccount account, User user, MutableAccountBalance accountBalance) {
    if (accountBalance != null) {
      FinancialAccountYear financialAccountYears = mFinancialAccountYearManager.getAll().stream().filter(f -> f.getYearClosingFlag().equals(YearClosingFlagType.OPEN)).collect(Collectors.toList()).get(0);
      accountBalance.setId(mIdGenerator.getNumericId());
      accountBalance.setYearOpenBalanceType(accountBalance.getYearOpenBalanceType() == null ? BalanceType.Dr : accountBalance.getYearOpenBalanceType());
      accountBalance.setYearOpenBalance(accountBalance.getYearOpenBalance() == null ? new BigDecimal(0.00) : accountBalance.getYearOpenBalance());
      accountBalance.setFinStartDate(financialAccountYears.getCurrentStartDate());
      accountBalance.setFinEndDate(financialAccountYears.getCurrentEndDate());
      accountBalance.setAccountCode(((PersistentAccount) account).getId());

      accountBalance.setModifiedBy(user.getEmployeeId());
      accountBalance.setModifiedDate(new Date());
      BigDecimal openingBalance = accountBalance.getYearOpenBalance();
      Account openingBalanceAdjustmentAccount = mAccountService.getOpeningBalanceAdjustmentAccount();
      if (!account.getId().equals(openingBalanceAdjustmentAccount.getId()))
        accountBalance.setYearOpenBalance(new BigDecimal(0));
      accountBalance.setTotDebitTrans(accountBalance.getYearOpenBalance() == null ? new BigDecimal(0.00) : accountBalance.getYearOpenBalance());
      accountBalance.setTotCreditTrans(new BigDecimal(0.000));
      accountBalance = setMonthAccountBalance(accountBalance, account);
      mAccountBalanceManager.insertFromAccount(accountBalance);
      accountBalance.setYearOpenBalance(openingBalance);
    }
    return accountBalance;
  }

  @Transactional
  public List<MutableAccountBalance> transferAccountBalanceToNewAcademicYear(FinancialAccountYear pNewFinancialAccountYear) {

    Company company = Utils.getCompany();
    Group incomeGroup = new PersistentGroup();
    Group expenditureGroup = new PersistentGroup();
    Group assetGroup = new PersistentGroup();
    Group liabilitiesGroup = new PersistentGroup();

    List<SystemGroupMap> systemGroupMapList = mSystemGroupMapManager.getAllByCompany(company);
    for (SystemGroupMap systemGroupMap : systemGroupMapList) {
      if (systemGroupMap.getGroupType().equals(GroupType.INCOME))
        incomeGroup = systemGroupMap.getGroup();
      if (systemGroupMap.getGroupType().equals(GroupType.EXPENSES))
        expenditureGroup = systemGroupMap.getGroup();
      if (systemGroupMap.getGroupType().equals(GroupType.ASSETS))
        assetGroup = systemGroupMap.getGroup();
      if (systemGroupMap.getGroupType().equals(GroupType.LIABILITIES))
        liabilitiesGroup = systemGroupMap.getGroup();
    }

    List<Group> incomeGroupList = mGrouopManager.getIncludingMainGroupList(Arrays.asList(incomeGroup.getGroupCode()), Utils.getCompany());
    List<Group> expenditureGroupList = mGrouopManager.getIncludingMainGroupList(Arrays.asList(expenditureGroup.getGroupCode()), Utils.getCompany());
    List<Group> assetGroupList = mGrouopManager.getIncludingMainGroupList(Arrays.asList(assetGroup.getGroupCode()), Utils.getCompany());
    List<Group> liabilitiesGroupList = mGrouopManager.getIncludingMainGroupList(Arrays.asList(liabilitiesGroup.getGroupCode()), Utils.getCompany());

    List<Account> incomeRelatedAccountList =
        mAccountManger.getIncludingGroups(incomeGroupList.stream().map(g -> g.getGroupCode()).collect(Collectors.toList()), Utils.getCompany());
    List<Account> expenditureRelatedAccountList =
        mAccountManger.getIncludingGroups(expenditureGroupList.stream().map(g -> g.getGroupCode()).collect(Collectors.toList()), Utils.getCompany());
    List<Account> assetRelatedAccountList =
        mAccountManger.getIncludingGroups(assetGroupList.stream().map(g -> g.getGroupCode()).collect(Collectors.toList()), Utils.getCompany());
    List<Account> liabilitiesRelatedAccountList =
        mAccountManger.getIncludingGroups(liabilitiesGroupList.stream().map(g -> g.getGroupCode()).collect(Collectors.toList()), Utils.getCompany());

    List<Account> combinedAccountList = new ArrayList<>();
    combinedAccountList.addAll(incomeRelatedAccountList);
    combinedAccountList.addAll(expenditureRelatedAccountList);
    combinedAccountList.addAll(assetRelatedAccountList);
    combinedAccountList.addAll(liabilitiesRelatedAccountList);

    List<MutableAccountBalance> accountBalanceList = mAccountBalanceManager.getAccountBalance(pNewFinancialAccountYear.getPreviousStartDate(), pNewFinancialAccountYear.getPreviousEndDate(), combinedAccountList);
    Map<Long, MutableAccountBalance> accountBalanceMapWithAccountId =
        accountBalanceList
            .parallelStream()
            .collect(Collectors.toMap(a -> a.getAccountCode(), a -> a));

    List<MutableAccountBalance> transferredAssetAndLiabilitiesAccountBalanceList = transferAssetRelatedAccountsToNewYearBasedAccountBalance(assetRelatedAccountList, liabilitiesRelatedAccountList, accountBalanceMapWithAccountId, pNewFinancialAccountYear);
    List<MutableAccountBalance> transferredIncomeAndExpenditureAccountBalanceList = transferIncomeAndExpenditureAccountBalance(incomeRelatedAccountList, expenditureRelatedAccountList, accountBalanceMapWithAccountId, pNewFinancialAccountYear);
    transferredAssetAndLiabilitiesAccountBalanceList.addAll(transferredIncomeAndExpenditureAccountBalanceList);

    return transferredAssetAndLiabilitiesAccountBalanceList;
  }

  private List<MutableAccountBalance> transferAssetRelatedAccountsToNewYearBasedAccountBalance(
      List<Account> assetRelatedAccountList, List<Account> liabilitiesRelatedAccountList,
      Map<Long, MutableAccountBalance> accountBalanceMapWithAccountId, FinancialAccountYear financialAccountYear) {

    assetRelatedAccountList.addAll(liabilitiesRelatedAccountList);
    List<MutableAccountBalance> newTransferredAssetAndLiabilitiesAccountBalanceList = new ArrayList<>();
    for(Account account : assetRelatedAccountList) {
      MutableAccountBalance previousAccountBalance = accountBalanceMapWithAccountId.get(account.getId());
      MutableAccountBalance newAccountBalance = previousAccountBalance;
      newAccountBalance.setFinStartDate(financialAccountYear.getCurrentStartDate());
      newAccountBalance.setFinEndDate(financialAccountYear.getCurrentEndDate());
      newAccountBalance.setId(mIdGenerator.getNumericId());
      newTransferredAssetAndLiabilitiesAccountBalanceList.add(newAccountBalance);
    }
    return newTransferredAssetAndLiabilitiesAccountBalanceList;
  }

  private List<MutableAccountBalance> transferIncomeAndExpenditureAccountBalance(
      List<Account> incomeRelatedAccountList, List<Account> expenditureRelatedAccountList,
      Map<Long, MutableAccountBalance> accountBalanceMapWithId, FinancialAccountYear financialAccountYear) {
    List<MutableAccountBalance> newTransferredIncomeAccountBalance = new ArrayList<>();
    List<MutableAccountBalance> newTransferredExpenditureAccountBalance = new ArrayList<>();

    BigDecimal incomeTotalDebit = new BigDecimal(0);
    BigDecimal incomeTotalCredit = new BigDecimal(0);
    BigDecimal expenditureTotalDebit = new BigDecimal(0);
    BigDecimal expenditureTotalCredit = new BigDecimal(0);

    for(Account account : incomeRelatedAccountList) {
      MutableAccountBalance previousIncomeAccountBalance = accountBalanceMapWithId.get(account.getId());
      incomeTotalDebit = incomeTotalDebit.add(previousIncomeAccountBalance.getTotDebitTrans());
      incomeTotalCredit = incomeTotalCredit.add(previousIncomeAccountBalance.getTotCreditTrans());
      MutableAccountBalance newAccountBalance =
          initializeNewAccountBalance(previousIncomeAccountBalance, financialAccountYear);
      newTransferredIncomeAccountBalance.add(newAccountBalance);
    }

    for(Account account : expenditureRelatedAccountList) {
      MutableAccountBalance previousExpenditureAccountBalance = accountBalanceMapWithId.get(account.getId());
      expenditureTotalDebit = expenditureTotalDebit.add(previousExpenditureAccountBalance.getTotDebitTrans());
      expenditureTotalCredit = expenditureTotalCredit.add(previousExpenditureAccountBalance.getTotCreditTrans());
      MutableAccountBalance newAccountBalance =
          initializeNewAccountBalance(previousExpenditureAccountBalance, financialAccountYear);
      newTransferredExpenditureAccountBalance.add(newAccountBalance);
    }

    BigDecimal totalIncome = incomeTotalDebit.subtract(incomeTotalCredit);
    BigDecimal totalExpense = expenditureTotalDebit.subtract(expenditureTotalCredit);

    MutableAccountBalance retailEarningsAccountBalance =
        configureRetailEarningForNewFinancialAccountYear(totalIncome, totalExpense, financialAccountYear);

    newTransferredExpenditureAccountBalance.add(retailEarningsAccountBalance);
    newTransferredExpenditureAccountBalance.addAll(newTransferredIncomeAccountBalance);

    return newTransferredExpenditureAccountBalance;
  }

  private MutableAccountBalance configureRetailEarningForNewFinancialAccountYear(BigDecimal totalIncome,
      BigDecimal totalExpense, FinancialAccountYear financialAccountYear) {
    Account retailEarningsAccount = mAccountService.getRetailEarningsAccount();
    MutableAccountBalance accountBalance =
        mAccountBalanceManager.getAccountBalance(financialAccountYear.getPreviousStartDate(),
            financialAccountYear.getPreviousEndDate(), retailEarningsAccount);
    accountBalance.setFinStartDate(financialAccountYear.getCurrentStartDate());
    accountBalance.setFinEndDate(financialAccountYear.getCurrentEndDate());
    BigDecimal yearOpenBalance = totalIncome.subtract(totalExpense);
    accountBalance.setYearOpenBalance(yearOpenBalance.abs());
    accountBalance.setYearOpenBalanceType(yearOpenBalance.compareTo(new BigDecimal(0)) < 0 ? BalanceType.Cr
        : BalanceType.Dr);
    if(accountBalance.getYearOpenBalanceType().equals(BalanceType.Dr))
      accountBalance.setTotDebitTrans(accountBalance.getTotDebitTrans().add(yearOpenBalance));
    else
      accountBalance.setTotCreditTrans(accountBalance.getTotCreditTrans().add(yearOpenBalance));
    return accountBalance;
  }

  private MutableAccountBalance initializeNewAccountBalance(MutableAccountBalance mutableAccountBalance,
      FinancialAccountYear financialAccountYear) {
    mutableAccountBalance.setId(mIdGenerator.getNumericId());
    mutableAccountBalance.setFinStartDate(financialAccountYear.getCurrentStartDate());
    mutableAccountBalance.setFinEndDate(financialAccountYear.getCurrentEndDate());
    mutableAccountBalance.setYearOpenBalance(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal01(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal02(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal03(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal04(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal05(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal06(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal07(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal08(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal09(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal10(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal11(new BigDecimal(0));
    mutableAccountBalance.setTotMonthDbBal12(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal01(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal02(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal03(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal04(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal05(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal06(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal07(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal08(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal09(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal10(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal11(new BigDecimal(0));
    mutableAccountBalance.setTotMonthCrBal12(new BigDecimal(0));
    mutableAccountBalance.setTotDebitTrans(new BigDecimal(0));
    mutableAccountBalance.setTotCreditTrans(new BigDecimal(0));

    return mutableAccountBalance;
  }

  public MutableAccountBalance updateAccountBalance(MutableAccount account, User user,
      MutableAccountBalance accountBalance) {

    return null;
  }

}
