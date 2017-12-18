package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.library.FineDaoDecorator;
import org.ums.generator.IdGenerator;

public class PersistentFineDao extends FineDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentFineDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }
}
