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
    FinancialAccountYear financialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
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
    if(month.equals(MonthType.JANUARY.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal01(pAccountBalance.getTotMonthCrBal01() == null ? pAmount : pAccountBalance.getTotMonthCrBal01().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal01(pAccountBalance.getTotMonthDbBal01() == null ? pAmount : pAccountBalance.getTotMonthDbBal01().add(pAmount));
    }
    if(month.equals(MonthType.FEBRUARY.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal02(pAccountBalance.getTotMonthCrBal02() == null ? pAmount : pAccountBalance.getTotMonthCrBal02().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal02(pAccountBalance.getTotMonthDbBal02() == null ? pAmount : pAccountBalance.getTotMonthDbBal02().add(pAmount));
    }
    if(month.equals(MonthType.MARCH.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal03(pAccountBalance.getTotMonthCrBal03() == null ? pAmount : pAccountBalance.getTotMonthCrBal03().add(pAmount));
      else
        pAccountBalance.setTotMonthDbBal03(pAccountBalance.getTotMonthDbBal03() == null ? pAmount : pAccountBalance.getTotMonthDbBal03().add(pAmount));
    }
    if(month.equals(MonthType.APRIL.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal04(pAmount);
      else
        pAccountBalance.setTotMonthDbBal04(pAmount);
    }
    if(month.equals(MonthType.MAY.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal05(pAmount);
      else
        pAccountBalance.setTotMonthDbBal05(pAmount);
    }
    if(month.equals(MonthType.JUNE.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal06(pAmount);
      else
        pAccountBalance.setTotMonthDbBal06(pAmount);
    }
    if(month.equals(MonthType.JULY.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal07(pAmount);
      else
        pAccountBalance.setTotMonthDbBal07(pAmount);
    }
    if(month.equals(MonthType.AUGUST.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal08(pAmount);
      else
        pAccountBalance.setTotMonthDbBal08(pAmount);
    }
    if(month.equals(MonthType.SEPTEMBER.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal09(pAmount);
      else
        pAccountBalance.setTotMonthDbBal09(pAmount);
    }
    if(month.equals(MonthType.OCTOBER.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal10(pAmount);
      else
        pAccountBalance.setTotMonthDbBal10(pAmount);
    }
    if(month.equals(MonthType.NOVEMBER.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal11(pAmount);
      else
        pAccountBalance.setTotMonthDbBal11(pAmount);
    }
    if(month.equals(MonthType.DECEMBER.getValue())) {
      if(pAccountTransaction.getBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal12(pAmount);
      else
        pAccountBalance.setTotMonthDbBal12(pAmount);
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
