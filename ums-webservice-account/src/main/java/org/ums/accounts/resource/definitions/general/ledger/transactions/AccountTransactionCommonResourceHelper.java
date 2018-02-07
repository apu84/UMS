package org.ums.accounts.resource.definitions.general.ledger.transactions;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.TransactionResponse;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.enums.accounts.definitions.voucher.number.control.ResetBasis;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.resource.ResourceHelper;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Calendar;
import java.util.Date;

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
    if(currentDay.after(getPreviousDate(openFinancialYear.getCurrentStartDate(), DateCondition.Previous))
        && currentDay.before(getPreviousDate(openFinancialYear.getCurrentEndDate(), DateCondition.Next))) {
      VoucherNumberControl voucherNumberControl = mVoucherNumberControlManager.getByVoucher(voucher, usersCompany);
      Calendar calendar = Calendar.getInstance();
      Date currentDate = new Date();
      calendar.setTime(currentDate);
      return createVoucherNumber(voucher, transactionResponse, voucherNumberControl, calendar, currentDate);

    }
    else {
      transactionResponse.setMessage("Current year is not opened");
      transactionResponse.setVoucherNo("");
      return transactionResponse;
    }
  }

  @NotNull
  private TransactionResponse createVoucherNumber(Voucher pVoucher, TransactionResponse pTransactionResponse,
      VoucherNumberControl pVoucherNumberControl, Calendar pCalendar, Date pCurrentDate) throws Exception {
    if(pVoucherNumberControl.getResetBasis().equals(ResetBasis.YEARLY)) {
      Date firstDate = UmsUtils.convertToDate("01-01-" + pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
      Date lastDate = UmsUtils.convertToDate("31-12-" + pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
      return getVoucherNumber(pVoucher, pTransactionResponse, firstDate, lastDate);
    }
    else if(pVoucherNumberControl.getResetBasis().equals(ResetBasis.MONTHLY)) {
      Date firstDate =
          UmsUtils.convertToDate("01-" + pCalendar.get(Calendar.MONTH) + "-" + pCalendar.get(Calendar.YEAR),
              "dd-MM-yyyy");
      Date lastDate =
          UmsUtils.convertToDate(pCalendar.get(Calendar.DAY_OF_MONTH) + "-" + pCalendar.get(Calendar.MONTH) + "-"
              + pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
      return getVoucherNumber(pVoucher, pTransactionResponse, firstDate, lastDate);
    }
    else if(pVoucherNumberControl.getResetBasis().equals(ResetBasis.WEEKLY)) {
      pCalendar.set(Calendar.DAY_OF_WEEK, pCalendar.getFirstDayOfWeek());
      Date firstDate = pCalendar.getTime();
      pCalendar.setTime(pCurrentDate);
      pCalendar.set(Calendar.DAY_OF_WEEK, pCalendar.getFirstDayOfWeek() + 6);
      Date lastDate = pCalendar.getTime();
      return getVoucherNumber(pVoucher, pTransactionResponse, firstDate, lastDate);
    }
    else if(pVoucherNumberControl.getResetBasis().equals(ResetBasis.DAILY)) {
      return getVoucherNumber(pVoucher, pTransactionResponse, pCurrentDate, pCurrentDate);
    }
    else {
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
    for(int i = 0; i < (8 - pNextVoucher.toString().length()); i++) {
      voucherNumber = "0" + voucherNumber;
    }
    voucherNumber = pVoucher.getShortName() + voucherNumber;
    return voucherNumber;
  }

  private Date getPreviousDate(Date pDate, DateCondition pDateCondition) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(pDate);
    if(pDateCondition.equals(DateCondition.Previous))
      calendar.add(Calendar.DATE, -1);
    else
      calendar.add(Calendar.DATE, +1);
    return calendar.getTime();
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
