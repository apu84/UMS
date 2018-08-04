package org.ums.academic.resource.fee.certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.enums.FacultyType;
import org.ums.enums.accounts.definitions.account.balance.AccountType;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.voucher.number.control.VoucherType;
import org.ums.enums.common.CompanyType;
import org.ums.fee.payment.StudentPayment;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.StudentManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.manager.accounts.CurrencyManager;
import org.ums.manager.accounts.SystemAccountMapManager;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;
import org.ums.persistent.model.accounts.PersistentCurrency;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jun-18.
 */
@Service
public class CertificateFeeService {

  @Autowired
  private AccountTransactionManager mAccountTransactionManager;
  @Autowired
  private CurrencyManager mCurrencyManager;
  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private SystemAccountMapManager mSystemAccountMapManager;
  @Autowired
  private StudentManager mStudentManager;
  @Autowired
  private AccountManager mAccountManager;

  public List<PersistentAccountTransaction> createStudentPaymentJournalEntry(StudentPayment pStudentPayment,
      String pStudentId) {
    Student student = mStudentManager.get(pStudentId);
    Company company = mCompanyManager.get(CompanyType.AUST_TECHNICAL.getValue());
    Currency baseCurrency = new PersistentCurrency();
    if(student.getProgram().getFacultyId() == FacultyType.Engineering.getId())
      baseCurrency = mCurrencyManager.getBaseCurrency(mCompanyManager.get(CompanyType.AUST_TECHNICAL.getValue()));
    else
      baseCurrency = mCurrencyManager.getBaseCurrency(mCompanyManager.get(CompanyType.AUST_NON_TECHNICAL.getValue()));

    PersistentAccountTransaction studentJournalEntry = new PersistentAccountTransaction();
    studentJournalEntry.setAccountId(mAccountManager.getAccount(Long.parseLong(pStudentId), company).getId());
    studentJournalEntry.setPostDate(new Date());
    studentJournalEntry.setVoucherId(VoucherType.JOURNAL_VOUCHER.getId());
    studentJournalEntry.setAmount(pStudentPayment.getAmount());
    studentJournalEntry.setBalanceType(BalanceType.Cr);
    studentJournalEntry.setCompanyId(company.getId());
    studentJournalEntry.setNarration("Certificate Payment");
    studentJournalEntry.setVoucherDate(new Date());
    studentJournalEntry.setCurrencyId(baseCurrency.getId());
    studentJournalEntry.setConversionFactor(new BigDecimal(1));
    studentJournalEntry.setModifiedBy(pStudentId);
    studentJournalEntry.setModifiedDate(new Date());

    Account paymentAccount = new PersistentAccount();
    if(student.getProgram().getFacultyId() == FacultyType.Engineering.getId())
      paymentAccount = mSystemAccountMapManager.get(AccountType.ENGINEERING_PROGRAM_ACCOUNT, company).getAccount();
    else
      paymentAccount = mSystemAccountMapManager.get(AccountType.BUSINESS_PROGRAM_ACCOUNT, company).getAccount();

    PersistentAccountTransaction bankAccountJournalEntry = new PersistentAccountTransaction();
    bankAccountJournalEntry.setAccountId(paymentAccount.getId());
    bankAccountJournalEntry.setPostDate(new Date());
    bankAccountJournalEntry.setVoucherId(VoucherType.JOURNAL_VOUCHER.getId());
    bankAccountJournalEntry.setAmount(pStudentPayment.getAmount());
    bankAccountJournalEntry.setBalanceType(BalanceType.Dr);
    bankAccountJournalEntry.setCompanyId(company.getId());
    bankAccountJournalEntry.setNarration("Certificate Payment");
    bankAccountJournalEntry.setVoucherDate(new Date());
    bankAccountJournalEntry.setCurrencyId(baseCurrency.getId());
    bankAccountJournalEntry.setConversionFactor(new BigDecimal(1));
    bankAccountJournalEntry.setModifiedBy(pStudentId);
    bankAccountJournalEntry.setModifiedDate(new Date());

    return Arrays.asList(studentJournalEntry, bankAccountJournalEntry);
  }
}
