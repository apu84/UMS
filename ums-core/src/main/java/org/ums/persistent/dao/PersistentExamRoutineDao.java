package org.ums.persistent.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.ExamRoutineDaoDecorator;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 26-Feb-16.
 */
public class PersistentExamRoutineDao extends ExamRoutineDaoDecorator {

  static String DELETE = "DELETE EXAM_ROUTINE Where SEMESTER=? And Exam_Type=? ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentExamRoutineDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  String EXAM_ROUTINE_DATE_BY_COURSE_ID =
      "SELECT  to_char(EXAM_DATE,'DD-MM-YYYY') EXAM_DATE,EXAM_TIME,PROGRAM_ID,APPLICATION_DEADLINE from EXAM_ROUTINE WHERE COURSE_ID=? AND SEMESTER=? AND EXAM_TYPE=?";

  @Override
  public List<ExamRoutineDto> getExamRoutine(int semesterId, int examTypeId) {
    String SELECT_ALL =
        "Select TO_CHAR(EXAM_DATE,'DD/MM/YYYY') EXAM_DATE,EXAM_TIME,MST_PROGRAM.PROGRAM_ID,PROGRAM_SHORT_NAME,  "
            + "MST_COURSE.YEAR,MST_COURSE.SEMESTER,COURSE_NO,COURSE_TITLE,MST_COURSE.COURSE_ID,EXAM_GROUP,APPLICATION_DEADLINE,  "
            + "TO_CHAR(APPLICATION_DEADLINE, 'DD/MM/YYYY HH:MI AM') APPLICATION_DEADLINE_STR,COURSE_SYLLABUS_MAP.SYLLABUS_ID "
            + "From EXAM_ROUTINE,MST_COURSE,MST_SYLLABUS,MST_PROGRAM,COURSE_SYLLABUS_MAP ,SEMESTER_SYLLABUS_MAP "
            + "Where EXAM_ROUTINE.SEMESTER= ? And Exam_Type=? "
            + "And COURSE_SYLLABUS_MAP.COURSE_ID=MST_COURSE.COURSE_ID  "
            + "And EXAM_ROUTINE.COURSE_ID=MST_COURSE.COURSE_ID  "
            + "And COURSE_SYLLABUS_MAP.SYLLABUS_ID=MST_SYLLABUS.SYLLABUS_ID  "
            + "And MST_SYLLABUS.PROGRAM_ID=MST_PROGRAM.PROGRAM_ID  "
            + "And COURSE_SYLLABUS_MAP.SYLLABUS_ID = SEMESTER_SYLLABUS_MAP.SYLLABUS_ID "
            + "And COURSE_SYLLABUS_MAP.COURSE_ID = MST_COURSE.COURSE_ID "
            + "And SEMESTER_SYLLABUS_MAP.YEAR=MST_COURSE.YEAR AND SEMESTER_SYLLABUS_MAP.SEMESTER=MST_COURSE.SEMESTER "
            + "AND SEMESTER_SYLLABUS_MAP.SEMESTER_ID=EXAM_ROUTINE.SEMESTER "
            + "Order By to_date(EXAM_DATE,'DD/MM/YYYY') ,Exam_Time,Program_Id,Year,Semester,Course_No  ";
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new Object[] {semesterId, examTypeId}, new ExamRoutineRowMapper());
  }

  @Override
  public List<ExamRoutineDto> getExamDatesBySemesterAndTypeAndCourseId(String pCourseId, Integer pSemesterId,
      Integer pExamType) {
    return mJdbcTemplate.query(EXAM_ROUTINE_DATE_BY_COURSE_ID, new Object[] {pCourseId, pSemesterId, pExamType},
        new ExamRoutineRowMapperOfDbTableModified());
  }

  @Override
  public List<ExamRoutineDto> getExamRoutine(int pSemesterId, int pExamType, String pOfferedBy) {
    String query =
        "SELECT TO_CHAR(EXAM_DATE, 'DD/MM/YYYY') EXAM_DATE,  EXAM_TIME, MST_PROGRAM.PROGRAM_ID, "
            + "PROGRAM_SHORT_NAME,MST_COURSE.YEAR,  MST_COURSE.SEMESTER,  COURSE_NO,  COURSE_TITLE, "
            + "MST_COURSE.COURSE_ID, "
            + "EXAM_GROUP, TO_CHAR(APPLICATION_DEADLINE, 'DD/MM/YYYY HH:MI') APPLICATION_DEADLINE_STR ,COURSE_SYLLABUS_MAP.SYLLABUS_ID "
            + "FROM EXAM_ROUTINE, MST_COURSE, MST_SYLLABUS, MST_PROGRAM, COURSE_SYLLABUS_MAP ,SEMESTER_SYLLABUS_MAP "
            + "WHERE EXAM_ROUTINE.SEMESTER = ?    AND Exam_Type = ?"
            + "AND COURSE_SYLLABUS_MAP.COURSE_ID = MST_COURSE.COURSE_ID "
            + "AND EXAM_ROUTINE.COURSE_ID = MST_COURSE.COURSE_ID AND MST_COURSE.OFFER_BY=? "
            + "AND COURSE_SYLLABUS_MAP.SYLLABUS_ID = MST_SYLLABUS.SYLLABUS_ID "
            + "AND MST_SYLLABUS.PROGRAM_ID = MST_PROGRAM.PROGRAM_ID "
            + "And COURSE_SYLLABUS_MAP.SYLLABUS_ID = SEMESTER_SYLLABUS_MAP.SYLLABUS_ID "
            + "And COURSE_SYLLABUS_MAP.COURSE_ID = MST_COURSE.COURSE_ID "
            + "And SEMESTER_SYLLABUS_MAP.YEAR=MST_COURSE.YEAR AND SEMESTER_SYLLABUS_MAP.SEMESTER=MST_COURSE.SEMESTER "
            + "AND SEMESTER_SYLLABUS_MAP.SEMESTER_ID=EXAM_ROUTINE.SEMESTER       "
            + "ORDER BY to_date(EXAM_DATE, 'DD/MM/YYYY'), Exam_Time, Program_Id, Year, Semester, Course_No ";

    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType, pOfferedBy}, new ExamRoutineRowMapper());
  }

  @Override
  public List<ExamRoutineDto> getExamRoutineForApplicationCCI(int semesterId, int examType) {
    String query =
        "Select exam_routine.exam_date original_date,to_char(exam_routine.exam_date,'MM-DD-YYYY') exam_date,nvl(total_student,0) total_student From ( "
            + "select distinct r.exam_date,semester,exam_type from exam_routine r Where r.exam_type=? and semester=?)exam_routine   "
            + "left join  "
            + "( "
            + "select exam_date,semester_id,count(total_student) total_student  from sp_sub_group_cci where semester_id=? group by exam_date,semester_id,exam_date order by exam_date "
            + ")subGroup on exam_routine.exam_date = subGroup.exam_date and exam_routine.semester=subGroup.semester_id  order by exam_routine.exam_date";

    return mJdbcTemplate
        .query(query, new Object[] {examType, semesterId, semesterId}, new ExamRoutineForCCIRowMapper());
  }

  @Override
  public ExamRoutineDto getExamRoutineForCivilExamBySemester(Integer pSemesterId) {
    String query =
        "select  TO_CHAR(rr.EXAM_DATE,'DD/MM/YYYY') exam_date from exam_routine,(  "
            + "select exam_date from exam_routine group by exam_date having count(exam_date)=2)  "
            + "rr where exam_routine.exam_date = rr.exam_date and ROWNUM=1 and semester=?";
    return mJdbcTemplate
        .queryForObject(query, new Object[] {pSemesterId}, new ExamRoutineForSeatPlanPublishRowMapper());
  }

  @Override
  public List<ExamRoutineDto> getExamDatesBySemesterAndType(Integer pSemesterId, Integer pExamType) {
    String query =
        "select to_char(EXAM_DATE,'DD-MM-YYYY') Exam_Date from " + "(select distinct(exam_date) from EXAM_ROUTINE "
            + "where exam_type=? and semester=? ORDER BY EXAM_DATE) ";
    return mJdbcTemplate.query(query, new Object[] {pExamType, pSemesterId},
        new ExamRoutineForSeatPlanPublishRowMapper());
  }

  @Override
  public List<ExamRoutineDto> getExamRoutineBySemesterAndExamTypeOrderByExamDateAndProgramIdAndCourseId(
      Integer pSemesterId, Integer pExamType) {
    String query =
        "" + "SELECT " + "  SEMESTER, " + "  to_char(EXAM_DATE,'DD/MM/YYYY') exam_date, " + "  EXAM_TIME, "
            + "  PROGRAM_ID, " + "  COURSE_ID " + "FROM EXAM_ROUTINE " + "WHERE " + "  SEMESTER=? "
            + "  AND EXAM_TYPE=? " + "ORDER BY EXAM_DATE, " + "  PROGRAM_ID, " + "  COURSE_ID";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType}, new ExamRoutineRowMapperOfDbTable());
  }

  @Override
  public List<ExamRoutineDto> getCCIExamRoutinesBySemeste(Integer pSemesterId) {
    String query =
        "select to_char(exam_date,'DD/MM/YYYY') exam_date  FROM   "
            + "(select distinct(exam_date) from exam_routine   "
            + "where exam_type=2 and semester=? order by exam_date) er";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ExamRoutineForSeatPlanPublishRowMapper());
  }

  public int delete(final MutableExamRoutine pExamRoutine) {
    if(pExamRoutine.getInsertType().equalsIgnoreCase("byProgram")) {
      String query = DELETE + " And  Exam_Date= to_date(?,'dd/MM/YYYY') And Exam_Time =?  and  Program_Id =?";
      String examDate = pExamRoutine.getRoutine().get(0).getExamDate();
      String examTime = pExamRoutine.getRoutine().get(0).getExamTime();
      int programId = pExamRoutine.getRoutine().get(0).getProgramId();

      return mJdbcTemplate.update(query, pExamRoutine.getSemesterId(), pExamRoutine.getExamTypeId(), examDate,
          examTime, programId);
    }
    else if(pExamRoutine.getInsertType().equalsIgnoreCase("byDateTime")) {
      String query = DELETE + " And  Exam_Date= to_date(?,'dd/MM/YYYY') And Exam_Time = ?  ";
      String examDate = pExamRoutine.getRoutine().get(0).getExamDate();
      String examTime = pExamRoutine.getRoutine().get(0).getExamTime();
      return mJdbcTemplate
          .update(query, pExamRoutine.getSemesterId(), pExamRoutine.getExamTypeId(), examDate, examTime);
    }
    else if(pExamRoutine.getInsertType().equalsIgnoreCase("all")) {
      String query = DELETE;
      return mJdbcTemplate.update(query, pExamRoutine.getSemesterId(), pExamRoutine.getExamTypeId());
    }
    return 0;
  }

  public Long create(final MutableExamRoutine pExamRoutine) {
    insertBatch(pExamRoutine);
    return 1L;
  }

  public void insertBatch(final MutableExamRoutine pExamRoutine) {
    String INSERT_ONE =
        "INSERT INTO EXAM_ROUTINE(SEMESTER,EXAM_TYPE,EXAM_DATE,EXAM_TIME,PROGRAM_ID,COURSE_ID,EXAM_GROUP,APPLICATION_DEADLINE) "
            + "VALUES(?,?,to_date(?,'dd/MM/YYYY'),?,?,?,?,to_date(?,'dd/MM/YYYY HH:MI AM'))";

    List<ExamRoutineDto> routineList = pExamRoutine.getRoutine();
    String sql = INSERT_ONE;

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ExamRoutineDto routine = routineList.get(i);
        ps.setInt(1, pExamRoutine.getSemesterId());
        ps.setInt(2, pExamRoutine.getExamTypeId());
        ps.setString(3, routine.getExamDate());
        ps.setString(4, routine.getExamTime());
        ps.setInt(5, routine.getProgramId());
        ps.setString(6, routine.getCourseId());
        ps.setInt(7, routine.getExamGroup());

        ps.setString(8, routine.getAppDeadLineStr());
      }

      @Override
      public int getBatchSize() {
        return routineList.size();
      }

    });
  }

  class ExamRoutineRowMapper implements RowMapper<ExamRoutineDto> {
    @Override
    public ExamRoutineDto mapRow(ResultSet resultSet, int i) throws SQLException {
      ExamRoutineDto routine = new ExamRoutineDto();
      routine.setExamDate(resultSet.getString("EXAM_DATE"));
      routine.setExamTime(resultSet.getString("EXAM_TIME"));
      routine.setProgramId(resultSet.getInt("PROGRAM_ID"));
      routine.setProgramName(resultSet.getString("PROGRAM_SHORT_NAME"));
      routine.setCourseYear(resultSet.getInt("YEAR"));
      routine.setCourseSemester(resultSet.getInt("SEMESTER"));
      routine.setCourseNumber(resultSet.getString("COURSE_NO"));
      routine.setCourseId(resultSet.getString("COURSE_ID"));
      routine.setCourseTitle(resultSet.getString("COURSE_TITLE"));
      routine.setExamGroup(resultSet.getInt("EXAM_GROUP"));
      routine.setAppDeadLine(resultSet.getDate("APPLICATION_DEADLINE"));
      routine.setAppDeadLineStr(resultSet.getString("APPLICATION_DEADLINE_STR"));
      AtomicReference<ExamRoutineDto> atomicReference = new AtomicReference<>(routine);
      return atomicReference.get();
    }
  }

  class ExamRoutineForCCIRowMapper implements RowMapper<ExamRoutineDto> {
    @Override
    public ExamRoutineDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      ExamRoutineDto routine = new ExamRoutineDto();
      routine.setExamDateOriginal(pResultSet.getString("ORIGINAL_DATE"));
      routine.setExamDate(pResultSet.getString("EXAM_DATE"));
      routine.setTotalStudent(pResultSet.getInt("TOTAL_STUDENT"));
      return routine;
    }
  }

  class ExamRoutineForSeatPlanPublishRowMapper implements RowMapper<ExamRoutineDto> {
    @Override
    public ExamRoutineDto mapRow(ResultSet resultSet, int pI) throws SQLException {
      ExamRoutineDto routine = new ExamRoutineDto();
      routine.setExamDate(resultSet.getString("exam_date"));
      return routine;
    }
  }

  class ExamRoutineRowMapperOfDbTableModified implements RowMapper<ExamRoutineDto> {
    @Override
    public ExamRoutineDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      ExamRoutineDto routine = new ExamRoutineDto();
      routine.setExamDate(pResultSet.getString("exam_date"));
      routine.setExamTime(pResultSet.getString("exam_time"));
      routine.setProgramId(pResultSet.getInt("program_id"));
      routine.setAppDeadLine(pResultSet.getDate("APPLICATION_DEADLINE"));
      return routine;
    }
  }

  class ExamRoutineRowMapperOfDbTable implements RowMapper<ExamRoutineDto> {
    @Override
    public ExamRoutineDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      ExamRoutineDto routine = new ExamRoutineDto();
      routine.setCourseSemester(pResultSet.getInt("semester"));
      routine.setExamDate(pResultSet.getString("exam_date"));
      routine.setExamTime(pResultSet.getString("exam_time"));
      routine.setProgramId(pResultSet.getInt("program_id"));
      routine.setCourseId(pResultSet.getString("course_id"));
      routine.setAppDeadLine(pResultSet.getDate("APPLICATION_DEADLINE"));
      routine.setAppDeadLineStr(pResultSet.getString("APPLICATION_DEADLINE_STR"));
      return routine;
    }
  }
}
