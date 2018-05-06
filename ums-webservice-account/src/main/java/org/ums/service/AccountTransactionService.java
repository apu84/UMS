package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.accounts.resource.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.general.ledger.transactions.journal.voucher.JournalVoucherResourceHelper;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.currency.CurrencyFlag;
import org.ums.enums.accounts.definitions.voucher.number.control.VoucherType;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.manager.accounts.CurrencyManager;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 23-Mar-18.
 */
@Service
public class AccountTransactionService {
  @Autowired
  private AccountTransactionManager accountTransactionManager;
  @Autowired
  private CurrencyManager currencyManager;
  @Autowired
  private AccountService accountService;
  @Autowired
  private JournalVoucherResourceHelper journalVoucherResourceHelper;

  public BigDecimal getTotalBalance(final List<AccountTransaction> accountTransactions){
        BigDecimal balance = new BigDecimal(0);
        accountTransactions.forEach(t->{
            if(t.getBalanceType().equals(BalanceType.Dr))
                balance.add(t.getAmount());
            else
                balance.subtract(t.getAmount());
        });
        return balance;
    }

  public void createOpeningBalanceJournalEntry(Account account, AccountBalance accountBalance) throws Exception{
        PersistentAccountTransaction newAccountJournal = new PersistentAccountTransaction();
        newAccountJournal.setNarration("Opening Balance Journal Entry");
        newAccountJournal.setVoucherDate(new Date());
        newAccountJournal.setAccountId(account.getId());
        newAccountJournal.setAmount(accountBalance.getYearOpenBalance());
        newAccountJournal.setBalanceType(accountBalance.getYearOpenBalanceType());
        Currency baseCurrency = currencyManager.getAll()
                .stream()
                .filter(c->c.getCurrencyFlag().equals(CurrencyFlag.BASE_CURRENCY)).collect(Collectors.toList()).get(0);
        newAccountJournal.setCurrencyId(baseCurrency.getId());
        newAccountJournal.setConversionFactor(new BigDecimal(1));
        newAccountJournal.setSerialNo(1);
        newAccountJournal.setVoucherId(VoucherType.JOURNAL_VOUCHER.getId());

        PersistentAccountTransaction openingBalanceTransaction = new PersistentAccountTransaction();
        openingBalanceTransaction.setNarration("Opening Balance Journal Entry");
        openingBalanceTransaction.setVoucherDate(newAccountJournal.getVoucherDate());
        openingBalanceTransaction.setAccountId(accountService.getOpeningBalanceAdjustmentAccount().getId());
        openingBalanceTransaction.setAmount(accountBalance.getYearOpenBalance());
        openingBalanceTransaction.setBalanceType(accountBalance.getYearOpenBalanceType().equals(BalanceType.Dr)? BalanceType.Cr: BalanceType.Dr);
        openingBalanceTransaction.setCurrencyId(baseCurrency.getId());
        openingBalanceTransaction.setConversionFactor(new BigDecimal(1));
        openingBalanceTransaction.setSerialNo(2);
        openingBalanceTransaction.setVoucherId(VoucherType.JOURNAL_VOUCHER.getId());

        List<PersistentAccountTransaction> journalEntries = new ArrayList<>();
        journalEntries.add(newAccountJournal);
        journalEntries.add(openingBalanceTransaction);

        journalVoucherResourceHelper.postTransactions(journalEntries);
    }
}
