package org.ums.persistent.dao.routine;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.routine.RoutineDaoDecorator;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.routine.PersistentRoutine;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersistentRoutineDao extends RoutineDaoDecorator {
  static String SELECT_ALL =
      "SELECT ROUTINE_ID,SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_ID,LAST_MODIFIED, SLOT_GROUP FROM CLASS_ROUTINE ";
  static String UPDATE_ONE =
      "UPDATE CLASS_ROUTINE SET SEMESTER_ID=:semesterId,PROGRAM_ID=:programId,COURSE_ID=:courseId,DAY=:day,SECTION=:section,YEAR=:year,SEMESTER=:semester,START_TIME=:startTime,"
          + "END_TIME=:endTime,DURATION=:duration,ROOM_ID=:roomId,LAST_MODIFIED=:lastModified, SLOT_GROUP=:slotGroup ";
  static String DELETE_ONE = "DELETE FROM CLASS_ROUTINE ";
  static String INSERT_ONE =
      "INSERT INTO CLASS_ROUTINE(ROUTINE_ID, SEMESTER_ID,PROGRAM_ID,COURSE_ID,DAY,SECTION,YEAR,SEMESTER,START_TIME,END_TIME,DURATION,ROOM_ID,LAST_MODIFIED,SLOT_GROUP) "
          + "VALUES(:routineId, :semesterId, :programId, :courseId, :day, :section, :year, :semester, :startTime, :endTime, :duration, :roomId,:lastModified ,:slotGroup)";

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
      "select * FROM CLASS_ROUTINE WHERE SEMESTER_ID=? and PROGRAM_ID=? and YEAR=? and SEMESTER=? and SECTION LIKE '?' ";
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

  @Override
  public List<Routine> getRoutine(int pSemesterId, String pCourseId) {
    String query = SELECT_ALL + " where semester_id=? and course_id=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId}, new RoutineRowMapper());
  }

  public int update(final MutableRoutine pMutableRoutine) {
    String query = UPDATE_ONE + " WHERE ROUTINE_ID=:routineId";
    Map updateParameters = getInsertOrUpdateParameters(pMutableRoutine);
    return mNamedParameterJdbcTemplate.update(query, updateParameters);
  }

  @Override
  public Long create(final MutableRoutine pMutable) {
    Long id = mIdGenerator.getNumericId();
    mNamedParameterJdbcTemplate.update(INSERT_ONE, getInsertOrUpdateParameters(pMutable));
    return id;
  }

  @Override
  public List<Routine> getTeacherRoutine(String teacherId) {
    String query = SELECT_ALL_FOR_TEACHER;
    return mJdbcTemplate.query(query, new Object[] {teacherId}, new RoutineRowMapper());
  }

  @Override
  public List<Routine> getRoutine(int pSemesterId, int pProgramId) {
    String query =
        SELECT_ALL + " WHERE CLASS_ROUTINE.COURSE_ID=MST_COURSE.COURSE_ID  " + " and CLASS_ROUTINE.SEMESTER_ID=?  "
            + " and program_id=?  ORDER BY CLASS_ROUTINE.DAY, class_routine.START_TIME";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId}, new RoutineRowMapper());
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
  public List<Routine> getRoutine(int semesterId, int programId, int year, int semester, String section) {
    String query =
        "select * FROM CLASS_ROUTINE WHERE SEMESTER_ID=? and PROGRAM_ID=? and YEAR=? and SEMESTER=? and SECTION LIKE '"
            + section + "%'";
    return mJdbcTemplate.query(query, new Object[] {semesterId, programId, year, semester}, new RoutineRowMapper());
  }

  @Override
  public int delete(final MutableRoutine pMutable) {
    String query = DELETE_ONE + " WHERE ROUTINE_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(List<MutableRoutine> pMutableList) {
    String update = UPDATE_ONE + " where routine_id=:routineId";
    Map<String, Object>[] updateParameters = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(update, updateParameters).length;
  }

  @Override
  public int delete(List<MutableRoutine> pMutableList) {
    String query = DELETE_ONE + " where routine_id=:routineId";
    Map<String, Object>[] deleteParameters = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, deleteParameters).length;
  }

  @Override
  public List<Long> create(List<MutableRoutine> pMutableList) {
    Map<String, Object>[] createParameters = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(INSERT_ONE, createParameters);
    return pMutableList
        .stream()
        .map(p -> p.getId())
        .collect(Collectors.toList());
  }

  private Map<String, Object>[] getParameterObjects(List<MutableRoutine> pMutableRoutines) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableRoutines.size()];
    for(int i = 0; i < pMutableRoutines.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableRoutines.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableRoutine pMutableRoutine) {
    Map parameter = new HashMap();
    parameter.put("routineId", pMutableRoutine.getId());
    parameter.put("semesterId", pMutableRoutine.getSemester().getId());
    parameter.put("programId", pMutableRoutine.getProgram().getId());
    parameter.put("courseId", pMutableRoutine.getCourse().getId());
    parameter.put("day", pMutableRoutine.getDay());
    parameter.put("section", pMutableRoutine.getSection());
    parameter.put("year", pMutableRoutine.getAcademicYear());
    parameter.put("semester", pMutableRoutine.getAcademicSemester());
    parameter.put("startTime", Time.valueOf(pMutableRoutine.getStartTime()));
    parameter.put("endTime", Time.valueOf(pMutableRoutine.getEndTime()));
    parameter.put("duration", pMutableRoutine.getDuration());
    parameter.put("roomId", pMutableRoutine.getRoom().getId());
    parameter.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    parameter.put("slotGroup", pMutableRoutine.getSlotGroup());
    return parameter;
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
      persistentRoutine.setDuration(pResultSet.getInt("DURATION"));
      persistentRoutine.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      persistentRoutine.setStartTime(pResultSet.getTime("START_TIME").toLocalTime());
      persistentRoutine.setEndTime(pResultSet.getTime("END_TIME").toLocalTime());
      persistentRoutine.setRoomId(pResultSet.getLong("ROOM_ID"));
      persistentRoutine.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      persistentRoutine.setSlotGroup(pResultSet.getInt("slot_group"));
      return persistentRoutine;
    }
  }

}
