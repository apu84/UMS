package org.ums.academic.resource.fee.certificate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Monjur-E-Morshed on 07-Oct-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services-context.xml", "classpath:application-context.xml",
    "classpath:**/applicationContext.xml"})
public class CertificateFeeHelperTest {

  @Autowired
  CertificateFeeHelper certificateFeeHelper;

  @Test
  public void resolvedAllDependenciesTest() {

  }

}
