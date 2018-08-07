package org.ums.accounts.resource.definitions.account.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.manager.ContentManager;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.resource.ResourceHelper;
import org.ums.util.Utils;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
@Component
public class AccountBalanceResourceHelper extends ResourceHelper<AccountBalance, MutableAccountBalance, Long> {

  @Autowired
  private AccountManager mAccountManager;
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;
  @Autowired
  private FinancialAccountYearManager mFinancialAccountYearManager;

  public BigDecimal getAccountBalance(final String pAccountId) {
    Account account = mAccountManager.get(Long.parseLong(pAccountId));
    FinancialAccountYear financialAccountYear =
        mFinancialAccountYearManager.getOpenedFinancialAccountYear(Utils.getCompany());
    AccountBalance accountBalance =
        mAccountBalanceManager.getAccountBalance(financialAccountYear.getCurrentStartDate(),
            financialAccountYear.getCurrentEndDate(), account);
    return accountBalance.getTotDebitTrans().subtract(accountBalance.getTotCreditTrans());
  }

  public MutableAccountBalance updateMonthlyDebitAndCreditBalance(MutableAccountBalance pAccountBalance,
      AccountTransaction pAccountTransaction, BigDecimal pAmount) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(pAccountTransaction.getVoucherDate());
    Integer month = calendar.getTime().getMonth() + 1;
    if(month.equals(MonthType.JANUARY.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal01(pAccountBalance.getTotMonthCrBal01() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal01().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal01(pAccountBalance.getTotMonthDbBal01() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal01().add(pAmount));
    }
    if(month.equals(MonthType.FEBRUARY.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal02(pAccountBalance.getTotMonthCrBal02() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal02().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal02(pAccountBalance.getTotMonthDbBal02() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal02().add(pAmount));
    }
    if(month.equals(MonthType.MARCH.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal03(pAccountBalance.getTotMonthCrBal03() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal03().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal03(pAccountBalance.getTotMonthDbBal03() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal03().add(pAmount));
    }
    if(month.equals(MonthType.APRIL.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal04(pAccountBalance.getTotMonthCrBal04() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal04().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal04(pAccountBalance.getTotMonthDbBal04() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal04().add(pAmount));
    }
    if(month.equals(MonthType.MAY.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal05(pAccountBalance.getTotMonthCrBal05() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal05().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal05(pAccountBalance.getTotMonthDbBal05() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal05().add(pAmount));
    }
    if(month.equals(MonthType.JUNE.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal06(pAccountBalance.getTotMonthCrBal06() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal06().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal06(pAccountBalance.getTotMonthDbBal06() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal06().add(pAmount));
    }
    if(month.equals(MonthType.JULY.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal07(pAccountBalance.getTotMonthCrBal07() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal07().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal07(pAccountBalance.getTotMonthDbBal07() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal07().add(pAmount));
    }
    if(month.equals(MonthType.AUGUST.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal08(pAccountBalance.getTotMonthCrBal08() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal08().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal08(pAccountBalance.getTotMonthDbBal08() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal08().add(pAmount));
    }
    if(month.equals(MonthType.SEPTEMBER.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal09(pAccountBalance.getTotMonthCrBal09() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal09().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal09(pAccountBalance.getTotMonthDbBal09() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal09().add(pAmount));
    }
    if(month.equals(MonthType.OCTOBER.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal10(pAccountBalance.getTotMonthCrBal10() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal10().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal10(pAccountBalance.getTotMonthDbBal10() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal10().add(pAmount));
    }
    if(month.equals(MonthType.NOVEMBER.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal11(pAccountBalance.getTotMonthCrBal11() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal11().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal11(pAccountBalance.getTotMonthDbBal11() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal11().add(pAmount));
    }
    if(month.equals(MonthType.DECEMBER.getId())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal12(pAccountBalance.getTotMonthCrBal12() == null ? pAmount : pAccountBalance
            .getTotMonthCrBal12().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal12(pAccountBalance.getTotMonthDbBal12() == null ? pAmount : pAccountBalance
            .getTotMonthDbBal12().add(pAmount));
    }

    return pAccountBalance;
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AccountBalance, MutableAccountBalance, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<AccountBalance, MutableAccountBalance> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(AccountBalance pReadonly) {
    return null;
  }
}
