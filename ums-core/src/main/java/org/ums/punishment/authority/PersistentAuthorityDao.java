package org.ums.punishment.authority;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentAuthorityDao extends AuthorityDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAuthorityDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class AuthorityRowMapper implements RowMapper<Authority> {

    @Override
    public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentAuthority persistentAuthority = new PersistentAuthority();
      return persistentAuthority;
    }
  }
}
