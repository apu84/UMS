package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.UGRegistrationResultDaoDecorator;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentStudentsExamAttendantInfo;
import org.ums.persistent.model.PersistentUGRegistrationResult;

public class PersistentUGRegistrationResultDao extends UGRegistrationResultDaoDecorator {
  String SELECT_ALL = "SELECT UG_REGISTRATION_RESULT.ID, UG_REGISTRATION_RESULT.STUDENT_ID, "
      + "UG_REGISTRATION_RESULT.SEMESTER_ID, " + "UG_REGISTRATION_RESULT.COURSE_ID, "
      + "UG_REGISTRATION_RESULT.GRADE_LETTER, " + "UG_REGISTRATION_RESULT.EXAM_TYPE, "
      + "UG_REGISTRATION_RESULT.REG_TYPE, " + "UG_REGISTRATION_RESULT.LAST_MODIFIED " + "FROM UG_REGISTRATION_RESULT ";

  String INSERT_ALL =
      "INSERT INTO UG_REGISTRATION_RESULT(ID, STUDENT_ID, SEMESTER_ID, COURSE_ID, GRADE_LETTER, EXAM_TYPE, TYPE, LAST_MODIFIED)"
          + " VALUES(?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String DELETE_BY_STUDENT_SEMESTER =
      "DELETE FROM UG_REGISTRATION_RESULT WHERE STUDENT_ID = ? AND SEMESTER_ID = ? AND EXAM_TYPE = ? AND STATUS = ?";

  String SELECT_ALL_CCI =
      "SELECT "
          + "               CCI.*, "
          + "               MST_COURSE.COURSE_NO, "
          + "               MST_COURSE.YEAR , "
          + "               MST_COURSE.SEMESTER, "
          + "               COURSE_TITLE,EXAM_ROUTINE.SEMESTER, "
          + "               to_char(exam_routine.exam_date, 'DD-MM-YYYY') exam_date "
          + "             FROM "
          + "               ( "
          + "                 SELECT "
          + "                   student_id, "
          + "                   course_id, "
          + "                   GRADE_LETTER, "
          + "                   exam_type, "
          + "                   'Clearance' type "
          + "                 FROM UG_REGISTRATION_RESULT "
          + "                 WHERE GRADE_LETTER = 'F' AND "
          + "                       Semester_Id = ? AND Student_id =? AND Exam_Type = 1 "
          + "                 UNION "
          + "                 SELECT "
          + "                   student_id, "
          + "                   course_id, "
          + "                   GRADE_LETTER, "
          + "                   exam_type, "
          + "                   'Improvement' type "
          + "                 FROM UG_REGISTRATION_RESULT "
          + "                 WHERE "
          + "                   Semester_Id = ? AND Student_id = ? AND GRADE_LETTER NOT IN ('F', 'A ', 'A', 'A-', 'B ') AND Exam_Type = 1 "
          + "                 UNION "
          + "                 SELECT "
          + "                   student_id, "
          + "                   course_id, "
          + "                   'F'     GRADE_LETTER, "
          + "                   1       Exam_Type, "
          + "                   'Carry' type "
          + "                 FROM "
          + "                   ( "
          + "                     select* from ( "
          + "                        SELECT "
          + "                       STUDENT_ID,course_id from UG_REGISTRATION_RESULT "
          + "                     WHERE Student_id = ? AND Semester_Id NOT IN ? AND GRADE_LETTER = 'F' "
          + "                    minus "
          + "                    SELECT "
          + "                       STUDENT_ID,course_id from UG_REGISTRATION_RESULT "
          + "                     WHERE course_id in "
          + "                     (SELECT "
          + "                       course_id from UG_REGISTRATION_RESULT "
          + "                     WHERE Student_id = ? AND Semester_Id NOT IN ? AND GRADE_LETTER = 'F') "
          + "                     and Student_id = ? AND Semester_Id NOT IN ?  and GRADE_LETTER!='F' "
          + "                     )tmp "
          + " "
          + "                   ) "
          + "               ) CCI, DB_IUMS.MST_COURSE, EXAM_ROUTINE "
          + "             WHERE MST_COURSE.COURSE_ID = CCI.COURSE_ID AND cci.course_id = exam_routine.course_id AND exam_routine.exam_type = 2 and EXAM_ROUTINE.SEMESTER=? "
          + "             ORDER BY Type, COURSE_NO, EXAM_ROUTINE.EXAM_DATE";

  String REGISTERED_THEORY_COURSES =
      "SELECT UG_REGISTRATION_RESULT.ID,UG_REGISTRATION_RESULT.STUDENT_ID,UG_REGISTRATION_RESULT.COURSE_ID,UG_REGISTRATION_RESULT.EXAM_TYPE, "
          + "UG_REGISTRATION_RESULT.REG_TYPE,UG_REGISTRATION_RESULT.SEMESTER_ID,UG_REGISTRATION_RESULT.LAST_MODIFIED "
          + "FROM UG_REGISTRATION_RESULT, MST_COURSE "
          + "WHERE STUDENT_ID = ? AND SEMESTER_ID = ? AND EXAM_TYPE = ? AND REG_TYPE=? AND UG_REGISTRATION_RESULT.COURSE_ID IN ( "
          + "  SELECT DISTINCT (COURSE_ID) "
          + "  FROM COURSE_TEACHER "
          + "  WHERE SEMESTER_ID = ?) AND MST_COURSE.COURSE_ID = UG_REGISTRATION_RESULT.COURSE_ID AND "
          + "      MST_COURSE.COURSE_TYPE = 1";
  String REG_COURSE_BY_SEM_EXAM_REG =
      "SELECT ID,STUDENT_ID,COURSE_ID,EXAM_TYPE,REG_TYPE,SEMESTER_ID,LAST_MODIFIED FROM UG_REGISTRATION_RESULT WHERE SEMESTER_ID=? AND EXAM_TYPE=? AND REG_TYPE=?";

  String EXAM_ATTENDANT_INFO =
      "SELECT a.COURSE_ID,a.PROGRAM_ID,c.DEPT_ID, b.\"YEAR\",b.SEMESTER ,( "
          + "SELECT COUNT(STUDENT_ID) FROM  UG_REGISTRATION_RESULT WHERE COURSE_ID=a.COURSE_ID AND SEMESTER_ID=? AND EXAM_TYPE=? "
          + ") as TOTAL_STUDENT, "
          + "(SELECT ABSENT_STUDENT FROM DER_EXAM_ATTENDANT_INFO WHERE PROGRAM_ID=a.PROGRAM_ID AND SEMESTER_ID=? AND EXAM_TYPE=? "
          + "and \"YEAR\"=b.\"YEAR\" AND  SEMESTER=b.SEMESTER AND EXAM_DATE = TO_DATE(?,'YYYY-MM-DD')) as ABSENT_STUDENT FROM "
          + " EXAM_ROUTINE a,MST_COURSE b,MST_PROGRAM c WHERE a.SEMESTER=? AND a.EXAM_DATE = TO_DATE(?,'YYYY-MM-DD') "
          + " AND a.COURSE_ID=b.COURSE_ID AND a.EXAM_TYPE=? AND a.PROGRAM_ID=c.PROGRAM_ID ORDER  BY c.DEPT_ID";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

  public PersistentUGRegistrationResultDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<StudentsExamAttendantInfo> getExamAttendantInfo(Integer pSemesterId, String pExamDate, Integer pExamType) {
    return mJdbcTemplate.query(EXAM_ATTENDANT_INFO, new Object[] {pSemesterId, pExamType, pSemesterId, pExamType,
        pExamDate, pSemesterId, pExamDate, pExamType}, new ExamAttendantRowMapper());
  }

  @Override
  public List<UGRegistrationResult> getRegisteredCoursesBySemesterAndExamTypeAndRegTpe(int pSemesterId, int pExamType,
      int pRegType) {
    return mJdbcTemplate.query(REG_COURSE_BY_SEM_EXAM_REG, new Object[] {pSemesterId, pExamType, pRegType},
        new UGRegistrationResultRowMapperWithoutResult());
  }

  @Override
  public List<UGRegistrationResult> getRegisteredTheoryCourseByStudent(String pStudentId, int pSemesterId,
      int pExamType, int pRegType) {
    return mJdbcTemplate.query(REGISTERED_THEORY_COURSES, new Object[] {pStudentId, pSemesterId, pExamType, pRegType,
        pSemesterId}, new UGRegistrationResultRowMapperWithoutResult());
  }

  public Integer getTotalRegisteredStudentForCourse(String pCourseId, List<String> pSection, Integer pSemesterId) {
    String query =
        "SELECT  COUNT (a.STUDENT_ID) FROM UG_REGISTRATION_RESULT_CURR a,STUDENTS b WHERE  a.COURSE_ID=:courseId AND b.THEORY_SECTION IN(:sectionList) AND a.SEMESTER_ID=:semesterId "
            + "AND  a.STUDENT_ID=b.STUDENT_ID AND b.DEPT_ID=(select DEPT_ID from MST_PROGRAM where PROGRAM_ID=( "
            + "select PROGRAM_ID from SEMESTER_SYLLABUS_MAP where SEMESTER_ID=:semesterId and (SYLLABUS_ID, Year, semester) in ( "
            + "select SYLLABUS_ID, MST_COURSE.\"YEAR\", MST_COURSE.SEMESTER from COURSE_SYLLABUS_MAP, MST_COURSE WHERE COURSE_SYLLABUS_MAP.COURSE_ID= :courseId and "
            + "MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID)))";
    Map parameterMap = new HashMap();
    parameterMap.put("sectionList", pSection);
    parameterMap.put("semesterId", pSemesterId);
    parameterMap.put("courseId", pCourseId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class);
  }

  @Override
  public List<UGRegistrationResult> getBySemesterAndExamTYpeAndGrade(int pSemesterId, int pExamType, String pGrade) {
    return super.getBySemesterAndExamTYpeAndGrade(pSemesterId, pExamType, pGrade);
  }

  @Override
  public List<UGRegistrationResult> getBySemesterAndExamTypeAndGradeAndStudent(int pSemesterId, int pExamType,
      String pGrade, String pStudentId) {
    return super.getBySemesterAndExamTypeAndGradeAndStudent(pSemesterId, pExamType, pGrade, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getImprovementCoursesBySemesterAndStudent(int pSemesterId, String pStudentId) {
    return super.getImprovementCoursesBySemesterAndStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getCarryCoursesBySemesterAndStudent(int pSemesterId, String pStudentId) {
    return super.getCarryCoursesBySemesterAndStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getCCI(int pSemesterId, String pExamDate) {
    String query =
        "SELECT "
            + "  UG_REGISTRATION_RESULT.ID, "
            + "  UG_REGISTRATION_RESULT.STUDENT_ID, "
            + "  UG_REGISTRATION_RESULT.SEMESTER_ID, "
            + "  UG_REGISTRATION_RESULT.COURSE_ID, "
            + "  UG_REGISTRATION_RESULT.GRADE_LETTER, "
            + "  UG_REGISTRATION_RESULT.EXAM_TYPE, "
            + "  UG_REGISTRATION_RESULT.REG_TYPE, "
            + "  UG_REGISTRATION_RESULT.LAST_MODIFIED "
            + "FROM UG_REGISTRATION_RESULT, EXAM_ROUTINE "
            + "WHERE UG_REGISTRATION_RESULT.SEMESTER_ID = ? AND ug_registration_result.exam_type=2 and  EXAM_ROUTINE.SEMESTER = UG_REGISTRATION_RESULT.SEMESTER_ID AND exam_routine.exam_type=ug_registration_result.exam_type and "
            + "      EXAM_ROUTINE.EXAM_DATE = TO_DATE(?, 'MM-DD-YYYY') AND UG_REGISTRATION_RESULT.COURSE_ID = EXAM_ROUTINE.COURSE_ID";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamDate},
        new UGRegistrationResultRowMapperWithoutResult());
  }

  @Override
  public List<UGRegistrationResult> getCarryClearanceImprovementCoursesByStudent(int pSemesterId, String pStudentId) {
    String query = SELECT_ALL_CCI;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pStudentId, pSemesterId, pStudentId, pStudentId,
        pSemesterId, pStudentId, pSemesterId, pStudentId, pSemesterId, pSemesterId},
        new UGRegistrationResultCCIRowMapper());
  }

  @Override
  public List<UGRegistrationResult> getByCourseSemester(int pSemesterId, String pCourseId, CourseRegType pCourseRegType) {
    String query = SELECT_ALL + " WHERE COURSE_ID = ? AND SEMESTER_ID = ? AND EXAM_TYPE = ?";
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId, pCourseRegType.getId()},
        new UGRegistrationResultRowMapperWithoutResult());
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId) {
    String query =
        SELECT_ALL + ", STUDENT_RECORD, STUDENTS WHERE STUDENT_RECORD.STUDENT_ID = UG_REGISTRATION_RESULT.STUDENT_ID "
            + "AND STUDENT_RECORD.STUDENT_ID = STUDENTS.STUDENT_ID "
            + "AND STUDENT_RECORD.REGISTRATION_TYPE != 'D' AND STUDENT_RECORD.REGISTRATION_TYPE != 'W' "
            + "AND STUDENT_RECORD.SEMESTER_ID = ? " + "AND STUDENTS.PROGRAM_ID = ? "
            + "ORDER BY STUDENT_RECORD.STUDENT_ID, UG_REGISTRATION_RESULT.SEMESTER_ID";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId},
        new UGRegistrationResultRowMapperWithResult());
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    String query =
        SELECT_ALL + ", STUDENT_RECORD, STUDENTS WHERE STUDENT_RECORD.STUDENT_ID = UG_REGISTRATION_RESULT.STUDENT_ID "
            + "AND STUDENT_RECORD.STUDENT_ID = STUDENTS.STUDENT_ID "
            + "AND STUDENT_RECORD.REGISTRATION_TYPE != 'D' AND STUDENT_RECORD.REGISTRATION_TYPE != 'W' "
            + "AND STUDENT_RECORD.SEMESTER_ID = ? " + "AND STUDENT_RECORD.YEAR = ? "
            + "AND STUDENT_RECORD.SEMESTER = ? " + "AND STUDENTS.PROGRAM_ID = ? "
            + "ORDER BY STUDENT_RECORD.STUDENT_ID, UG_REGISTRATION_RESULT.SEMESTER_ID";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pYear, pSemester, pProgramId},
        new UGRegistrationResultRowMapperWithResult());
  }

  @Override
  public List<Long> create(List<MutableUGRegistrationResult> pMutableList) {
    List<Object[]> params = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0]).collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableUGRegistrationResult> pRegistrationResults) {
    List<Object[]> params = new ArrayList<>();
    for(UGRegistrationResult registrationResult : pRegistrationResults) {
      params.add(new Object[] {mIdGenerator.getNumericId(), registrationResult.getStudent().getId(),
          registrationResult.getSemester().getId(), registrationResult.getCourse().getId(),
          registrationResult.getGradeLetter(), registrationResult.getExamType().getId(),
          registrationResult.getType().getId()});
    }

    return params;
  }

  @Override
  public int delete(List<MutableUGRegistrationResult> pMutableList) {
    return mJdbcTemplate.batchUpdate(DELETE_BY_STUDENT_SEMESTER, getDeleteParamList(pMutableList)).length;
  }

  private List<Object[]> getDeleteParamList(List<MutableUGRegistrationResult> pRegistrationResults) {
    List<Object[]> params = new ArrayList<>();
    for(UGRegistrationResult registrationResult : pRegistrationResults) {
      params.add(new Object[] {registrationResult.getStudent().getId(), registrationResult.getSemester().getId(),
          registrationResult.getExamType().getId(), registrationResult.getType().getId()});
    }

    return params;
  }

  @Override
  public List<UGRegistrationResult> getRegisteredCourseByStudent(int pSemesterId, String pStudentId,
      CourseRegType pCourseRegType) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID = ?  AND STUDENT_ID = ? AND REG_TYPE = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pStudentId, pCourseRegType.getId()},
        new UGRegistrationResultRowMapperWithoutResult());
  }

  @Override
  public List<UGRegistrationResult> getResults(String pStudentId, Integer pSemesterId) {
    String query = SELECT_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pStudentId}, new UGRegistrationResultRowMapperWithResult());
  }

  @Override
  public List<UGRegistrationResult> getSemesterResult(String pStudentId, Integer pSemesterId) {
    String query = SELECT_ALL + " WHERE STUDENT_ID = ? AND SEMESTER_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new UGRegistrationResultRowMapperWithResult());
  }

  // this will only work with Carry, Clearance and Improvement applications.
  class UGRegistrationResultCCIRowMapper implements RowMapper<UGRegistrationResult> {
    @Override
    public UGRegistrationResult mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentUGRegistrationResult result = new PersistentUGRegistrationResult();
      result.setStudentId(pResultSet.getString("STUDENT_ID"));
      result.setCourseId(pResultSet.getString("COURSE_ID"));
      result.setGradeLetter(pResultSet.getString("GRADE_LETTER"));
      result.setExamType(ExamType.get(pResultSet.getInt("EXAM_TYPE")));
      // System.out.println(pResultSet.getString("TYPE"));
      Map<String, Integer> cciNameMap = new HashMap<>();
      cciNameMap.put("Regular", 1);
      cciNameMap.put("Clearance", 2);
      cciNameMap.put("Special Carry", 4);
      cciNameMap.put("Carry", 3);
      cciNameMap.put("Improvement", 5);
      result.setType(CourseRegType.get(cciNameMap.get(pResultSet.getString("TYPE"))));
      result.setCourseNo(pResultSet.getString("COURSE_NO"));
      result.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      result.setExamDate(pResultSet.getString("EXAM_DATE"));
      return result;
    }
  }
  class ExamAttendantRowMapper implements RowMapper<StudentsExamAttendantInfo> {
    @Override
    public StudentsExamAttendantInfo mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentStudentsExamAttendantInfo application = new PersistentStudentsExamAttendantInfo();
      application.setCourseId(pResultSet.getString("COURSE_ID"));
      application.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      application.setDeptId(pResultSet.getString("DEPT_ID"));
      application.setYear(pResultSet.getInt("YEAR"));
      application.setSemester(pResultSet.getInt("SEMESTER"));
      application.setRegisteredStudents(pResultSet.getInt("TOTAL_STUDENT"));
      application.setAbsentStudents(pResultSet.getInt("ABSENT_STUDENT"));
      return application;
    }
  }

  class UGRegistrationResultRowMapperWithoutResult implements RowMapper<UGRegistrationResult> {
    protected MutableUGRegistrationResult build(ResultSet pResultSet) throws SQLException {
      MutableUGRegistrationResult result = new PersistentUGRegistrationResult();
      result.setId(pResultSet.getLong("ID"));
      result.setStudentId(pResultSet.getString("STUDENT_ID"));
      result.setCourseId(pResultSet.getString("COURSE_ID"));
      result.setExamType(ExamType.get(pResultSet.getInt("EXAM_TYPE")));
      result.setType(CourseRegType.get(pResultSet.getInt("REG_TYPE")));
      result.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      result.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return result;
    }

    @Override
    public UGRegistrationResult mapRow(ResultSet pResultSet, int pI) throws SQLException {
      AtomicReference<UGRegistrationResult> registrationResult = new AtomicReference<>(build(pResultSet));
      return registrationResult.get();
    }
  }

  class UGRegistrationResultRowMapperWithResult extends UGRegistrationResultRowMapperWithoutResult {
    @Override
    public UGRegistrationResult mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableUGRegistrationResult registrationResult = build(pResultSet);
      registrationResult.setGradeLetter(pResultSet.getString("GRADE_LETTER"));
      AtomicReference<UGRegistrationResult> result = new AtomicReference<>(registrationResult);
      return result.get();
    }
  }

  // Todo: make another row mapper which will implement all the methods.

}
