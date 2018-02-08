package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import io.reactivex.Observable;
import javafx.beans.value.ObservableValue;
import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.accounts.resource.definitions.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.exceptions.MisMatchException;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;
import org.ums.usermanagement.user.User;

import javax.json.JsonArray;
import javax.ws.rs.core.Response;
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
  public Response save(JsonArray pJsonValues) throws Exception {
    List<MutableAccountTransaction> transactions = createTransactions(pJsonValues);
    updateAccountBalance(transactions);
    return Response.ok().build();
  }

  @NotNull
  private List<MutableAccountTransaction> createTransactions(JsonArray pJsonValues) throws Exception {
    List<MutableAccountTransaction> transactions = new ArrayList<>();
    User loggedUser = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    Company company = mCompanyManager.get("01");

    for(int i = 0; i < pJsonValues.size(); i++) {
      PersistentAccountTransaction transaction = new PersistentAccountTransaction();
      mAccountTransactionBuilder.build(transaction, pJsonValues.getJsonObject(i));
      transaction.setId(mIdGenerator.getNumericId());
      transaction.setModifiedBy(loggedUser.getEmployeeId());
      transaction.setModifiedDate(new Date());
      transaction.setPostDate(new Date());
      transaction.setVoucherDate(new Date());
      transaction.setCompanyId(company.getId());
      transaction.setVoucherNo(company.getId() + transaction.getVoucherNo());
      transactions.add(transaction);
    }
    mAccountTransactionManager.create(transactions);
    return transactions;
  }

  private void updateAccountBalance(List<MutableAccountTransaction> pTransactions) {
    FinancialAccountYear currentFinancialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    List<Account> accounts = new ArrayList<>();
    Map<Long, MutableAccountTransaction> accountMapWithTransaction = new HashMap<>();
    BigDecimal totalDebit= BigDecimal.valueOf(0.00);
    BigDecimal totalCredit = BigDecimal.valueOf(0.00);
    Observable<MutableAccountTransaction> observableTransactions = Observable.fromIterable(pTransactions);
    observableTransactions.subscribe((a)->{
      accounts.add(a.getAccount());
      accountMapWithTransaction.put(a.getAccount().getAccountCode(), a);

      if(a.getBalanceType().equals(BalanceType.CREDIT))
        totalCredit.add(a.getAmount());
      else
        totalDebit.add(a.getAmount());
    });
    if(!totalCredit.equals(totalDebit))
      throw new MisMatchException("Credit and Debit should be equal");

    List<MutableAccountBalance> accountBalanceList = generateUpdatedAccountBalance(currentFinancialAccountYear, accounts, accountMapWithTransaction);
    mAccountBalanceManager.update(accountBalanceList);
  }

  @NotNull
  private List<MutableAccountBalance> generateUpdatedAccountBalance(FinancialAccountYear pCurrentFinancialAccountYear, List<Account> pAccounts, Map<Long, MutableAccountTransaction> pAccountMapWithTransaction) {
    List<MutableAccountBalance> accountBalanceList = mAccountBalanceManager.getAccountBalance(pCurrentFinancialAccountYear.getCurrentStartDate(), pCurrentFinancialAccountYear.getCurrentEndDate(), pAccounts);
    Observable<MutableAccountBalance> accountBalanceObservable= Observable.fromIterable(accountBalanceList);
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(Calendar.MONTH);
    accountBalanceObservable.subscribe((a)->{
      MutableAccountTransaction accountTransaction = pAccountMapWithTransaction.get(a.getAccountCode());
      adjustMonthAmount(month, a, accountTransaction);
      if(accountTransaction.getBalanceType().equals(BalanceType.CREDIT))
        a.setTotCreditTrans(a.getTotCreditTrans().add(accountTransaction.getAmount()));
      else
        a.setTotDebitTrans(a.getTotDebitTrans().add(accountTransaction.getAmount()));
    });
    return accountBalanceList;
  }

  private void adjustMonthAmount(int pMonth, MutableAccountBalance a, MutableAccountTransaction pAccountTransaction) {
    if(pMonth == MonthType.JANUARY.getValue())
      a.setTotMonthDbBal01(a.getTotMonthDbBal01().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.FEBRUARY.getValue())
      a.setTotMonthDbBal02(a.getTotMonthDbBal02().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.MARCH.getValue())
      a.setTotMonthDbBal03(a.getTotMonthDbBal03().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.APRIL.getValue())
      a.setTotMonthDbBal04(a.getTotMonthDbBal04().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.MAY.getValue())
      a.setTotMonthDbBal05(a.getTotMonthDbBal05().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.JUNE.getValue())
      a.setTotMonthDbBal06(a.getTotMonthDbBal06().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.JULY.getValue())
      a.setTotMonthDbBal07(a.getTotMonthDbBal07().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.AUGUST.getValue())
      a.setTotMonthDbBal08(a.getTotMonthDbBal08().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.SEPTEMBER.getValue())
      a.setTotMonthDbBal09(a.getTotMonthDbBal09().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.OCTOBER.getValue())
      a.setTotMonthDbBal10(a.getTotMonthDbBal10().add(pAccountTransaction.getAmount()));
    else if(pMonth == MonthType.NOVEMBER.getValue())
      a.setTotMonthDbBal11(a.getTotMonthDbBal11().add(pAccountTransaction.getAmount()));
    else
      a.setTotMonthDbBal12(a.getTotMonthDbBal12().add(pAccountTransaction.getAmount()));
  }

}
