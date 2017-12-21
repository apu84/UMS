package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.FineDaoDecorator;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentFine;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentFineDao extends FineDaoDecorator {

  String GET_ONE =
      "SELECT ID, CIRCULATION_ID, FINE_APPLIED_DATE, FINE_APPLIED_BY, FINE_FORGIVEN_BY, FINE_PAYMENT_DATE, AMOUNT, FINE_CATEGORY, DESCRIPTION, LAST_MODIFIED FROM FINE";

  String INSERT_ONE =
      "INSERT INTO FINE (ID, CIRCULATION_ID, FINE_APPLIED_DATE, FINE_APPLIED_BY, FINE_FORGIVEN_BY, FINE_PAYMENT_DATE, AMOUNT, FINE_CATEGORY, DESCRIPTION, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
          + getLastModifiedSql() + " )";

  String UPDATE_ONE =
      "UPDATE FINE SET ID = ?, CIRCULATION_ID = ?, FINE_APPLIED_DATE = ?, FINE_APPLIED_BY = ?, FINE_FORGIVEN_BY = ?, FINE_PAYMENT_DATE = ?, AMOUNT = ?, FINE_CATEGORY = ?, DESCRIPTION = ? , LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  String DELETE_ONE = "DELETE FROM FINES ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentFineDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  // @Override
  // public Fine get()

  class RoleRowMapper implements RowMapper<Fine> {

    @Override
    public Fine mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentFine persistentFine = new PersistentFine();
      persistentFine.setId(resultSet.getLong("ID"));
      persistentFine.setCirculationId(resultSet.getLong("CIRCULATION_ID"));
      persistentFine.setFineCategory(resultSet.getInt("FINE_CATEGORY"));
      persistentFine.setFineAppliedDate(resultSet.getDate("FINE_APPLIED_DATE"));
      persistentFine.setFineAppliedBy(resultSet.getString("FINE_APPLIED_BY"));
      persistentFine.setFineForgivenBy(resultSet.getString("FINE_FORGIVEN_BY"));
      persistentFine.setFinePaymentDate(resultSet.getDate("FINE_PAYMENT_DATE"));
      persistentFine.setDescription(resultSet.getString("DESCRIPTION"));
      persistentFine.setLastModified(resultSet.getString("LAST_MODIFIED"));
      persistentFine.setAmount(resultSet.getFloat("AMOUNT"));
      return persistentFine;
    }
  }
}
