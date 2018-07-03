package org.ums.accounts.resource.definitions.account.balance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Monjur-E-Morshed on 02-Jul-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services-context.xml", "classpath:integration-context.xml",
    "classpath:application-context.xml", "classpath:application-context-webservice-core-test.xml",
    "classpath:applicationContext.xml"})
@PrepareForTest(AccountBalanceResourceHelper.class)
public class AccountBalanceResourceHelperTest {

  @Autowired
  private AccountBalanceResourceHelper mAccountBalanceResourceHelper;

  @Test
  public void init() {

  }
}
