package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationTESDaoDecorator;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.enums.tes.TesStatus;
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

  String DELETE_ALL = "DELETE FROM TES_SET_QUESTIONS";
  String SELECT_ALL_QUESTIONS =
      "SELECT a.ID,a.QUESTION,a.OBSERVATION_TYPE from TES_QUESTIONS a,TES_SET_QUESTIONS b WHERE  "
          + "a.ID=b.ID AND b.SEMESTER_ID=?";
  String SET_QUESTIONS = "Insert  into  TES_SET_QUESTIONS (ID,SEMESTER_ID,INSERTED_ON)  values  (?,?,sysdate)";
  String ADD_QUESTIONS =
      "Insert  into  TES_QUESTIONS (ID,QUESTION,OBSERVATION_TYPE,INSERTED_ON)  values  (?,?,?,sysdate)";

  String INSERT_TES_SCORE =
      "Insert  into  TES_SCORE  (ID,SEMESTER_ID,STUDENT_ID,COURSE_ID,TEACHER_ID,QUESTION_ID,RATINGS,COMMENTS,INSERTED_ON) values  (?,?,?,?,?,?,?,?,sysdate)";
  String INSERT_ASSIGNED_COURSES =
      "Insert  into  TES_SELECTED_COURSES  (ID,COURSE_ID,SEMESTER_ID,TEACHER_ID,DEPT_ID,ASSIGNED_SECTION,INSERTED_ON)  values  (?,?,?,?,?,?,sysdate)";
  String ALREADY_REVIEWED =
      "SELECT  DISTINCT  COURSE_ID,STUDENT_ID,SEMESTER_ID,TEACHER_ID from TES_SCORE WHERE STUDENT_ID=? AND SEMESTER_ID=?";
  String COURSES_FOR_MAPPING =
      "SELECT COURSE_ID,TEACHER_ID,SEMESTER_ID,ASSIGNED_SECTION FROM TES_SELECTED_COURSES WHERE TEACHER_ID=? and SEMESTER_ID=?";

  String REVIEW_ELIGIBLE_COURSES =
      "SELECT DISTINCT  a.STUDENT_ID, a.SEMESTER_ID,a.COURSE_ID, b.COURSE_NO,b.COURSE_TITLE,c.TEACHER_ID,d.ASSIGNED_SECTION,e.FIRST_NAME,e.LAST_NAME "
          + "FROM UG_REGISTRATION_RESULT a,MST_COURSE b,COURSE_TEACHER c,TES_SELECTED_COURSES d,EMP_PERSONAL_INFO e "
          + "WHERE "
          + "  a.SEMESTER_ID = ? ANd a.STUDENT_ID= ? AND b.COURSE_TYPE= ? AND a.COURSE_ID=b.COURSE_ID  AND "
          + "  a.SEMESTER_ID=c.SEMESTER_ID AND a.COURSE_ID=c.COURSE_ID AND  a.SEMESTER_ID=c.SEMESTER_ID AND c.\"SECTION\"= ? AND "
          + "  d.COURSE_ID=a.COURSE_ID AND (c.COURSE_ID=d.COURSE_ID and c.TEACHER_ID=d.TEACHER_ID AND c.\"SECTION\"=d.ASSIGNED_SECTION AND c.SEMESTER_ID=d.SEMESTER_ID) AND "
          + "  e.EMPLOYEE_ID=d.TEACHER_ID";

  String TOTAL_TEACHERS_RECORD =
      "select DISTINCT  COUNT (a.EMPLOYEE_ID) "
          + "from EMPLOYEES a,EMP_PERSONAL_INFO b,MST_DESIGNATION c,MST_DEPT_OFFICE WHERE a.DEPT_OFFICE=?  AND a.EMPLOYEE_TYPE=1 AND "
          + "a.EMPLOYEE_ID=b.EMPLOYEE_ID AND " + "a.DESIGNATION=c.DESIGNATION_ID AND "
          + "MST_DEPT_OFFICE.DEPT_ID=a.DEPT_OFFICE";

  String ASSIGNED_COURSE_INFO =
      "SELECT c.FIRST_NAME,c.LAST_NAME,b.COURSE_NO,b.COURSE_TITLE,a.SEMESTER_ID,a.ASSIGNED_SECTION,to_char(a.INSERTED_ON,'DD-MM-YYYY') INSERTED_ON FROM TES_SELECTED_COURSES a,MST_COURSE b,EMP_PERSONAL_INFO c "
          + "WHERE   a.SEMESTER_ID=? AND  a.COURSE_ID=b.COURSE_ID AND a.TEACHER_ID=c.EMPLOYEE_ID AND a.DEPT_ID=?";

  String TEACHER_INFO =
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

  String TEACHERS_BY_DEPT = "select EMPLOYEE_ID from EMPLOYEES  WHERE DEPT_OFFICE=? AND EMPLOYEE_TYPE=1";

  String ASSIGNED_COURSES =
      "SELECT a.TEACHER_ID,a.COURSE_ID,b.COURSE_NO,b.COURSE_TITLE,a.\"SECTION\",a.SEMESTER_ID from COURSE_TEACHER a,MST_COURSE b WHERE a.TEACHER_ID=? and a.SEMESTER_ID=? "
          + " AND a.COURSE_ID=b.COURSE_ID AND b.COURSE_TYPE=1";

  String CONFIRMATION_BEFORE_REVIEW_SUBMISSION =
      " SELECT  a.QUESTION_ID,b.QUESTION,a.RATINGS,a.COMMENTS,b.OBSERVATION_TYPE from TES_SCORE a,TES_QUESTIONS b "
          + " WHERE a.STUDENT_ID=? AND a.SEMESTER_ID=?  AND a.COURSE_ID=? AND a.TEACHER_ID=? AND a.QUESTION_ID=b.ID";

  String TOTAL_NUMBER_OF_STUDENT_REVIEWED =
      "SELECT  COUNT (DISTINCT STUDENT_ID) as STUDENT_NO from TES_SCORE WHERE  TEACHER_ID=? AND COURSE_ID= ? AND SEMESTER_ID= ?  ORDER BY STUDENT_ID";
  String AVG_SCORE_OF_RATINGS =
      "SELECT  sum(RATINGS) as average  from TES_SCORE WHERE TEACHER_ID=? AND COURSE_ID= ? AND QUESTION_ID= ?  and SEMESTER_ID= ? ORDER BY STUDENT_ID,QUESTION_ID";
  String DETAILED_EVALUATION_RESULT =
      "SELECT  a.STUDENT_ID,a.QUESTION_ID,a.RATINGS,b.OBSERVATION_TYPE,a.COMMENTS from TES_SCORE a,TES_QUESTIONS b WHERE a.TEACHER_ID=? AND a.COURSE_ID= ? AND a.SEMESTER_ID= ? "
          + " AND a.QUESTION_ID=b.ID ORDER BY a.STUDENT_ID,a.QUESTION_ID";

  String ASSIGNED_BY_HEAD_FOR_REVIEW_COURSES =
      "SELECT DISTINCT  a.TEACHER_ID,a.SEMESTER_ID, a.COURSE_ID,b.COURSE_NO,b.COURSE_TITLE,a.DEPT_ID from TES_SELECTED_COURSES a,MST_COURSE b WHERE a.TEACHER_ID=? and a.SEMESTER_ID=? and a.COURSE_ID=b.COURSE_ID";

  String COURSE_DEPARTMENT_MAP =
      "select PROGRAM_SHORT_NAME from MST_PROGRAM where PROGRAM_ID=( "
          + "select PROGRAM_ID from SEMESTER_SYLLABUS_MAP where SEMESTER_ID= ? and (SYLLABUS_ID, Year, semester) in ( "
          + "select SYLLABUS_ID, MST_COURSE.\"YEAR\", MST_COURSE.SEMESTER from COURSE_SYLLABUS_MAP, MST_COURSE WHERE COURSE_SYLLABUS_MAP.COURSE_ID= ? and "
          + "MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID))";

  String ELIGIBLE_FACULTY_MEMBERS =
      "Select DISTINCT TEACHER_ID  from TES_SELECTED_COURSES WHERE DEPT_ID=? AND SEMESTER_ID=? ";

  String SEMESTER_PARAMETER =
      "SELECT SEMESTER_ID,to_char(START_DATE,'DD-MM-YYYY') START_DATE,to_char(END_DATE,'DD-MM-YYYY') END_DATE from MST_PARAMETER_SETTING WHERE SEMESTER_ID=? and PARAMETER_ID=?";

  String DEPT_WISE_FACULTY_LIST_FOR_REPORT_GENERATION =
      "select DISTINCT TEACHER_ID from TES_SELECTED_COURSES WHERE  DEPT_ID=? AND SEMESTER_ID=?";

  String ALL_FACULTY_LIST_FOR_REPORT_GENERATION =
      "select DISTINCT TEACHER_ID from TES_SELECTED_COURSES WHERE SEMESTER_ID=?";

  String PARAMETER_FOR_REPORT_GENERATION =
      "SELECT DISTINCT COURSE_ID,TEACHER_ID,SEMESTER_ID,DEPT_ID from TES_SELECTED_COURSES WHERE  TEACHER_ID=? AND SEMESTER_ID=?";

  String GET_TOTAL_REGISTERED_STUDENTS_FOR_A_COURSE =
      "SELECT  COUNT (a.STUDENT_ID) FROM UG_REGISTRATION_RESULT_CURR a,STUDENTS b WHERE  a.COURSE_ID=? AND b.THEORY_SECTION=? AND a.SEMESTER_ID=? "
          + "AND  a.STUDENT_ID=b.STUDENT_ID AND b.DEPT_ID=(select DEPT_ID from MST_PROGRAM where PROGRAM_ID=( "
          + "select PROGRAM_ID from SEMESTER_SYLLABUS_MAP where SEMESTER_ID=? and (SYLLABUS_ID, Year, semester) in ( "
          + "select SYLLABUS_ID, MST_COURSE.\"YEAR\", MST_COURSE.SEMESTER from COURSE_SYLLABUS_MAP, MST_COURSE WHERE COURSE_SYLLABUS_MAP.COURSE_ID= ? and "
          + "MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID)))";

  String SEMESTER_WISE_QUESTIONS = "SELECT ID,QUESTION,OBSERVATION_TYPE FROM TES_QUESTIONS";
  String QUESTION_SEMESTER_MAP = "select ID,SEMESTER_ID from TES_SET_QUESTIONS WHERE SEMESTER_ID=?";
  String SECTIONS_NUMBERS_FOR_A_COURSE_HAS_BEEN_ASSIGNED_FOR_A_SEMESTER_FOR_EVALUATION =
      "SELECT COURSE_ID,ASSIGNED_SECTION from TES_SELECTED_COURSES WHERE COURSE_ID=? AND SEMESTER_ID=? AND TEACHER_ID=? ORDER BY ASSIGNED_SECTION";
  String ALL_SECTIONS_FOR_A_COURSE =
      "select COURSE_ID,\"SECTION\" from COURSE_TEACHER WHERE COURSE_ID=? AND SEMESTER_ID=? AND TEACHER_ID=? ORDER BY \"SECTION\"";
  String DEPT_OF_TEACHERS_BY_TEACHER_ID = "SELECT DISTINCT  DEPT_ID FROM MST_PROGRAM WHERE CATEGORY_ID=?";
  String COURSES_FOR_QUESTION_WISE_REPORT =
      "SELECT DISTINCT a.COURSE_ID FROM MST_COURSE a,COURSE_TEACHER b WHERE a.COURSE_TYPE=1 AND a.OFFER_BY=? AND a.\"YEAR\"=? AND a.SEMESTER=? AND b.SEMESTER_ID=? AND a.COURSE_ID=b.COURSE_ID";

  String TEACHERS_FOR_QUESTION_WISE_REPORT =
      "SELECT DISTINCT TEACHER_ID FROM COURSE_TEACHER WHERE  COURSE_ID=? AND  SEMESTER_ID=?";

  @Override
  public List<ApplicationTES> getCourseForQuestionWiseReport(String pDeptId, Integer pYear, Integer pSemester,
      Integer pSemesterId) {
    String query = COURSES_FOR_QUESTION_WISE_REPORT;
    return mJdbcTemplate.query(query, new Object[] {pDeptId, pYear, pSemester, pSemesterId},
        new ApplicationTesRowMapperForQuestionWiseReport());
  }

  @Override
  public List<ApplicationTES> getTeacherListForQuestionWiseReport(String pCourseId, Integer pSemesterId) {
    String query = TEACHERS_FOR_QUESTION_WISE_REPORT;
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId},
        new ApplicationTesRowMapperForFacultyListReport());
  }

  @Override
  public List<ApplicationTES> getDeptListByFacultyId(Integer pFacultyId) {
    String query = DEPT_OF_TEACHERS_BY_TEACHER_ID;
    return mJdbcTemplate.query(query, new Object[] {pFacultyId}, new ApplicationTesRowMapperForDeptList());
  }

  @Override
  public List<ApplicationTES> getSectionList(String pCourseId, Integer pSemesterId, String pTeacherId) {
    String query = SECTIONS_NUMBERS_FOR_A_COURSE_HAS_BEEN_ASSIGNED_FOR_A_SEMESTER_FOR_EVALUATION;
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId, pTeacherId},
        new ApplicationTesRowMapperForSectionList());
  }

  @Override
  public List<ApplicationTES> getAllSectionForSelectedCourse(String pCourseId, String pTeacherId, Integer pSemesterId) {
    String query = ALL_SECTIONS_FOR_A_COURSE;
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId, pTeacherId},
        new ApplicationTesRowMapperForAllSection());
  }

  @Override
  public List<ApplicationTES> getQuestionSemesterMap(Integer pSemesterId) {
    String query = QUESTION_SEMESTER_MAP;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTesRowMapperForQuestionsMapping());
  }

  @Override
  public List<MutableApplicationTES> getQuestions() {
    String query = SEMESTER_WISE_QUESTIONS;
    return mJdbcTemplate.query(query, new ApplicationTesRowMapperForQuestions());
  }

  @Override
  public Integer getTotalRegisteredStudentForCourse(String pCourseId, String pSection, Integer pSemesterId) {
    String query = GET_TOTAL_REGISTERED_STUDENTS_FOR_A_COURSE;
    return mJdbcTemplate.queryForObject(query, new Object[] {pCourseId, pSection, pSemesterId, pSemesterId, pCourseId},
        Integer.class);
  }

  @Override
  public List<ApplicationTES> getParametersForReport(String pTeacherId, Integer pSemesterId) {
    String query = PARAMETER_FOR_REPORT_GENERATION;
    return mJdbcTemplate.query(query, new Object[] {pTeacherId, pSemesterId},
        new ApplicationTesRowMapperForReportParameter());
  }

  @Override
  public List<ApplicationTES> getFacultyListForReport(String pDeptId, Integer pSemesterId) {
    String query = "";
    if(pDeptId.equals(TesStatus.ALL_DEPARTMENT.getValue()) || pDeptId.equals(TesStatus.MAXIMUM_SCORE_HOLDER.getValue())
        || pDeptId.equals(TesStatus.MINIMUM_SCORE_HOLDER.getValue())
        || pDeptId.equals(TesStatus.FACULTY_OF_ENGINEERING.getValue())
        || pDeptId.equals(TesStatus.FACULTY_OF_BUSINESS_AND_SOCIAL_SCIENCE.getValue())
        || pDeptId.equals(TesStatus.FACULTY_OF_ARCHITECTURE.getValue())) {
      query = ALL_FACULTY_LIST_FOR_REPORT_GENERATION;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTesRowMapperForFacultyListReport());
    }
    else {
      query = DEPT_WISE_FACULTY_LIST_FOR_REPORT_GENERATION;
      return mJdbcTemplate.query(query, new Object[] {pDeptId, pSemesterId},
          new ApplicationTesRowMapperForFacultyListReport());
    }
  }

  @Override
  public List<ApplicationTES> getDeadlines(String pParameterId, Integer pSemesterId) {
    String query = SEMESTER_PARAMETER;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pParameterId},
        new ApplicationTesRowMapperForSemesterParameter());
  }

  @Override
  public List<MutableApplicationTES> getEligibleFacultyMembers(String pDeptId, Integer pSemesterId) {
    String query = ELIGIBLE_FACULTY_MEMBERS;
    return mJdbcTemplate.query(query, new Object[] {pDeptId, pSemesterId}, new ApplicationTesRowMapperForTeachers());
  }

  @Override
  public String getCourseDepartmentMap(String pCourseId, Integer pSemesterId) {
    String query = COURSE_DEPARTMENT_MAP;
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, pCourseId}, String.class);
  }

  @Override
  public List<ApplicationTES> getAssignedReviewableCoursesList(String pTeacherId, Integer pSemesterId) {
    String query = ASSIGNED_BY_HEAD_FOR_REVIEW_COURSES;
    return mJdbcTemplate.query(query, new Object[] {pTeacherId, pSemesterId},
        new ApplicationTesRowMapperForAssignedByHeadReviewableCoursesList());
  }

  @Override
  public Integer getTotalStudentNumber(String pTeacherId, String pCourseId, Integer pSemesterId) {
    String query = TOTAL_NUMBER_OF_STUDENT_REVIEWED;
    return mJdbcTemplate.queryForObject(query, new Object[] {pTeacherId, pCourseId, pSemesterId}, Integer.class);
  }

  @Override
  public Double getAverageScore(String pTeacherId, String pCourseId, Long pQuestionId, Integer pSemesterId) {
    String query = AVG_SCORE_OF_RATINGS;
    double x =
        mJdbcTemplate.queryForObject(query, new Object[] {pTeacherId, pCourseId, pQuestionId, pSemesterId},
            Double.class);
    return x;
  }

  @Override
  public List<ApplicationTES> getDetailedResult(String pTeacherId, String pCourseId, Integer pSemesterId) {
    String query = DETAILED_EVALUATION_RESULT;
    return mJdbcTemplate.query(query, new Object[] {pTeacherId, pCourseId, pSemesterId},
        new ApplicationTesRowMapperForDetailedResult());
  }

  @Override
  public List<ApplicationTES> getReviewedCoursesForReadOnlyMode(String pCourseId, String pTeacherId, String pStudentId,
      Integer pSemesterId) {
    String query = CONFIRMATION_BEFORE_REVIEW_SUBMISSION;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId, pCourseId, pTeacherId},
        new ApplicationTesRowMapperForAllQuestions());
  }

  @Override
  public List<ApplicationTES> getAssignedCoursesByHead(String pFacultyId, Integer pSemesterId) {
    String query = COURSES_FOR_MAPPING;
    return mJdbcTemplate.query(query, new Object[] {pFacultyId, pSemesterId},
        new ApplicationTesRowMapperForAssignedCoursesForMapping());
  }

  @Override
  public List<MutableApplicationTES> getAssignedCourses(String pFacultyId, Integer pSemesterId) {
    String query = ASSIGNED_COURSES;
    return mJdbcTemplate.query(query, new Object[] {pFacultyId, pSemesterId},
        new ApplicationTesRowMapperForAssignedCourses());
  }

  @Override
  public List<MutableApplicationTES> getFacultyMembers(String pDeptId) {
    String query = TEACHERS_BY_DEPT;
    return mJdbcTemplate.query(query, new Object[] {pDeptId}, new ApplicationTesRowMapperForEmployeeMembers());
  }

  @Override
  public List<ApplicationTES> getAlreadyReviewedCourses(String pStudentId, Integer pSemesterId) {
    String query = ALREADY_REVIEWED;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new ApplicationTesRowMapperForGetAlreadyReviewedCourses());
  }

  @Override
  public Long addQuestions(MutableApplicationTES pMutableList) {
    String query = ADD_QUESTIONS;
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(query, id, pMutableList.getQuestionDetails(), pMutableList.getObservationType());
    return id;
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
  public List<Long> setQuestions(List<MutableApplicationTES> pMutableList) {
    List<Object[]>  parameters  =  getInsertParamListForSetQuestions(pMutableList);
    mJdbcTemplate.batchUpdate(SET_QUESTIONS,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public  List<Long>  create(List<MutableApplicationTES>  pMutableList)  {
    List<Object[]>  parameters  =  getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_TES_SCORE,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public int delete(List<MutableApplicationTES> pMutableList) {
    String query = DELETE_ALL + " WHERE ID=? AND SEMESTER_ID=?";
    List<Object[]> parameters = deleteParamList(pMutableList);
    return mJdbcTemplate.batchUpdate(query, parameters).length;
  }

  private List<Object[]> deleteParamList(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {app.getQuestionId(), app.getSemester()});
    }
    return params;
  }

  private List<Object[]> getInsertParamList(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getSemester(), app.getStudentId(),
          app.getReviewEligibleCourseId(), app.getTeacherId(), app.getQuestionId(), app.getPoint(), app.getComment(),});
    }

    return params;
  }

  private List<Object[]> getInsertParamListForHeadTes(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getReviewEligibleCourseId(), app.getSemester(),
          app.getTeacherId(), app.getDeptId(), app.getSection()});
    }

    return params;
  }

  private List<Object[]> getInsertParamListForSetQuestions(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {app.getQuestionId(), app.getSemester()});
    }
    return params;
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    String query = SELECT_ALL_QUESTIONS;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTesRowMapperForGetAllQuestions());
  }

  @Override
  public List<MutableApplicationTES> getMigrationQuestions(Integer pSemesterId) {
    String query = SELECT_ALL_QUESTIONS;
    return mJdbcTemplate
        .query(query, new Object[] {pSemesterId}, new ApplicationTesRowMapperForGetMigrationQuestions());
  }

  @Override
  public List<MutableApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId,
      String pCourseType, String pSection) {
    String query = REVIEW_ELIGIBLE_COURSES;
    Integer cType = pCourseType.equals("Theory") ? 1 : 2;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pStudentId, cType, pSection},
        new ApplicationTesRowMapperForGetAllReviewEligiblecourses());
  }

  @Override
  public List<ApplicationTES> getRecordsOfAssignedCoursesByHead(Integer pSemesterId, String pDeptId) {
    String query = ASSIGNED_COURSE_INFO;// ApplicationTESRowMapperForAssignedCourses
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pDeptId},
        new ApplicationTesRowMapperForAssignedCoursesForRecords());
  }

  @Override
  public Integer getTotalRecords(String pDeptId) {
    String query = TOTAL_TEACHERS_RECORD;
    return mJdbcTemplate.queryForObject(query, new Object[] {pDeptId}, Integer.class);
  }

  @Override
  public List<ApplicationTES> getTeachersInfo(String pCourseId, Integer pSemesterId, String pSection) {
    String query = TEACHER_INFO;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pSection},
        new ApplicationTESRowMapperForGetAllTeachersInfo());
  }

  class ApplicationTesRowMapperForGetAllQuestions implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      getData(pResultSet, application);
      return application;
    }
  }

  private void getData(ResultSet pResultSet, PersistentApplicationTES application) throws SQLException {
    application.setQuestionId(pResultSet.getLong("ID"));
    application.setQuestionDetails(pResultSet.getString("QUESTION"));
    application.setObservationType((pResultSet.getInt("OBSERVATION_TYPE")));
  }

  class ApplicationTesRowMapperForGetMigrationQuestions implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      getData(pResultSet, application);
      return application;
    }
  }
  class ApplicationTesRowMapperForAllQuestions implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getLong("Question_ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION"));
      application.setObservationType((pResultSet.getInt("OBSERVATION_TYPE")));
      application.setPoint(pResultSet.getInt("RATINGS"));
      application.setComment(pResultSet.getString("COMMENTS"));

      return application;
    }
  }
  class ApplicationTesRowMapperForAssignedByHeadReviewableCoursesList implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setDeptId(pResultSet.getString("DEPT_ID"));
      return application;
    }
  }

  class ApplicationTesRowMapperForSemesterParameter implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setSemesterStartDate(pResultSet.getString("START_DATE"));
      application.setSemesterEndDate(pResultSet.getString("END_DATE"));
      return application;
    }
  }
  class ApplicationTesRowMapperForFacultyListReport implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      return application;
    }
  }
  class ApplicationTesRowMapperForQuestionWiseReport implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      return application;
    }
  }
  class ApplicationTesRowMapperForQuestions implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getLong("ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION"));
      application.setObservationType(pResultSet.getInt("OBSERVATION_TYPE"));
      return application;
    }
  }
  class ApplicationTesRowMapperForAllSection implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setSection(pResultSet.getString("SECTION"));
      return application;
    }
  }
  class ApplicationTesRowMapperForSectionList implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      return application;
    }
  }
  class ApplicationTesRowMapperForQuestionsMapping implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getLong("ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      return application;
    }
  }
  class ApplicationTesRowMapperForDeptList implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setDeptId(pResultSet.getString("DEPT_ID"));
      return application;
    }
  }
  class ApplicationTesRowMapperForReportParameter implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setDeptId(pResultSet.getString("DEPT_ID"));
      return application;
    }
  }

  class ApplicationTESRowMapperForSemesterNameList implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setSemesterName(pResultSet.getString("SEMESTER_NAME"));
      return application;
    }
  }

  class ApplicationTesRowMapperForDetailedResult implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setQuestionId(pResultSet.getLong("QUESTION_ID"));
      application.setPoint(pResultSet.getInt("RATINGS"));
      application.setObservationType(pResultSet.getInt("OBSERVATION_TYPE"));
      application.setComment(pResultSet.getString("COMMENTS"));
      return application;
    }
  }

  class ApplicationTesRowMapperForGetAllReviewEligiblecourses implements RowMapper<MutableApplicationTES> {
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
  class ApplicationTesRowMapperForAssignedCourses implements RowMapper<MutableApplicationTES> {
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

  class ApplicationTesRowMapperForAssignedCoursesForMapping implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      return application;
    }
  }

  class ApplicationTesRowMapperForAssignedCoursesForRecords implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setFirstName(pResultSet.getString("FIRST_NAME"));
      application.setLastName(pResultSet.getString("LAST_NAME"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      application.setAppliedDate(pResultSet.getString("INSERTED_ON"));
      return application;
    }
  }
  class ApplicationTesRowMapperForEmployeeMembers implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("EMPLOYEE_ID"));
      return application;
    }
  }
  class ApplicationTesRowMapperForTeachers implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      return application;
    }
  }
  class ApplicationTesRowMapperForGetAlreadyReviewedCourses implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
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
