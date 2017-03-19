package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SemesterWithdrawalLogDaoDecorator;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentSemesterWithdrawalLog;

public class PersistentSemesterWithdrawalLogDao extends SemesterWithdrawalLogDaoDecorator {

  static String SELECT_ALL =
      "SELECT SWL_ID,SW_ID,EMPLOYEE_ID,ACTION,EVENT_DATE_TIME,LAST_MODIFIED FROM SEMESTER_WITHDRAW_LOG ";

  static String INSERT_ONE =
      "INSERT INTO SEMESTER_WITHDRAW_LOG (SWL_ID, SW_ID,EMPLOYEE_ID,ACTION,EVENT_DATE_TIME,LAST_MODIFIED)"
          + "VALUES (?,?,?,?,systimestamp," + getLastModifiedSql() + " )";
  static String UPDATE_ONE =
      "UPDATE SEMESTER_WITHDRAW_LOG SET SW_ID=?,EMPLOYEE_ID=?,ACTION=?,EVENT_DATE_TIME=systimestamp,LAST_MODIFIED= "
          + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM SEMESTER_WITHDRAW_LOG ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentSemesterWithdrawalLogDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<SemesterWithdrawalLog> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SemesterWithdrawalLogRowMapper());
  }

  @Override
  public SemesterWithdrawalLog get(Long pId) {
    String query = SELECT_ALL + " WHERE SWL_ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new SemesterWithdrawalLogRowMapper());
  }

  @Override
  public int update(MutableSemesterWithdrawalLog pMutable) {
    String query = UPDATE_ONE + " WHERE SWL_ID=?";
    return mJdbcTemplate.update(query, pMutable.getSemesterWithdrawal().getId(), pMutable.getEmployeeId(),
        pMutable.getAction(), pMutable.getId());
  }

  @Override
  public int delete(MutableSemesterWithdrawalLog pMutable) {
    String query = DELETE_ONE + " WHERE SWL_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableSemesterWithdrawalLog pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getSemesterWithdrawal().getId(), pMutable.getEmployeeId(),
        pMutable.getAction());
    return id;
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
      pLog.setId(pResultSet.getLong("SWL_ID"));
      pLog.setSemesterWithdrawalId(pResultSet.getLong("SW_ID"));
      pLog.setEmployeeId(pResultSet.getString("EMPLOYEE_ID"));
      pLog.setAction(pResultSet.getInt("ACTION"));
      pLog.setEventDate(pResultSet.getString("EVENT_DATE_TIME"));
      pLog.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return null;
    }
  }

}
