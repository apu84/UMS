package org.ums.persistent.dao.routine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.routine.RoutineDaoDecorator;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.routine.PersistentRoutine;

public class PersistentRoutineDao extends RoutineDaoDecorator {
  static String SELECT_ALL =
      "SELECT ROUTINE_ID,SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_ID,LAST_MODIFIED FROM CLASS_ROUTINE ";
  static String UPDATE_ONE =
      "UPDATE CLASS_ROUTINE SET SEMESTER_ID=?,PROGRAM_ID=?,COURSE_ID=?,DAY=?,SECTION=?,YEAR=?,SEMESTER=?,START_TIME=?,END_TIME=?,DURATION=?,ROOM_ID=?,LAST_MODIFIED="
          + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM CLASS_ROUTINE ";
  static String INSERT_ONE =
      "INSERT INTO CLASS_ROUTINE(ROUTINE_ID, SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_ID,LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + getLastModifiedSql() + ")";
  static String ORDER_BY = "ORDER BY SEMESTER_ID ";
  static String SELECT_ALL_FOR_TEACHER = "SELECT " + "  CLASS_ROUTINE.ROUTINE_ID, " + "  CLASS_ROUTINE.SEMESTER_ID, "
      + "  CLASS_ROUTINE.COURSE_ID, " + "  CLASS_ROUTINE.SECTION, " + "  CLASS_ROUTINE.YEAR, "
      + "  CLASS_ROUTINE.SEMESTER, " + "  CLASS_ROUTINE.START_TIME, " + "  CLASS_ROUTINE.END_TIME, "
      + "  CLASS_ROUTINE.DURATION, " + "  CLASS_ROUTINE.ROOM_ID, " + "  CLASS_ROUTINE.DAY, "
      + "  CLASS_ROUTINE.PROGRAM_ID, " + "  MST_COURSE.COURSE_NO " + "FROM " + "  CLASS_ROUTINE, MST_COURSE, ( "
      + "                               SELECT DISTINCT "
      + "                                 COURSE_TEACHER.COURSE_ID, "
      + "                                 MST_SEMESTER.SEMESTER_ID "
      + "                               FROM COURSE_TEACHER, MST_SEMESTER "
      + "                               WHERE MST_SEMESTER.STATUS = 1 "
      + "                                     AND COURSE_TEACHER.TEACHER_ID = ? "
      + "                             ) courseTeacher " + "WHERE "
      + "  courseTeacher.COURSE_ID = CLASS_ROUTINE.COURSE_ID AND "
      + "  courseTeacher.SEMESTER_ID = CLASS_ROUTINE.SEMESTER_ID AND "
      + "  CLASS_ROUTINE.COURSE_ID = MST_COURSE.COURSE_ID " + "ORDER BY "
      + "  CLASS_ROUTINE.DAY, CLASS_ROUTINE.START_TIME";

  static String SELECT_ALL_FOR_STUDENT =
      " SELECT ROUTINE_ID,SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_ID,LAST_MODIFIED "
          + "FROM CLASS_ROUTINE WHERE SEMESTER_ID=? AND PROGRAM_ID=? AND YEAR=? AND SEMESTER=?";

  static String SELECT_ALL_FOR_EMPLOYEE =
      "SELECT ROUTINE_ID,SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_ID,LAST_MODIFIED FROM CLASS_ROUTINE WHERE SEMESTER_ID=? and PROGRAM_ID=? and YEAR=? and SEMESTER=? ";
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentRoutineDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  public Routine get(final Long pId) {
    String query = SELECT_ALL + " WHERE ROUTINE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new RoutineRowMapper());
  }

  public List<Routine> getAll() {
    String query = SELECT_ALL + ORDER_BY;
    return mJdbcTemplate.query(query, new RoutineRowMapper());
  }

  public int update(final MutableRoutine pMutableRoutine) {
    String query = UPDATE_ONE + " WHERE ROUTINE_ID=?";
    return mJdbcTemplate.update(query, pMutableRoutine.getSemester().getId(), pMutableRoutine.getProgram().getId(),
        pMutableRoutine.getCourseId(), pMutableRoutine.getDay(), pMutableRoutine.getSection(),
        pMutableRoutine.getAcademicYear(), pMutableRoutine.getAcademicSemester(), pMutableRoutine.getStartTime(),
        pMutableRoutine.getEndTime(), pMutableRoutine.getDuration(), pMutableRoutine.getRoomId(),
        pMutableRoutine.getId());
  }

  @Override
  public Long create(final MutableRoutine pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getSemester().getId(), pMutable.getProgram().getId(),
        pMutable.getCourseId(), pMutable.getDay(), pMutable.getSection(), pMutable.getAcademicYear(),
        pMutable.getAcademicSemester(), pMutable.getStartTime(), pMutable.getEndTime(), pMutable.getDuration(),
        pMutable.getRoomId());
    return id;
  }

  @Override
  public List<Routine> getTeacherRoutine(String teacherId) {
    String query = SELECT_ALL_FOR_TEACHER;
    return mJdbcTemplate.query(query, new Object[] {teacherId}, new RoutineRowMapperWithCourseNo());
  }

  @Override
  public List<Routine> getRoutine(int pSemesterId, int pProgramId) {
    String query =
        " SELECT    " + "                CLASS_ROUTINE.ROUTINE_ID,    "
            + "                CLASS_ROUTINE.SEMESTER_ID,    " + "                CLASS_ROUTINE.COURSE_ID,    "
            + "                CLASS_ROUTINE.SECTION,    " + "                CLASS_ROUTINE.YEAR,    "
            + "                CLASS_ROUTINE.SEMESTER,    " + "                CLASS_ROUTINE.START_TIME,    "
            + "                CLASS_ROUTINE.END_TIME,    " + "                CLASS_ROUTINE.DURATION,    "
            + "                CLASS_ROUTINE.ROOM_ID,    " + "                CLASS_ROUTINE.DAY,    "
            + "                CLASS_ROUTINE.PROGRAM_ID,    " + "                MST_COURSE.COURSE_NO    "
            + "              FROM    " + "                CLASS_ROUTINE,MST_COURSE "
            + " WHERE CLASS_ROUTINE.COURSE_ID=MST_COURSE.COURSE_ID  " + " and CLASS_ROUTINE.SEMESTER_ID=?  "
            + " and program_id=?  ORDER BY CLASS_ROUTINE.DAY, class_routine.START_TIME";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId}, new RoutineRowMapperWithCourseNo());
  }

  @Override
  public List<Routine> getStudentRoutine(Student pStudent) {
    String query = SELECT_ALL_FOR_STUDENT;
    return mJdbcTemplate.query(
        query,
        new Object[] {pStudent.getSemesterId(), pStudent.getProgramId(), pStudent.getCurrentYear(),
            pStudent.getCurrentAcademicSemester()}, new RoutineRowMapper());
  }

  @Override
  public List<Routine> getEmployeeRoutine(int semesterId, int programId, int year, int semester) {
    String query = SELECT_ALL_FOR_EMPLOYEE;
    return mJdbcTemplate.query(query, new Object[] {semesterId, programId, year, semester}, new RoutineRowMapper());
  }

  @Override
  public int delete(final MutableRoutine pMutable) {
    String query = DELETE_ONE + " WHERE ROUTINE_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(List<MutableRoutine> pMutableList) {
    String update = UPDATE_ONE + " where routine_id=?";
    return mJdbcTemplate.batchUpdate(update, getUpdateParamList(pMutableList)).length;
  }

  @Override
  public int delete(List<MutableRoutine> pMutableList) {
    String query = DELETE_ONE + " where routine_id=?";
    return mJdbcTemplate.batchUpdate(query, getDeleteParamList(pMutableList)).length;
  }

  @Override
  public List<Long> create(List<MutableRoutine> pMutableList) {
    List<Object[]> params = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ONE, params);
    return params.stream().map(param -> (Long) param[0])
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableRoutine> pRoutines) {
    List<Object[]> params = new ArrayList<>();

    for(Routine routine : pRoutines) {
      params.add(new Object[] {mIdGenerator.getNumericId(), routine.getSemester().getId(),
          routine.getProgram().getId(), routine.getCourseId(), routine.getDay(), routine.getSection(),
          routine.getAcademicYear(), routine.getAcademicSemester(), routine.getStartTime(), routine.getEndTime(),
          routine.getDuration(), routine.getRoomId()});
    }

    return params;
  }

  private List<Object[]> getUpdateParamList(List<MutableRoutine> pRoutines) {
    List<Object[]> params = new ArrayList<>();

    for(Routine routine : pRoutines) {
      params.add(new Object[] {routine.getSemester().getId(), routine.getProgram().getId(), routine.getCourseId(),
          routine.getDay(), routine.getSection(), routine.getAcademicYear(), routine.getAcademicSemester(),
          routine.getStartTime(), routine.getEndTime(), routine.getDuration(), routine.getRoomId(), routine.getId()});
    }

    return params;
  }

  private List<Object[]> getDeleteParamList(List<MutableRoutine> pRoutines) {
    List<Object[]> params = new ArrayList<>();

    for(Routine routine : pRoutines) {
      params.add(new Object[] {routine.getId()});
    }

    return params;
  }

  class RoutineRowMapper implements RowMapper<Routine> {
    @Override
    public Routine mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentRoutine persistentRoutine = new PersistentRoutine();
      persistentRoutine.setId(pResultSet.getLong("ROUTINE_ID"));
      persistentRoutine.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      persistentRoutine.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      persistentRoutine.setCourseId(pResultSet.getString("COURSE_ID"));
      persistentRoutine.setDay(pResultSet.getInt("DAY"));
      persistentRoutine.setSection(pResultSet.getString("SECTION"));
      persistentRoutine.setAcademicYear(pResultSet.getInt("YEAR"));
      persistentRoutine.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      persistentRoutine.setStartTime(pResultSet.getTime("START_TIME").toLocalTime());
      persistentRoutine.setEndTime(pResultSet.getTime("END_TIME").toLocalTime());
      persistentRoutine.setRoomId(pResultSet.getLong("ROOM_ID"));
      persistentRoutine.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return persistentRoutine;
    }
  }

  /**
   * RoutineRowMapperWithCourseNo--> this row mapper has courseNo information attached with it.
   * Also, getter for duration variable is added. All others are same as the RoutineRowMapper.
   */
  class RoutineRowMapperWithCourseNo implements RowMapper<Routine> {
    @Override
    public Routine mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentRoutine persistentRoutine = new PersistentRoutine();
      persistentRoutine.setId(pResultSet.getLong("ROUTINE_ID"));
      persistentRoutine.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      persistentRoutine.setCourseId(pResultSet.getString("COURSE_ID"));
      persistentRoutine.setSection(pResultSet.getString("SECTION"));
      persistentRoutine.setAcademicYear(pResultSet.getInt("YEAR"));
      persistentRoutine.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      persistentRoutine.setStartTime(pResultSet.getTime("START_TIME").toLocalTime());
      persistentRoutine.setEndTime(pResultSet.getTime("END_TIME").toLocalTime());
      persistentRoutine.setDuration(pResultSet.getInt("duration"));
      persistentRoutine.setRoomId(pResultSet.getLong("ROOM_ID"));
      persistentRoutine.setDay(pResultSet.getInt("DAY"));
      persistentRoutine.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      return persistentRoutine;
    }
  }
}
