package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.cache.accounts.*;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.*;
import org.ums.persistent.dao.accounts.*;
import org.ums.statistics.JdbcTemplateFactory;
import org.ums.statistics.NamedParameterJdbcTemplateFactory;

/**
 * Created by Monjur-E-Morshed on 05-Dec-17.
 */
@Configuration
public class AccountsContext {

  @Autowired
  CacheFactory mCacheFactory;
  @Autowired
  JdbcTemplateFactory mTemplateFactory;
  @Autowired
  IdGenerator mIdGenerator;
  @Autowired
  NamedParameterJdbcTemplateFactory mNamedParameterJdbcTemplateFactory;
  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Bean
  GroupManager groupManager() {
    return new PersistentGroupDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator);
  }

  @Bean
  AccountManager accountManager() {
    AccountCache accountCache = new AccountCache(mCacheFactory.getCacheManager());
    accountCache.setManager(new PersistentAccountDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator));
    return accountCache;
  }

  @Bean
  MonthManager monthManager() {
    MonthCache monthCache = new MonthCache(mCacheFactory.getCacheManager());
    monthCache.setManager(new PersistentMonthDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator));
    return monthCache;
  }

  @Bean
  CurrencyManager currencyManager() {
    CurrencyCache currencyCache = new CurrencyCache(mCacheFactory.getCacheManager());
    currencyCache.setManager(new PersistentCurrencyDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator));
    return currencyCache;
  }

  @Bean
  CurrencyConversionManager currencyConversionManager() {
    CurrencyConversionCache currencyConversionCache = new CurrencyConversionCache(mCacheFactory.getCacheManager());
    currencyConversionCache.setManager(new PersistentCurrencyConversionDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator));
    return currencyConversionCache;
  }

  @Bean
  ReceiptManager receiptManager() {
    ReceiptCache receiptCache = new ReceiptCache(mCacheFactory.getCacheManager());
    receiptCache.setManager(new PersistentReceiptDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator));
    return receiptCache;
  }

  @Bean
  MonthBalanceManager monthBalanceManager() {
    return new PersistentMonthBalanceDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator);
  }

  @Bean
  PredefinedNarrationManager predefinedNarrationManager() {
    PredefinedNarrationCache predefinedNarrationCache = new PredefinedNarrationCache(mCacheFactory.getCacheManager());
    predefinedNarrationCache.setManager(new PersistentPredefinedNarrationDao(
        mTemplateFactory.getAccountsJdbcTemplate(), mNamedParameterJdbcTemplateFactory
            .getAccountNamedParameterJdbcTemplate(), mIdGenerator));
    return predefinedNarrationCache;
  }

  @Bean
  FinancialAccountYearManager financialAccountYearManager() {
    FinancialAccountYearCache financialAccountYearCache =
        new FinancialAccountYearCache(mCacheFactory.getCacheManager());
    financialAccountYearCache.setManager(new PersistentFinancialAccountYearDao(mTemplateFactory
        .getAccountsJdbcTemplate(), mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(),
        mIdGenerator));
    return financialAccountYearCache;
  }

  @Bean
  AccountBalanceManager accountBalanceManager() {
    return new PersistentAccountBalanceDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator);
  }

  @Bean(name = "accountTransactionManager")
  AccountTransactionManager accountTransactionManager() {
    return new PersistantAccountTransactionDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator);
  }

  @Bean
  ChequeRegisterManager chequeRegisterManager() {
    return new PersistentChequeRegisterDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator);
  }

  @Bean
  PeriodCloseManager periodCloseManager() {
    return new PersistentPeriodCloseDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator);
  }

  @Bean
  VoucherManager voucherManager() {
    VoucherCache voucherCache = new VoucherCache(mCacheFactory.getCacheManager());
    voucherCache.setManager(new PersistentVoucherDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator));
    return voucherCache;
  }

  @Bean
  VoucherNumberControlManager voucherNumberControlManager() {
    VoucherNumberControlCache voucherNumberControlCache =
        new VoucherNumberControlCache(mCacheFactory.getCacheManager());
    voucherNumberControlCache.setManager(new PersistentVoucherNumberControlDao(mTemplateFactory
        .getAccountsJdbcTemplate(), mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(),
        mIdGenerator));
    return voucherNumberControlCache;
  }

}
