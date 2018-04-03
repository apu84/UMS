package org.ums.punishment.penalty;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentPenaltyDao extends PenaltyDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentPenaltyDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class PenaltyRowMapper implements RowMapper<Penalty> {

    @Override
    public Penalty mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentPenalty persistentPenalty = new PersistentPenalty();
      return persistentPenalty;
    }
  }
}
