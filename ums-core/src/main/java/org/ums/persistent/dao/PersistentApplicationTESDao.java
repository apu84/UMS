package org.ums.persistent.dao;

import org.openxmlformats.schemas.spreadsheetml.x2006.main.STRef;
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
      "Insert  into  APPLICATION_TES_QINFO  (ID,QUESTION_ID,POINT,STUDENT_COMMENT,FACULTY_ID,OBSERVATION_TYPE,COURSE_ID,STUDENT_ID,SEMESTER_ID,APPLIED_ON)  values  (?,?,?,?,?,?,?,?,?,systimestamp)";
  String INSERT_ASSIGNED_COURSES =
      "Insert  into  TES_COURSE_ASSIGN  (ID,COURSE_ID,TEACHER_ID,SEMESTER_ID,ASSIGNED_SECTION,DEPT_ID,STATUS,APPLIED_ON)  values  (?,?,?,?,?,?,?,systimestamp)";
  String ALREADY_REVIEWED =
      "SELECT  DISTINCT  COURSE_ID,STUDENT_ID,SEMESTER_ID,FACULTY_ID from APPLICATION_TES_QINFO WHERE STUDENT_ID=? AND SEMESTER_ID=?";
  String getCoursesForMapping =
      "SELECT COURSE_ID,TEACHER_ID,SEMESTER_ID,ASSIGNED_SECTION,STATUS FROM TES_COURSE_ASSIGN WHERE TEACHER_ID=? and SEMESTER_ID=?";

  String getAllReviewEligibleCoures =
      "SELECT DISTINCT  a.STUDENT_ID, a.SEMESTER_ID,a.COURSE_ID, b.COURSE_NO,b.COURSE_TITLE,c.TEACHER_ID,d.ASSIGNED_SECTION,e.FIRST_NAME,e.LAST_NAME "
          + "FROM UG_REGISTRATION_RESULT a,MST_COURSE b,COURSE_TEACHER c,TES_COURSE_ASSIGN d,EMP_PERSONAL_INFO e "
          + "WHERE "
          + "  a.SEMESTER_ID = ? ANd a.STUDENT_ID= ? AND b.COURSE_TYPE= ? AND a.COURSE_ID=b.COURSE_ID  AND "
          + "  a.SEMESTER_ID=c.SEMESTER_ID AND a.COURSE_ID=c.COURSE_ID AND  a.SEMESTER_ID=c.SEMESTER_ID AND c.\"SECTION\"= ? AND "
          + "  d.COURSE_ID=a.COURSE_ID AND (c.COURSE_ID=d.COURSE_ID and c.TEACHER_ID=d.TEACHER_ID AND c.\"SECTION\"=d.ASSIGNED_SECTION) AND "
          + "  e.EMPLOYEE_ID=d.TEACHER_ID";

  String getSemesterName = "SELECT SEMESTER_NAME from MST_SEMESTER WHERE SEMESTER_ID=?";

  String getTotalTeachersRecords =
      "select DISTINCT  COUNT (a.EMPLOYEE_ID) "
          + "from EMPLOYEES a,EMP_PERSONAL_INFO b,MST_DESIGNATION c,MST_DEPT_OFFICE WHERE a.DEPT_OFFICE=?  AND a.EMPLOYEE_TYPE=1 AND "
          + "a.EMPLOYEE_ID=b.EMPLOYEE_ID AND " + "a.DESIGNATION=c.DESIGNATION_ID AND "
          + "MST_DEPT_OFFICE.DEPT_ID=a.DEPT_OFFICE";

  String getRecords =
      "SELECT c.FIRST_NAME,c.LAST_NAME,b.COURSE_NO,b.COURSE_TITLE,a.SEMESTER_ID,a.ASSIGNED_SECTION,to_char(a.applied_on,'DD-MM-YYYY') applied_on FROM TES_COURSE_ASSIGN a,MST_COURSE b,EMP_PERSONAL_INFO c "
          + "WHERE   a.SEMESTER_ID=? AND  a.COURSE_ID=b.COURSE_ID AND a.TEACHER_ID=c.EMPLOYEE_ID AND a.DEPT_ID=?";

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
          + " a.DEPT_OFFICE, "
          + "c.DESIGNATION_NAME "
          + "from EMPLOYEES a,EMP_PERSONAL_INFO b,MST_DESIGNATION c,MST_DEPT_OFFICE WHERE a.DEPT_OFFICE=?  AND a.EMPLOYEE_TYPE=1 AND "
          + "a.EMPLOYEE_ID=b.EMPLOYEE_ID AND " + "a.DESIGNATION=c.DESIGNATION_ID AND "
          + "MST_DEPT_OFFICE.DEPT_ID=a.DEPT_OFFICE";

  String getAssignedCourses =
      "SELECT a.TEACHER_ID,a.COURSE_ID,b.COURSE_NO,b.COURSE_TITLE,a.\"SECTION\",a.SEMESTER_ID from COURSE_TEACHER a,MST_COURSE b WHERE a.TEACHER_ID=? and a.SEMESTER_ID=? "
          + " AND a.COURSE_ID=b.COURSE_ID AND b.COURSE_TYPE=1";

  String getDataForReadOnly =
      " SELECT  a.QUESTION_ID,b.QUESTION_DETAILS, a.POINT,a.STUDENT_COMMENT,a.OBSERVATION_TYPE from APPLICATION_TES_QINFO a,APPLICATION_TES_QUESTIONS b "
          + " WHERE a.STUDENT_ID=? AND a.SEMESTER_ID=?  AND a.COURSE_ID=? AND a.FACULTY_ID=? AND a.QUESTION_ID=b.QUESTION_ID";

  @Override
  public List<ApplicationTES> getRivewedCoursesForReadOnlyMode(String pCourseId, String pTeacherId, String pStudentId,
      Integer pSemesterId) {
    String query = getDataForReadOnly;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId,pCourseId, pTeacherId},
        new ApplicationTESRowMapperForGetAllQuestionsReadOnly());
  }

  @Override
  public List<ApplicationTES> getAssignedCoursesByHead(String pFacultyId, Integer pSemesterId) {
    String query = getCoursesForMapping;
    return mJdbcTemplate.query(query, new Object[] {pFacultyId, pSemesterId},
        new ApplicationTESRowMapperForAssignedCoursesForMapping());
  }

  @Override
  public List<MutableApplicationTES> getAssignedCourses(String pFacultyId, Integer pSemesterId) {
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
  public List<Long> saveAssignedCourses(List<MutableApplicationTES> pMutableList) {
    List<Object[]>  parameters  =  getInsertParamListForHeadTes(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ASSIGNED_COURSES,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public  List<Long>  create(List<MutableApplicationTES>  pMutableList)  {
    List<Object[]>  parameters  =  getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ONE,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getQuestionId(), app.getPoint(), app.getComment(),
          app.getTeacherId(), app.getObservationType(), app.getReviewEligibleCourses(), app.getStudentId(),
          app.getSemester()});
    }

    return params;
  }

  private List<Object[]> getInsertParamListForHeadTes(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getReviewEligibleCourses(), app.getTeacherId(),
          app.getSemester(), app.getSection(), app.getDeptId(), app.getStatus()});
    }

    return params;
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    String query = getAllQuestions;
    return mJdbcTemplate.query(query, new ApplicationTESRowMapperForGetAllQuestions());
  }

  @Override
  public List<MutableApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId,
      String pCourseType, String pSection) {
    String query = getAllReviewEligibleCoures;
    Integer cType = pCourseType.equals("Theory") ? 1 : 2;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pStudentId, cType, pSection},
        new ApplicationTESRowMapperForGetAllReviewEligiblecourses());
  }

  @Override
  public List<ApplicationTES> getRecordsOfAssignedCoursesByHead(Integer pSemesterId, String pDeptId) {
    String query = getRecords;// ApplicationTESRowMapperForAssignedCourses
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pDeptId},
        new ApplicationTESRowMapperForAssignedCoursesForRecords());
  }

  @Override
  public String getSemesterName(Integer pCurrentSemester) {
    String query = getSemesterName;
    return mJdbcTemplate.queryForObject(query, new Object[] {pCurrentSemester}, String.class);
  }

  @Override
  public Integer getTotalRecords(String pDeptId) {
    String query = getTotalTeachersRecords;
    return mJdbcTemplate.queryForObject(query, new Object[] {pDeptId}, Integer.class);
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
  class ApplicationTESRowMapperForGetAllQuestionsReadOnly implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getInt("QUESTION_ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION_DETAILS"));
      application.setObservationType((pResultSet.getInt("OBSERVATION_TYPE")));
      application.setPoint(pResultSet.getInt("POINT"));
      application.setComment(pResultSet.getString("STUDENT_COMMENT"));
      return application;
    }
  }

  class ApplicationTESRowMapperForGetAllReviewEligiblecourses implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      application.setFirstName(pResultSet.getString("FIRST_NAME"));
      application.setLastName(pResultSet.getString("LAST_NAME"));
      return application;
    }
  }
  class ApplicationTESRowMapperForAssignedCourses implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
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

  class ApplicationTESRowMapperForAssignedCoursesForMapping implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      application.setStatus(pResultSet.getInt("STATUS"));
      return application;
    }
  }

  class ApplicationTESRowMapperForAssignedCoursesForRecords implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setFirstName(pResultSet.getString("FIRST_NAME"));
      application.setLastName(pResultSet.getString("LAST_NAME"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      application.setAppliedDate(pResultSet.getString("applied_on"));
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
      application.setDeptId(pResultSet.getString("DEPT_OFFICE"));
      application.setDeptShortName(pResultSet.getString("SHORT_NAME"));
      application.setDesignation(pResultSet.getString("DESIGNATION_NAME"));
      return application;
    }
  }
  class ApplicationTESRowMapperForGetAlreadyReviewedCourses implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setTeacherId(pResultSet.getString("FACULTY_ID"));
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
