package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.CirculationDaoDecorator;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentCirculation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistentCirculationDao extends CirculationDaoDecorator {

  String GET_ONE =
      "SELECT ID, PATRON_ID, MFN, ISSUE_DATE, DUE_DATE, RETURN_DATE, FINE_STATUS, LAST_MODIFIED FROM CIRCULATION ";

  String INSERT_ONE =
      "INSERT INTO CIRCULATION (ID, PATRON_ID, MFN, ISSUE_DATE, DUE_DATE, RETURN_DATE, FINE_STATUS, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, "
          + getLastModifiedSql() + " )";

  String UPDATE_ONE = "UPDATE CIRCULATION SET RETURN_DATE = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";

  String DELETE_ONE = "DELETE FROM CIRCULATION ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCirculationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int saveCheckout(MutableCirculation pMutable) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, mIdGenerator.getNumericId(), pMutable.getPatronId(), pMutable.getMfn(),
        new Date(), pMutable.getDueDate(), pMutable.getReturnDate(), 0);
  }

  @Override
  public List<Circulation> getCirculation(String pPatronId) {
    String query = GET_ONE + " WHERE PATRON_ID = ? AND RETURN_DATE IS NULL ";
    return mJdbcTemplate.query(query, new Object[] {pPatronId}, new PersistentCirculationDao.RoleRowMapper());
  }

  @Override
  public List<Circulation> getCirculationCheckedInItems(String pPatronId) {
    String query = GET_ONE + " WHERE PATRON_ID = ? AND RETURN_DATE IS NOT NULL ";
    return mJdbcTemplate.query(query, new Object[] {pPatronId}, new PersistentCirculationDao.RoleRowMapper());
  }

  @Override
  public List<Circulation> getAllCirculation(String pPatronId) {
    String query = GET_ONE + " WHERE PATRON_ID = ? ORDER BY RETURN_DATE DESC ";
    return mJdbcTemplate.query(query, new Object[] {pPatronId}, new PersistentCirculationDao.RoleRowMapper());
  }

  @Override
  public int updateCirculation(MutableCirculation pMutable) {
    String query = UPDATE_ONE + " WHERE MFN = ? AND RETURN_DATE IS NULL";
    return mJdbcTemplate.update(query, pMutable.getReturnDate(), pMutable.getMfn());
  }

  @Override
  public int batchUpdateCirculation(List<MutableCirculation> pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND PATRON_ID = ? AND MFN = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutable)).length;
  }

  private List<Object[]> getUpdateParams(List<MutableCirculation> pMutable) {
    List<Object[]> params = new ArrayList<>();
    for(Circulation circulation : pMutable) {
      params.add(new Object[] {circulation.getReturnDate(), circulation.getId(), circulation.getPatronId(),
          circulation.getMfn()});
    }
    return params;
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
