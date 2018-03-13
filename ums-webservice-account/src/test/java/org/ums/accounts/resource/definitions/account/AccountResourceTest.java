package org.ums.accounts.resource.definitions.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Monjur-E-Morshed on 13-Mar-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services-context.xml", "classpath:application-context.xml",
    "classpath:application-context-webservice-core.xml", "classpath:applicationContext.xml"})
public class AccountResourceTest {

  @Autowired
  private AccountResource mAccountResource;

  @Test
  public void initialTest() {

  }

}
