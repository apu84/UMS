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

  static String SELECT_ALL = "SELECT SWL_ID,SW_ID,EMPLOYEE_ID,ACTION,COMMENTS,EVENT_DATE_TIME,LAST_MODIFIED FROM SEMESTER_WITHDRAW_LOG ";
  static String SELECT_ALL_FOR_STUDENT="SELECT L.SWL_ID,L.SW_ID,L.EMPLOYEE_ID,L.ACTION,L.COMMENTS,L.EVENT_DATE_TIME,L.LAST_MODIFIED FROM SEMESTER_WITHDRAW_LOG L , SEMESTER_WITHDRAW S " +
      "WHERE S.SW_ID=L.SW_ID AND L.ACTOR_ID=? AND S.SEMESTER_ID=?";
  static String INSERT_ONE = "INSERT INTO SEMESTER_WITHDRAW_LOG (SW_ID,EMPLOYEE_ID,ACTION,COMMENTS,EVENT_DATE_TIME,LAST_MODIFIED)" +
      "VALUES (?,?,?,?,to_date(to_char(sysdate,'DD MM YYYY HH:MM')),"+getLastModifiedSql()+" )";
  static String UPDATE_ONE = "UPDATE SEMESTER_WITHDRAW_LOG SET SW_ID=?,EMPLOYEE_ID=?,ACTION=?,COMMENTS=?,EVENT_DATE_TIME=to_date(to_char(sysdate,'DD MM YYYY HH:MM')),LAST_MODIFIED= "+getLastModifiedSql()+" ";
  static String DELETE_ONE = "DELETE FROM SEMESTER_WITHDRAW_LOG ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSemesterWithdrawalLogDao(JdbcTemplate pJdbcTemplate){
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SemesterWithdrawalLog> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query,new SemesterWithdrawalLogRowMapper());
  }

  @Override
  public SemesterWithdrawalLog get(Integer pId) throws Exception {
    String query = SELECT_ALL+" WHERE SWL_ID=?";
    return mJdbcTemplate.queryForObject(query,new Object[]{pId},new SemesterWithdrawalLogRowMapper());
  }

  @Override
  public int update(MutableSemesterWithdrawalLog pMutable) throws Exception {
    String query = UPDATE_ONE+" WHERE SWL_ID=?";
    return mJdbcTemplate.update(query,
          pMutable.getSemesterWithdrawal().getId(),
          pMutable.getEmployee().getId(),
          pMutable.getAction(),
          pMutable.getComments(),
          pMutable.getId()
        );
  }

  @Override
  public int delete(MutableSemesterWithdrawalLog pMutable) throws Exception {
    String query = DELETE_ONE+" WHERE SWL_ID=?";
    return mJdbcTemplate.update(query,pMutable.getId());
  }

  @Override
  public int create(MutableSemesterWithdrawalLog pMutable) throws Exception {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query,
          pMutable.getSemesterWithdrawal().getId(),
          pMutable.getEmployee().getId(),
          pMutable.getAction(),
          pMutable.getComments()
        );
  }

  @Override
  public SemesterWithdrawalLog getBySemesterWithdrawalId(int pSemesterWithdrawalId) {
    String query = SELECT_ALL+" WHERE SWL_ID=?";
    return mJdbcTemplate.queryForObject(query,new Object[]{pSemesterWithdrawalId},new SemesterWithdrawalLogRowMapper());
  }

  class SemesterWithdrawalLogRowMapper implements RowMapper<SemesterWithdrawalLog>{
    @Override
    public SemesterWithdrawalLog mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSemesterWithdrawalLog pLog = new PersistentSemesterWithdrawalLog();
      pLog.setId(pResultSet.getInt("SWL_ID"));
      pLog.setSemesterWithdrawalId(pResultSet.getInt("SW_ID"));
      pLog.setEmployeeId(pResultSet.getString("EMPLOYEE_ID"));
      pLog.setAction(pResultSet.getInt("ACTION"));
      pLog.setComments(pResultSet.getString("COMMENTS"));
      pLog.setEventDate(pResultSet.getString("EVENT_DATE_TIME"));
      pLog.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return null;
    }
  }

}
