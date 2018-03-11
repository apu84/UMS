/*
 * package org.ums.accounts.resource.general.ledger.transactions;
 * 
 * import org.junit.Before; import org.junit.Test; import org.junit.runner.RunWith; import
 * org.powermock.core.classloader.annotations.SuppressStaticInitializationFor; import
 * org.springframework.test.context.ContextConfiguration; import
 * org.springframework.test.context.junit4.SpringJUnit4ClassRunner; import
 * org.ums.accounts.resource.
 * definitions.general.ledger.transactions.AccountTransactionCommonResourceHelper; import
 * org.ums.domain.model.immutable.accounts.FinancialAccountYear; import
 * org.ums.domain.model.immutable.accounts.Voucher; import
 * org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear; import
 * org.ums.enums.accounts.definitions.financial.account.year.BookClosingFlagType; import
 * org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType; import
 * org.ums.generator.IdGenerator; import org.ums.manager.CompanyManager; import
 * org.ums.manager.accounts.*; import
 * org.ums.persistent.model.accounts.PersistentFinancialAccountYear; import
 * org.ums.persistent.model.accounts.PersistentVoucher; import
 * org.ums.usermanagement.user.UserManager; import org.ums.util.UmsUtils;
 * 
 * import java.util.Calendar; import java.util.Date;
 * 
 * import static org.junit.Assert.assertNotNull; import static org.mockito.Mockito.mock;
 */
/**
 * Created by Monjur-E-Morshed on 19-Feb-18.
 */
/*
 * 
 * @SuppressStaticInitializationFor({"org.ums.persistent.model.accounts"})
 * 
 * @RunWith(SpringJUnit4ClassRunner.class)
 * 
 * @ContextConfiguration(locations = {"classpath:services-context.xml",
 * "classpath:integration-context.xml", "classpath:application-context-webservice-core.xml",
 * "classpath:applicationContext.xml"}) public class AccountTransactionCommonResourceHelperTest {
 * 
 * protected AccountTransactionManager mAccountTransactionManager;
 * 
 * protected AccountTransactionBuilder mAccountTransactionBuilder;
 * 
 * protected VoucherManager mVoucherManager;
 * 
 * protected FinancialAccountYearManager mFinancialAccountYearManager;
 * 
 * protected VoucherNumberControlManager mVoucherNumberControlManager;
 * 
 * protected CompanyManager mCompanyManager;
 * 
 * protected AccountManager mAccountManager;
 * 
 * protected AccountBalanceManager mAccountBalanceManager;
 * 
 * protected UserManager mUserManager;
 * 
 * protected IdGenerator mIdGenerator;
 * 
 * protected MonthBalanceManager mMonthBalanceManager;
 * 
 * protected AccountTransactionCommonResourceHelper mAccountTransactionCommonResourceHelper;
 * 
 * private Voucher voucher; private FinancialAccountYear mFinancialAccountYear;
 * 
 * @Before public void initialize() throws Exception { mAccountBalanceManager =
 * mock(AccountBalanceManager.class); mAccountTransactionManager =
 * mock(AccountTransactionManager.class); mAccountTransactionBuilder =
 * mock(AccountTransactionBuilder.class); mVoucherManager = mock(VoucherManager.class);
 * mFinancialAccountYearManager = mock(FinancialAccountYearManager.class);
 * mVoucherNumberControlManager = mock(VoucherNumberControlManager.class); mCompanyManager =
 * mock(CompanyManager.class); mAccountManager = mock(AccountManager.class); mUserManager =
 * mock(UserManager.class); mIdGenerator = mock(IdGenerator.class); mMonthBalanceManager =
 * mock(MonthBalanceManager.class); mAccountTransactionCommonResourceHelper =
 * mock(AccountTransactionCommonResourceHelper.class); initializeVoucher();
 * initializeOpenFinancialAccountYear(); }
 * 
 * @Test public void initialTest() { assertNotNull(mAccountBalanceManager);
 * assertNotNull(mAccountTransactionManager);
 * assertNotNull(mAccountTransactionCommonResourceHelper); }
 * 
 * @Test public void testIntegration() throws Exception {
 * 
 * }
 * 
 * public void initializeVoucher() { PersistentVoucher persistentVoucher = new PersistentVoucher();
 * persistentVoucher.setId(1L); persistentVoucher.setName("Journal Voucher");
 * persistentVoucher.setShortName("JN"); voucher = persistentVoucher; }
 * 
 * public void initializeOpenFinancialAccountYear() throws Exception { MutableFinancialAccountYear
 * mutableFinancialAccountYear = new PersistentFinancialAccountYear();
 * mutableFinancialAccountYear.setId(1L); Date currentdate = new Date(); Calendar calendar =
 * Calendar.getInstance(); calendar.setTime(currentdate);
 * mutableFinancialAccountYear.setCurrentStartDate(UmsUtils.convertToDate("01-01-" +
 * calendar.getWeekYear(), "dd-MM-yyyy"));
 * mutableFinancialAccountYear.setCurrentEndDate(UmsUtils.convertToDate("31-12-" +
 * calendar.getWeekYear(), "dd-MM-yyyy"));
 * mutableFinancialAccountYear.setPreviousStartDate(UmsUtils.convertToDate("01-01-" +
 * (calendar.getWeekYear() - 1), "dd-MM-yyyy"));
 * mutableFinancialAccountYear.setPreviousEndDate(UmsUtils.convertToDate("31-12-" +
 * (calendar.getWeekYear() - 1), "dd-MM-yyyy"));
 * mutableFinancialAccountYear.setBookClosingFlag(BookClosingFlagType.OPEN);
 * mutableFinancialAccountYear.setYearClosingFlag(YearClosingFlagType.OPEN);
 * mutableFinancialAccountYear.setModifiedDate(new Date());
 * mutableFinancialAccountYear.setModifiedBy("711002"); mFinancialAccountYear =
 * mutableFinancialAccountYear; }
 * 
 * }
 */
