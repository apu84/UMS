package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.accounts.GroupDaoDecorator;
import org.ums.generator.IdGenerator;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public class PersistentGroupDao extends GroupDaoDecorator {


  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentGroupDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }
}
