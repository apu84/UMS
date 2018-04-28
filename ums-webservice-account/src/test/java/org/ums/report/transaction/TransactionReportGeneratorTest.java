package org.ums.report.transaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ums.decorator.CompanyDaoDecorator;
import org.ums.decorator.accounts.AccountTransactionDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountTransactionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 10-Apr-18.
 */

// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"classpath:services-context.xml",
// "classpath:integration-context.xml", "classpath:application-context-webservice-core.xml",
// "classpath:applicationContext.xml"})
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class TransactionReportGeneratorTest {

  // @Configuration
  // static class TransactionReportGeneratorConfiguration{
  //
  // @Bean
  // public TransactionReportGenerator transactionReportGenerator(){
  // return new TransactionReportGenerator();
  // }
  // @Bean
  // public AccountTransactionManager accountTransactionManager(){
  // return new AccountTransactionDaoDecorator();
  // }
  //
  // @Bean
  // public CompanyManager companyManager(){
  // return new CompanyDaoDecorator();
  // }
  // }

  @Mock
  private TransactionReportGenerator transactionReportGenerator;
  @Mock
  private CompanyManager companyManager;

  public static final String DEST = "G:test/transactionRepot.pdf";

  String voucherNo = "JN00001";
  Date voucherDate = new Date();
  OutputStream outputStream;

  @Before
  public void init() throws Exception {
    File file = new File(DEST);
    file.getParentFile().mkdirs();
    outputStream = new FileOutputStream(DEST);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createVoucherReportTest() throws Exception {
    transactionReportGenerator.createVoucherReport(voucherNo, voucherDate, outputStream);
  }
}
