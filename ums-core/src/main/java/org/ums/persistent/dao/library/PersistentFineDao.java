package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.FineDaoDecorator;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentFine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentFineDao extends FineDaoDecorator {

  String GET_ONE =
      "SELECT ID, PATRON_ID, CIRCULATION_ID, FINE_APPLIED_DATE, FINE_APPLIED_BY, FINE_FORGIVEN_BY, FINE_PAYMENT_DATE, AMOUNT, FINE_CATEGORY, DESCRIPTION, LAST_MODIFIED FROM FINE";

  String INSERT_ONE =
      "INSERT INTO FINE (ID, PATRON_ID,  CIRCULATION_ID, FINE_APPLIED_DATE, FINE_APPLIED_BY, FINE_FORGIVEN_BY, FINE_PAYMENT_DATE, AMOUNT, FINE_CATEGORY, DESCRIPTION, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
          + getLastModifiedSql() + " )";

  String UPDATE_ONE = "UPDATE FINE SET FINE_FORGIVEN_BY = ?, FINE_PAYMENT_DATE = ?, DESCRIPTION = ? , LAST_MODIFIED = "
      + getLastModifiedSql() + " ";

  String DELETE_ONE = "DELETE FROM FINES ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentFineDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Fine> getFines(final String pPatronId) {
    String query = GET_ONE + " WHERE PATRON_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pPatronId}, new PersistentFineDao.RoleRowMapper());
  }

  @Override
  public int saveFine(final MutableFine pMutableFine) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, mIdGenerator.getNumericId(), pMutableFine.getPatronId(),
        pMutableFine.getCirculationId(), pMutableFine.getFineAppliedDate(), pMutableFine.getFineAppliedBy(),
        pMutableFine.getFineForgivenBy(), pMutableFine.getFinePaymentDate(), pMutableFine.getAmount(),
        pMutableFine.getFineCategory(), pMutableFine.getDescription());
  }

  @Override
  public int updateFine(final MutableFine pMutableFine) {
    String query = UPDATE_ONE + " WHERE ID = ? AND PATRON_ID = ? AND CIRCULATION_ID = ?";
    return mJdbcTemplate.update(query, pMutableFine.getFineForgivenBy(), pMutableFine.getFinePaymentDate(),
        pMutableFine.getDescription(), pMutableFine.getId(), pMutableFine.getPatronId(),
        pMutableFine.getCirculationId());
  }

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
      persistentFine.setPatronId(resultSet.getString("PATRON_ID"));
      return persistentFine;
    }
  }
}
