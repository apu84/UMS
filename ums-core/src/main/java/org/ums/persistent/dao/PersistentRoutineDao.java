package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.RoutineDaoDecorator;
import org.ums.domain.model.immutable.Routine;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.persistent.model.PersistentRoutine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PersistentRoutineDao extends RoutineDaoDecorator {
  static String SELECT_ALL = "SELECT ROUTINE_ID,SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_NO,LAST_MODIFIED FROM CLASS_ROUTINE ";
  static String UPDATE_ONE = "UPDATE CLASS_ROUTINE SET SEMESTER_ID=?,PROGRAM_ID=?,COURSE_ID=?,DAY=?,SECTION=?,YEAR=?,SEMESTER=?,START_TIME=?,END_TIME=?,DURATION=?,ROOM_NO=?,LAST_MODIFIED=" + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM CLASS_ROUTINE ";
  static String INSERT_ONE = "INSERT INTO CLASS_ROUTINE(SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_NO,LAST_MODIFIED) " +
      "VALUES(?,?,?,?,?,?,?,?,?,?,?," + getLastModifiedSql() + ")";
  static String ORDER_BY = "ORDER BY SEMESTER_ID";
  static String SELECT_ALL_FOR_TEACHER = "SELECT SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_NO FROM CLASS_ROUTINE AND COURSE_TEACHER WHERE " +
      "WHERE COURSE_TEACHER.TEACHER_ID = ? AND CLASS_ROUTINE.COURSE_ID = COURSE_TEACHER.COURSE_ID";
  static String SELECT_ALL_FOR_STUDENT = " SELECT ROUTINE_ID,SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_NO,LAST_MODIFIED FROM CLASS_ROUTINE WHERE SEMESTER_ID=? AND PROGRAM_ID=? AND YEAR=? AND SEMESTER=?";

  static String SELECT_ALL_FOR_EMPLOYEE = "SELECT ROUTINE_ID,SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_NO,LAST_MODIFIED FROM CLASS_ROUTINE WHERE SEMESTER_ID=? and PROGRAM_ID=? and YEAR=? and SEMESTER=? ";
  private JdbcTemplate mJdbcTemplate;

  public PersistentRoutineDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Routine get(final String pId) throws Exception {
    String query = SELECT_ALL + " WHERE ROUTINE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new RoutineRowMapper());
  }

  public List<Routine> getAll() throws Exception {
    String query = SELECT_ALL + ORDER_BY;
    return mJdbcTemplate.query(query, new RoutineRowMapper());
  }

  public int update(final MutableRoutine pMutableRoutine) throws Exception {
    String query = UPDATE_ONE + " WHERE ROUTINE_ID=?";
    return mJdbcTemplate.update(query,
        pMutableRoutine.getSemester().getId(),
        pMutableRoutine.getProgram().getId(),
        pMutableRoutine.getCourseId(),
        pMutableRoutine.getDay(),
        pMutableRoutine.getSection(),
        pMutableRoutine.getAcademicYear(),
        pMutableRoutine.getAcademicSemester(),
        pMutableRoutine.getStartTime(),
        pMutableRoutine.getEndTime(),
        pMutableRoutine.getDuration(),
        pMutableRoutine.getRoomNo(),
        pMutableRoutine.getId()
    );
  }

  @Override
  public int create(final MutableRoutine pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ONE,
        pMutable.getSemester().getId(),
        pMutable.getProgram().getId(),
        pMutable.getCourseId(),
        pMutable.getDay(),
        pMutable.getSection(),
        pMutable.getAcademicYear(),
        pMutable.getAcademicSemester(),
        pMutable.getStartTime(),
        pMutable.getEndTime(),
        pMutable.getDuration(),
        pMutable.getRoomNo()
    );
  }

  @Override
  public List<Routine> getTeacherRoutine(String teacherId) {
    String query = SELECT_ALL_FOR_TEACHER;
    return mJdbcTemplate.query(query, new Object[]{teacherId}, new RoutineRowMapper());
  }

  @Override
  public List<Routine> getStudentRoutine(Student pStudent) {
    String query = SELECT_ALL_FOR_STUDENT;
    return mJdbcTemplate.query(query, new Object[]{pStudent.getSemesterId(), pStudent.getProgramId(), pStudent.getCurrentYear(), pStudent.getCurrentAcademicSemester()}, new RoutineRowMapper());
  }

  @Override
  public List<Routine> getEmployeeRoutine(int semesterId, int programId, int year, int semester) {
    String query = SELECT_ALL_FOR_EMPLOYEE;
    return mJdbcTemplate.query(query, new Object[]{semesterId, programId, year, semester}, new RoutineRowMapper());
  }

  @Override
  public int delete(final MutableRoutine pMutable) throws Exception {
    String query = DELETE_ONE + " WHERE ROUTINE_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  class RoutineRowMapper implements RowMapper<Routine> {
    @Override
    public Routine mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentRoutine persistentRoutine = new PersistentRoutine();
      persistentRoutine.setId(pResultSet.getString("ROUTINE_ID"));
      persistentRoutine.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      persistentRoutine.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      persistentRoutine.setCourseId(pResultSet.getString("COURSE_ID"));
      persistentRoutine.setDay(pResultSet.getInt("DAY"));
      persistentRoutine.setSection(pResultSet.getString("SECTION"));
      persistentRoutine.setAcademicYear(pResultSet.getInt("YEAR"));
      persistentRoutine.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      persistentRoutine.setStartTime(pResultSet.getString("START_TIME"));
      persistentRoutine.setEndTime(pResultSet.getString("END_TIME"));
      persistentRoutine.setRoomNo(pResultSet.getString("ROOM_NO"));
      persistentRoutine.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return persistentRoutine;
    }
  }
}
