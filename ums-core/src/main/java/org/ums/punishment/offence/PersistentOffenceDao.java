package org.ums.punishment.offence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentOffenceDao extends OffenceDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOffenceDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class OffenceRowMapper implements RowMapper<Offence> {

    @Override
    public Offence mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentOffence persistentOffence = new PersistentOffence();
      return persistentOffence;
    }
  }
}
