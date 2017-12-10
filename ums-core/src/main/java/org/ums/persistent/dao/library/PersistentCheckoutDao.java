package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.library.CheckoutDaoDecorator;
import org.ums.generator.IdGenerator;

public class PersistentCheckoutDao extends CheckoutDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCheckoutDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }
}
