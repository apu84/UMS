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
import org.ums.decorator.UGRegistrationResultDaoDecorator;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;
import org.ums.generator.IdGenerator;
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
          + "  CCI.*, "
          + "  MST_COURSE.COURSE_NO, "
          + "  COURSE_TITLE, "
          + "  to_char(exam_routine.exam_date, 'DD-MM-YYYY') exam_date "
          + "FROM "
          + "  ( "
          + "    SELECT "
          + "      student_id, "
          + "      course_id, "
          + "      GRADE_LETTER, "
          + "      exam_type, "
          + "      'Clearance' type "
          + "    FROM UG_REGISTRATION_RESULT "
          + "    WHERE GRADE_LETTER = 'F' AND "
          + "          Semester_Id = ? AND Student_id = ? AND Exam_Type = 1 "
          + "    UNION "
          + "    SELECT "
          + "      student_id, "
          + "      course_id, "
          + "      GRADE_LETTER, "
          + "      exam_type, "
          + "      'Improvement' type "
          + "    FROM UG_REGISTRATION_RESULT "
          + "    WHERE "
          + "      Semester_Id = ? AND Student_id = ? AND GRADE_LETTER NOT IN ('F', 'A ', 'A', 'A-', 'B ') AND Exam_Type = 1 "
          + "    UNION "
          + "    SELECT "
          + "      student_id, "
          + "      course_id, "
          + "      'F'     GRADE_LETTER, "
          + "      1       Exam_Type, "
          + "      'Carry' type "
          + "    FROM "
          + "      ( "
          + "        SELECT "
          + "          student_id, "
          + "          course_id "
          + "        FROM UG_REGISTRATION_RESULT "
          + "        WHERE Student_id = ? AND Semester_Id != ? AND Exam_Type = 1 AND GRADE_LETTER = 'F' "
          + "        MINUS "
          + "        SELECT "
          + "          student_id, "
          + "          course_id "
          + "        FROM UG_REGISTRATION_RESULT "
          + "        WHERE Student_id = ? AND GRADE_LETTER != 'F' "
          + "      ) "
          + "  ) CCI, DB_IUMS.MST_COURSE, EXAM_ROUTINE "
          + "WHERE MST_COURSE.COURSE_ID = CCI.COURSE_ID AND cci.course_id = exam_routine.course_id AND exam_routine.exam_type = 2 and EXAM_ROUTINE.SEMESTER=? "
          + "ORDER BY Type, COURSE_NO, EXAM_ROUTINE.EXAM_DATE";

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

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentUGRegistrationResultDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
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
        pSemesterId, pStudentId, pSemesterId}, new UGRegistrationResultCCIRowMapper());
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
