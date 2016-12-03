package org.ums.persistent.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SemesterWithdrawalDaoDecorator;
import org.ums.decorator.SemesterWithdrawalLogDaoDecorator;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.persistent.model.PersistentSemesterWithdrawalLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentSemesterWithdrawalLogDao extends SemesterWithdrawalLogDaoDecorator {

  static String SELECT_ALL =
      "SELECT SWL_ID,SW_ID,EMPLOYEE_ID,ACTION,EVENT_DATE_TIME,LAST_MODIFIED FROM SEMESTER_WITHDRAW_LOG ";

  static String INSERT_ONE =
      "INSERT INTO SEMESTER_WITHDRAW_LOG (SW_ID,EMPLOYEE_ID,ACTION,EVENT_DATE_TIME,LAST_MODIFIED)"
          + "VALUES (?,?,?,systimestamp," + getLastModifiedSql() + " )";
  static String UPDATE_ONE =
      "UPDATE SEMESTER_WITHDRAW_LOG SET SW_ID=?,EMPLOYEE_ID=?,ACTION=?,EVENT_DATE_TIME=systimestamp,LAST_MODIFIED= "
          + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM SEMESTER_WITHDRAW_LOG ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSemesterWithdrawalLogDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SemesterWithdrawalLog> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SemesterWithdrawalLogRowMapper());
  }

  @Override
  public SemesterWithdrawalLog get(Integer pId) {
    String query = SELECT_ALL + " WHERE SWL_ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new SemesterWithdrawalLogRowMapper());
  }

  @Override
  public int update(MutableSemesterWithdrawalLog pMutable) {
    String query = UPDATE_ONE + " WHERE SWL_ID=?";
    return mJdbcTemplate.update(query, pMutable.getSemesterWithdrawal().getId(),
        pMutable.getEmployeeId(), pMutable.getAction(), pMutable.getId());
  }

  @Override
  public int delete(MutableSemesterWithdrawalLog pMutable) {
    String query = DELETE_ONE + " WHERE SWL_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableSemesterWithdrawalLog pMutable) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutable.getSemesterWithdrawal().getId(),
        pMutable.getEmployeeId(), pMutable.getAction());
  }

  @Override
  public SemesterWithdrawalLog getBySemesterWithdrawalId(int pSemesterWithdrawalId) {
    String query = SELECT_ALL + " WHERE SWL_ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterWithdrawalId},
        new SemesterWithdrawalLogRowMapper());
  }

  class SemesterWithdrawalLogRowMapper implements RowMapper<SemesterWithdrawalLog> {
    @Override
    public SemesterWithdrawalLog mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSemesterWithdrawalLog pLog = new PersistentSemesterWithdrawalLog();
      pLog.setId(pResultSet.getInt("SWL_ID"));
      pLog.setSemesterWithdrawalId(pResultSet.getInt("SW_ID"));
      pLog.setEmployeeId(pResultSet.getString("EMPLOYEE_ID"));
      pLog.setAction(pResultSet.getInt("ACTION"));
      pLog.setEventDate(pResultSet.getString("EVENT_DATE_TIME"));
      pLog.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return null;
    }
  }

}
