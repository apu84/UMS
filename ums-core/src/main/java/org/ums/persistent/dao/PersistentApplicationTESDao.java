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

}
