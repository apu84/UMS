package org.ums.punishment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentPunishmentDao extends PunishmentDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentPunishmentDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class PunishmentRowMapper implements RowMapper<Punishment> {

    @Override
    public Punishment mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentPunishment persistentPunishment = new PersistentPunishment();
      return persistentPunishment;
    }
  }
}
