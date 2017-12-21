package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.CirculationDaoDecorator;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentCirculation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentCirculationDao extends CirculationDaoDecorator {

  String GET_ONE =
      "SELECT ID, PATRON_ID, MFN, ISSUE_DATE, DUE_DATE, RETURN_DATE, FINE_STATUS, LAST_MODIFIED FROM CIRCULATION ";

  String INSERT_ONE =
      "INSERT INTO CIRCULATION (ID, PATRON_ID, MFN, ISSUE_DATE, DUE_DATE, RETURN_DATE, FINE_STATUS, LAST_MODIFIED VALUES (?, ?, ?, ?, ?, ?, ?, "
          + getLastModifiedSql() + ")";

  String UPDATE_ONE =
      "UPDATE CIRCULATION ID = ?, PATRON_ID = ?, MFN = ?, ISSUE_DATE = ?, DUE_DATE = ?, RETURN_DATE = ?, FINE_STATUS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  String DELETE_ONE = "DELETE FROM CIRCULATION ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCirculationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class RoleRowMapper implements RowMapper<Circulation> {

    @Override
    public Circulation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentCirculation persistentCirculation = new PersistentCirculation();
      persistentCirculation.setId(resultSet.getLong("ID"));
      persistentCirculation.setPatronId(resultSet.getString("PATRON_ID"));
      persistentCirculation.setMfn(resultSet.getLong("MFN"));
      persistentCirculation.setIssueDate(resultSet.getDate("ISSUE_DATE"));
      persistentCirculation.setDueDate(resultSet.getDate("DUE_DATE"));
      persistentCirculation.setReturnDate(resultSet.getDate("RETURN_DATE"));
      persistentCirculation.setFineStatus(resultSet.getInt("FINE_STATUS"));
      persistentCirculation.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return persistentCirculation;
    }
  }
}
