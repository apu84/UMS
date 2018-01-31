package org.ums.accounts.resource.definitions.voucher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services-context.xml",
    "classpath:application-context-webservice-core.xml", "classpath:applicationContext.xml"})
@WebAppConfiguration
public class VoucherResourceTest {

  @Autowired
  VoucherResource mVoucherResource;

  private MockMvc mMockMvc;

  @Before
  public void init() {

  }

  @Test
  public void getVoucherTest() throws Exception {
    /*
     * Voucher voucher = mVoucherResource.mVoucherManager.get(1L); ObjectMapper mapper = new
     * ObjectMapper(); System.out.println(get("/account/definition/voucher/id/1"));
     * mMockMvc.perform(
     * get("/account/definition/voucher/id/1").accept(MediaType.APPLICATION_JSON)).andDo(print());
     */

  }
}
