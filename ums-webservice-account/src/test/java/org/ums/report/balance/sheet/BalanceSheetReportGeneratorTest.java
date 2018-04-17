package org.ums.report.balance.sheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Monjur-E-Morshed on 31-Mar-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services-context.xml", "classpath:integration-context.xml",
    "classpath:application-context.xml", "classpath:application-context-webservice-core.xml",
    "classpath:applicationContext.xml"})
@PrepareForTest(BalanceSheetReportGenerator.class)
public class BalanceSheetReportGeneratorTest {
  @Autowired
  private BalanceSheetReportGenerator mBalanceSheetReportGenerator;

  @Test
  public void initialTest() {

  }
}
