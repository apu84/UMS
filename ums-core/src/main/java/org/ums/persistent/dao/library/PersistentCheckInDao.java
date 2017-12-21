package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.CheckInDaoDecorator;
import org.ums.domain.model.immutable.library.CheckIn;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentCheckIn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentCheckInDao extends CheckInDaoDecorator {

  String GET_ONE = "SELECT ID, CHECKOUT_ID, RETURN_DATE, LAST_MODIFIED ";

  String INSERT_ONE = "INSERT INTO CHECK_IN (ID, CHECKOUT_ID, RETURN_DATE, LAST_MODIFIED) VALUES (?, ?, ?, "
      + getLastModifiedSql() + " )";

  String UPDATE_ONE = "UPDATE CHECK_IN SET ID = ?, CHECKOUT_ID = ?, RETURN_DATE = ?, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";

  String DELETE_ONE = "DELETE FROM CHECK_IN ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCheckInDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class RoleRowMapper implements RowMapper<CheckIn> {

    @Override
    public CheckIn mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentCheckIn persistentCheckIn = new PersistentCheckIn();
      persistentCheckIn.setId(resultSet.getLong("ID"));
      persistentCheckIn.setCheckoutId(resultSet.getLong("CHECKOUT_ID"));
      persistentCheckIn.setReturnDate(resultSet.getDate("RETURN_DATE"));
      persistentCheckIn.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return persistentCheckIn;
    }
  }
}
