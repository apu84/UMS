package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationTESDaoDecorator;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentApplicationTES;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
public class PersistentApplicationTESDao extends ApplicationTESDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentApplicationTESDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String getAllQuestions = "SELECT QUESTION_ID,QUESTION_DETAILS,OBSERVATION_TYPE from APPLICATION_TES_QUESTIONS";

  String INSERT_ONE =
      "Insert  into  APPLICATION_TES_QINFO  (ID,QUESTION_ID,POINT,STUDENT_COMMENT,FACULTY_ID,OBSERVATION_TYPE)  values  (?,?,?,?,?,?)";
  String INSERT_TWO =
      "Insert  into  APPLICATION_TES_S_INFO  (COURSE_ID,STUDENT_ID,SEMESTER_ID,FACULTY_ID,APPLIED_ON)  values  (?,?,?,?,systimestamp)";
  String ALREADY_REVIEWED =
      "SELECT  DISTINCT a.FACULTY_ID,a.COURSE_ID,MST_COURSE.COURSE_TITLE,a.STUDENT_ID,a.SEMESTER_ID,b.FIRST_NAME,b.LAST_NAME "
          + "FROM APPLICATION_TES_S_INFO a,EMP_PERSONAL_INFO b,MST_COURSE WHERE STUDENT_ID=? and SEMESTER_ID=? "
          + "AND a.FACULTY_ID=b.EMPLOYEE_ID AND  a.COURSE_ID=MST_COURSE.COURSE_ID";

  String getAllReviewEligibleCoures =
      "select* from (  "
          + "SELECT DISTINCT  UG_REGISTRATION_RESULT.COURSE_ID,MST_COURSE.COURSE_TITLE,MST_COURSE.COURSE_NO  "
          + "FROM UG_REGISTRATION_RESULT, COURSE_TEACHER,MST_COURSE  "
          + "WHERE  "
          + "  UG_REGISTRATION_RESULT.SEMESTER_ID = ? AND UG_REGISTRATION_RESULT.SEMESTER_ID = COURSE_TEACHER.SEMESTER_ID AND  "
          + "  UG_REGISTRATION_RESULT.COURSE_ID = COURSE_TEACHER.COURSE_ID AND UG_REGISTRATION_RESULT.STUDENT_ID = ? AND  "
          + "  UG_REGISTRATION_RESULT.COURSE_ID=MST_COURSE.COURSE_ID AND MST_COURSE.COURSE_TYPE=? AND COURSE_TEACHER.COURSE_ID=MST_COURSE.COURSE_ID  "
          + "minus  "
          + "SELECT DISTINCT a.COURSE_ID,MST_COURSE.COURSE_TITLE,MST_COURSE.COURSE_NO FROM APPLICATION_TES_S_INFO a,MST_COURSE  "
          + "WHERE STUDENT_ID=150204035 AND SEMESTER_ID=11012017 AND a.COURSE_ID =MST_COURSE.COURSE_ID) tmp";

  String getSemesterName = "SELECT SEMESTER_NAME from MST_SEMESTER WHERE SEMESTER_ID=?";

  String getTeachersInfo =
      "SELECT "
          + "COURSE_TEACHER.TEACHER_ID, "
          + "COURSE_TEACHER.COURSE_ID, "
          + "COURSE_TEACHER.\"SECTION\", "
          + "EMPLOYEES.DEPT_OFFICE, "
          + "MST_DEPT_OFFICE.SHORT_NAME, "
          + "EMP_PERSONAL_INFO.FIRST_NAME, "
          + "EMP_PERSONAL_INFO.LAST_NAME "
          + "FROM COURSE_TEACHER,EMPLOYEES,EMP_PERSONAL_INFO,MST_DEPT_OFFICE "
          + "WHERE COURSE_TEACHER.TEACHER_ID=EMPLOYEES.EMPLOYEE_ID AND COURSE_TEACHER.TEACHER_ID=EMP_PERSONAL_INFO.EMPLOYEE_ID AND "
          + "MST_DEPT_OFFICE.DEPT_ID=EMPLOYEES.DEPT_OFFICE AND COURSE_TEACHER.SEMESTER_ID=? and COURSE_TEACHER.COURSE_ID=? and COURSE_TEACHER.\"SECTION\"=?";

  String getFacultyMembers =
      "select a.EMPLOYEE_ID, "
          + "b.FIRST_NAME, "
          + "b.LAST_NAME, "
          + "MST_DEPT_OFFICE.SHORT_NAME, "
          + "c.DESIGNATION_NAME "
          + "from EMPLOYEES a,EMP_PERSONAL_INFO b,MST_DESIGNATION c,MST_DEPT_OFFICE WHERE a.DEPT_OFFICE=?  AND a.EMPLOYEE_TYPE=1 AND "
          + "a.EMPLOYEE_ID=b.EMPLOYEE_ID AND " + "a.DESIGNATION=c.DESIGNATION_ID AND "
          + "MST_DEPT_OFFICE.DEPT_ID=a.DEPT_OFFICE";

  String getAssignedCourses =
      "SELECT a.TEACHER_ID,a.COURSE_ID,b.COURSE_NO,b.COURSE_TITLE,a.\"SECTION\",a.SEMESTER_ID from COURSE_TEACHER a,MST_COURSE b WHERE a.TEACHER_ID=? and a.SEMESTER_ID=? "
          + " AND a.COURSE_ID=b.COURSE_ID AND b.COURSE_TYPE=1";

  @Override
  public List<ApplicationTES> getAssignedCourses(String pFacultyId, Integer pSemesterId) {
    String query = getAssignedCourses;
    return mJdbcTemplate.query(query, new Object[] {pFacultyId, pSemesterId},
        new ApplicationTESRowMapperForAssignedCourses());
  }

  @Override
  public List<ApplicationTES> getFacultyMembers(String pDeptId) {
    String query = getFacultyMembers;
    return mJdbcTemplate.query(query, new Object[] {pDeptId}, new ApplicationTESRowMapperForFacultyMembers());
  }

  @Override
  public List<ApplicationTES> getAlreadyReviewdCourses(String pStudentId, Integer pSemesterId) {
    String query = ALREADY_REVIEWED;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new ApplicationTESRowMapperForGetAlreadyReviewedCourses());
  }

  @Override
  public  List<Long>  create(List<MutableApplicationTES>  pMutableList)  {
    List<Object[]>  parameters  =  getInsertParamList(pMutableList);
    List<Object[]>  parametersNew  =  getInsertParamListNew(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ONE,  parameters);
    mJdbcTemplate.batchUpdate(INSERT_TWO,  parametersNew);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getQuestionId(), app.getPoint(), app.getComment(),
          app.getTeacherId(), app.getObservationType()});
    }

    return params;
  }

  private List<Object[]> getInsertParamListNew(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {app.getReviewEligibleCourses(), app.getStudentId(), app.getSemester(),
          app.getTeacherId()});
    }

    return params;
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    String query = getAllQuestions;
    return mJdbcTemplate.query(query, new ApplicationTESRowMapperForGetAllQuestions());
  }

  @Override
  public List<ApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId, String pCourseType) {
    String query = getAllReviewEligibleCoures;
    Integer cType = pCourseType.equals("Theory") ? 1 : 2;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pStudentId, cType},
        new ApplicationTESRowMapperForGetAllReviewEligiblecourses());
  }

  @Override
  public String getSemesterName(Integer pCurrentSemester) {
    String query = getSemesterName;
    return mJdbcTemplate.queryForObject(query, new Object[] {pCurrentSemester}, String.class);
  }

  @Override
  public List<ApplicationTES> getTeachersInfo(String pCourseId, Integer pSemesterId, String pSection) {
    String query = getTeachersInfo;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pSection},
        new ApplicationTESRowMapperForGetAllTeachersInfo());
  }

  class ApplicationTESRowMapperForGetAllQuestions implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getInt("QUESTION_ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION_DETAILS"));
      application.setObservationType((pResultSet.getInt("OBSERVATION_TYPE")));
      return application;
    }
  }

  class ApplicationTESRowMapperForGetAllReviewEligiblecourses implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      return application;
    }
  }
  class ApplicationTESRowMapperForAssignedCourses implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setSection(pResultSet.getString("SECTION"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      return application;
    }
  }
  class ApplicationTESRowMapperForFacultyMembers implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("EMPLOYEE_ID"));
      application.setFirstName(pResultSet.getString("FIRST_NAME"));
      application.setLastName(pResultSet.getString("LAST_NAME"));
      application.setDeptShortName(pResultSet.getString("SHORT_NAME"));
      application.setDesignation(pResultSet.getString("DESIGNATION_NAME"));
      return application;
    }
  }
  class ApplicationTESRowMapperForGetAlreadyReviewedCourses implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("FACULTY_ID"));
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setFirstName(pResultSet.getString("FIRST_NAME"));
      application.setLastName(pResultSet.getString("LAST_NAME"));
      return application;
    }
  }

  class ApplicationTESRowMapperForGetAllTeachersInfo implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setSection(pResultSet.getString("SECTION"));
      application.setDeptId(pResultSet.getString("DEPT_OFFICE"));
      application.setDeptShortName(pResultSet.getString("SHORT_NAME"));
      application.setFirstName(pResultSet.getString("FIRST_NAME"));
      application.setLastName(pResultSet.getString("LAST_NAME"));
      return application;
    }
  }

}
