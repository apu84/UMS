package org.ums.persistent.dao.accounts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.manager.accounts.CurrencyManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 30-Jan-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services-context.xml"})
public class PersistentCurrencyDaoTest {

  @Autowired
  private CurrencyManager mCurrencyManager;


  @Test
  public void init() {

  }

  @Test
  public void getOneTest() {
    List<Currency> currencyList = mCurrencyManager.getAll();
    Assert.notNull(mCurrencyManager.get(currencyList.get(0).getId()));
  }

  @Test
  public void getAllTest() {
    Assert.notEmpty(mCurrencyManager.getAll());
  }

}
