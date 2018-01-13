package org.ums.accounts.resource.definitions.period.close;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.generator.IdGenerator;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public class MutablePeriodCloseResource extends Resource {
  @Autowired
  protected PeriodCloseReosurceHelper mHelper;
  @Autowired
  protected IdGenerator mIdGenerator;

}
