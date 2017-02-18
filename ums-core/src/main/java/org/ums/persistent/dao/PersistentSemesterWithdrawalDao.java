package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SemesterWithdrawalDaoDecorator;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentSemesterWithdrawal;

public class PersistentSemesterWithdrawalDao extends SemesterWithdrawalDaoDecorator {

  String SELECT_ALL =
      "SELECT SW_ID,SEMESTER_ID,PROGRAM_ID,STUDENT_ID,YEAR,SEMESTER,CAUSE,STATUS,APP_DATE,COMMENTS,LAST_MODIFIED FROM SEMESTER_WITHDRAW";
  String SELECT_ALL_For_Employee =
      "SELECT SW.SW_ID,SW.SEMESTER_ID,SW.PROGRAM_ID,SW.STUDENT_ID,SW.YEAR,SW.SEMESTER,SW.CAUSE,SW.STATUS,SW.APP_DATE,SW.COMMENTS,SW.LAST_MODIFIED FROM SEMESTER_WITHDRAW";

  String INSERT_ONE =
      "INSERT INTO SEMESTER_WITHDRAW(SW_ID, SEMESTER_ID,PROGRAM_ID,STUDENT_ID,YEAR,SEMESTER,CAUSE,STATUS,COMMENTS,APP_DATE,LAST_MODIFIED) VALUES(?,?,?,?,?,?,?,?,?,systimestamp,"
          + getLastModifiedSql() + ") ";
  String UPDATE_ONE =
      "UPDATE SEMESTER_WITHDRAW SET SEMESTER_ID=?,PROGRAM_ID=?,STUDENT_ID=?,YEAR=?,SEMESTER=?,CAUSE=?,STATUS=?,COMMENTS=?,APP_DATE=systimestamp,LAST_MODIFIED="
          + getLastModifiedSql() + " ";
  String UPDATE_ONE_ALTERNATE =
      "UPDATE SEMESTER_WITHDRAW SET SEMESTER_ID=?,PROGRAM_ID=?,STUDENT_ID=?,YEAR=?,SEMESTER=?,CAUSE=?,STATUS=?,APP_DATE=TO_TIMESTAMP(?,'RRRR-MM-DD HH24:MI:SS.FF'),COMMENTS=?,LAST_MODIFIED="
          + getLastModifiedSql() + " ";

  String DELETE_ONE = "DELETE FROM SEMESTER_WITHDRAW ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentSemesterWithdrawalDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public SemesterWithdrawal getStudentsRecord(String studentId, int semesterId, int year,
      int semester) {
    String query = SELECT_ALL + " WHERE STUDENT_ID=? AND SEMESTER_ID=? AND YEAR=? AND SEMESTER=?";
    return mJdbcTemplate.queryForObject(query,
        new Object[] {studentId, semesterId, year, semester}, new SemesterWithdrawalRowMapper());
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForHead(String teacherId) {
    return super.getSemesterWithdrawalForHead(teacherId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForAAO(String employeeId) {
    return super.getSemesterWithdrawalForAAO(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForVC(String employeeId) {
    return super.getSemesterWithdrawalForVC(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForRegistrar(String employeeId) {
    return super.getSemesterWithdrawalForRegistrar(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForDeputyRegistrar(String employeeId) {
    return super.getSemesterWithdrawalForDeputyRegistrar(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getByDeptForEmployee(String deptId) {

    String query =
        SELECT_ALL_For_Employee
            + "  SW,STUDENTS S,STUDENT_RECORD SR WHERE SW.STUDENT_ID = S.STUDENT_ID "
            + " AND SW.STUDENT_ID=SR.STUDENT_ID AND S.DEPT_ID=? AND SW.YEAR =SR.YEAR "
            + " AND SW.SEMESTER=SR.SEMESTER  ";
    return mJdbcTemplate.query(query, new Object[] {deptId}, new SemesterWithdrawalRowMapper());
  }

  @Override
  public int update(MutableSemesterWithdrawal pMutable) {
    if(pMutable.getStatus() < 2) {
      String query = UPDATE_ONE + " WHERE SW_ID=?";
      return mJdbcTemplate.update(query, pMutable.getSemester().getId(), pMutable.getProgram()
          .getId(), pMutable.getStudent().getId(), pMutable.getStudent().getCurrentYear(), pMutable
          .getStudent().getCurrentAcademicSemester(), pMutable.getCause(), pMutable.getStatus(),
          pMutable.getComment(), pMutable.getId());
    }
    else {

      String query = UPDATE_ONE_ALTERNATE + " WHERE SW_ID=?";
      return mJdbcTemplate.update(query, pMutable.getSemester().getId(), pMutable.getProgram()
          .getId(), pMutable.getStudent().getId(), pMutable.getStudent().getCurrentYear(), pMutable
          .getStudent().getCurrentAcademicSemester(), pMutable.getCause(), pMutable.getStatus(),
          pMutable.getAppDate(), pMutable.getComment(), pMutable.getId());
    }

  }

  @Override
  public int delete(MutableSemesterWithdrawal pMutable) {
    String query = DELETE_ONE + " WHERE SW_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public SemesterWithdrawal get(Long pId) {
    String query = SELECT_ALL + " WHERE SW_ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new SemesterWithdrawalRowMapper());
  }

  @Override
  public List<SemesterWithdrawal> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SemesterWithdrawalRowMapper());
  }

  @Override
  public int create(MutableSemesterWithdrawal pMutable) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, mIdGenerator.getNumericId(), pMutable.getSemester().getId(),
        pMutable.getProgram().getId(), pMutable.getStudent().getId(), pMutable.getStudent()
            .getCurrentYear(), pMutable.getStudent().getCurrentAcademicSemester(), pMutable
            .getCause(), pMutable.getStatus(), pMutable.getComment());
  }

  class SemesterWithdrawalRowMapper implements RowMapper<SemesterWithdrawal> {
    @Override
    public SemesterWithdrawal mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSemesterWithdrawal persistenceSW = new PersistentSemesterWithdrawal();
      persistenceSW.setId(pResultSet.getLong("SW_ID"));
      persistenceSW.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      persistenceSW.setStudentId(pResultSet.getString("STUDENT_ID"));
      persistenceSW.setAcademicYear(pResultSet.getInt("YEAR"));
      persistenceSW.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      persistenceSW.setCause(pResultSet.getString("CAUSE"));
      persistenceSW.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      persistenceSW.setStatus(pResultSet.getInt("STATUS"));
      persistenceSW.setAppDate(pResultSet.getString("APP_DATE"));
      persistenceSW.setComments(pResultSet.getString("COMMENTS"));
      persistenceSW.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return persistenceSW;
    }
  }

  /*
   * class SemesterWithdrawalLogRowMapper implements RowMapper<SemesterWithdrawalLog>{
   * 
   * @Override public SemesterWithdrawalLog mapRow(ResultSet pResultSet, int pI) throws SQLException
   * { PersistentSemesterWithdrawalLog persistentSemesterWithdrawalLog = new
   * PersistentSemesterWithdrawalLog();
   * persistentSemesterWithdrawalLog.setId(pResultSet.getInt("SWL_ID"));
   * persistentSemesterWithdrawalLog.setSemesterWithdrawalId(pResultSet.getInt("SW_ID"));
   * persistentSemesterWithdrawalLog.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
   * persistentSemesterWithdrawalLog.setActor(pResultSet.getString("ACTOR"));
   * persistentSemesterWithdrawalLog.setAction(pResultSet.getInt("ACTION"));
   * persistentSemesterWithdrawalLog.setComments(pResultSet.getString("COMMENTS")); return
   * persistentSemesterWithdrawalLog; } }
   */
}
