package org.ums.report.transaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.ums.manager.CompanyManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 10-Apr-18.
 */

@PrepareForTest(TransactionReportGenerator.class)
public class TransactionReportGeneratorTest {

  @InjectMocks
  private CompanyManager mCompanyManager;

  @InjectMocks
  private TransactionReportGenerator mTransactionReportGenerator;

  public static final String DEST = "G:test/transactionRepot.pdf";

  String voucherNo = "JN00001";
  Date voucherDate = new Date();
  OutputStream outputStream;

  @Before
  public void init() throws Exception {
    File file = new File(DEST);
    file.getParentFile().mkdirs();
    outputStream = new FileOutputStream(DEST);
  }

  @Test
  public void createVoucherReportTest() throws Exception {
    //mTransactionReportGenerator.createVoucherReport(voucherNo, voucherDate, outputStream);
  }
}
