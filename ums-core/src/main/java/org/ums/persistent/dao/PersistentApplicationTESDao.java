package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationTESDaoDecorator;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentApplicationTES;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

  String getAllQuestions = "SELECT QUESTION_ID,QUESTION_DETAILS from APPLICATION_TES_QUESTIONS";

  String getAllReviewEligibleCoures =
      "SELECT  DISTINCT  UG_REGISTRATION_RESULT.COURSE_ID,MST_COURSE.COURSE_TITLE,MST_COURSE.COURSE_NO  "
          + "FROM UG_REGISTRATION_RESULT, COURSE_TEACHER,MST_COURSE  "
          + "WHERE  "
          + "  UG_REGISTRATION_RESULT.SEMESTER_ID = ? AND UG_REGISTRATION_RESULT.SEMESTER_ID = COURSE_TEACHER.SEMESTER_ID AND  "
          + "  UG_REGISTRATION_RESULT.COURSE_ID = COURSE_TEACHER.COURSE_ID AND UG_REGISTRATION_RESULT.STUDENT_ID = ? AND  "
          + "  UG_REGISTRATION_RESULT.COURSE_ID=MST_COURSE.COURSE_ID AND MST_COURSE.COURSE_TYPE=?";

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

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    String query = getAllQuestions;
    return mJdbcTemplate.query(query, new ApplicationCCIRowMapperForGetAllQuestions());
  }

  @Override
  public List<ApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId, String pCourseType) {
    String query = getAllReviewEligibleCoures;
    Integer cType = pCourseType.equals("Theory") ? 1 : 2;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pStudentId, cType},
        new ApplicationCCIRowMapperForGetAllReviewEligiblecourses());
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
        new ApplicationCCIRowMapperForGetAllTeachersInfo());
  }

  class ApplicationCCIRowMapperForGetAllQuestions implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getInt("QUESTION_ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION_DETAILS"));
      return application;
    }
  }

  class ApplicationCCIRowMapperForGetAllReviewEligiblecourses implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      return application;
    }
  }

  class ApplicationCCIRowMapperForGetAllTeachersInfo implements RowMapper<ApplicationTES> {
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
