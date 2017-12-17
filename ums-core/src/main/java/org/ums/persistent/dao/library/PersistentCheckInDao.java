package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.library.CheckInDaoDecorator;
import org.ums.generator.IdGenerator;

public class PersistentCheckInDao extends CheckInDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCheckInDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }
}
