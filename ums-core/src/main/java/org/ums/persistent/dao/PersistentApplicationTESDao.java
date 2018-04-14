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

  String DELETE_ALL = "DELETE FROM APPLICATION_TES_SET_QUESTIONS";
  String getAllQuestions =
      "SELECT a.QUESTION_ID,a.QUESTION_DETAILS,a.OBSERVATION_TYPE from APPLICATION_TES_QUESTIONS a,APPLICATION_TES_SET_QUESTIONS b WHERE  "
          + "a.QUESTION_ID=b.QUESTION_ID AND b.SEMESTER_ID=?";
  String setQuestion =
      "Insert  into  APPLICATION_TES_SET_QUESTIONS (ID,QUESTION_ID,SEMESTER_ID,STATUS,APPLIED_ON)  values  (?,?,?,?,systimestamp)";
  String addQuestion =
      "Insert  into  APPLICATION_TES_QUESTIONS (ID,QUESTION_ID,QUESTION_DETAILS,SEMESTER_ID,OBSERVATION_TYPE,APPLIED_ON)  values  (?,?,?,?,?,systimestamp)";

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
          + "  d.COURSE_ID=a.COURSE_ID AND (c.COURSE_ID=d.COURSE_ID and c.TEACHER_ID=d.TEACHER_ID AND c.\"SECTION\"=d.ASSIGNED_SECTION AND c.SEMESTER_ID=d.SEMESTER_ID) AND "
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

  String getTotalStudentNo =
      "SELECT  COUNT (DISTINCT STUDENT_ID) as STUDENT_NO from APPLICATION_TES_QINFO WHERE  FACULTY_ID= ? AND COURSE_ID= ? AND SEMESTER_ID= ?  ORDER BY STUDENT_ID";
  String getAvgScore =
      "SELECT  sum(POINT) as average  from APPLICATION_TES_QINFO WHERE FACULTY_ID= ? AND COURSE_ID= ? AND QUESTION_ID= ?  and SEMESTER_ID= ? ORDER BY STUDENT_ID,QUESTION_ID";
  String getDetailedScore =
      "SELECT  STUDENT_ID,QUESTION_ID,POINT,OBSERVATION_TYPE,STUDENT_COMMENT from APPLICATION_TES_QINFO WHERE FACULTY_ID= ? AND COURSE_ID= ? AND SEMESTER_ID= ? ORDER BY STUDENT_ID,QUESTION_ID";
  String getObservationType = "SELECT OBSERVATION_TYPE from APPLICATION_TES_QUESTIONS WHERE QUESTION_ID=?";

  String getQuestionDetails = "SELECT QUESTION_DETAILS from APPLICATION_TES_QUESTIONS WHERE QUESTION_ID=?";

  String getSemesterNameList = "SELECT SEMESTER_ID,SEMESTER_NAME from MST_SEMESTER ORDER BY START_DATE DESC";

  String getAssignedReviewableCoursesList =
      "SELECT DISTINCT  a.TEACHER_ID,a.SEMESTER_ID, a.COURSE_ID,b.COURSE_NO,b.COURSE_TITLE,a.DEPT_ID from TES_COURSE_ASSIGN a,MST_COURSE b WHERE a.TEACHER_ID=? and a.SEMESTER_ID=? and a.COURSE_ID=b.COURSE_ID";

  String getCourseDepartmentMap =
      "select PROGRAM_SHORT_NAME from MST_PROGRAM where PROGRAM_ID=( "
          + "select PROGRAM_ID from SEMESTER_SYLLABUS_MAP where SEMESTER_ID= ? and (SYLLABUS_ID, Year, semester) in ( "
          + "select SYLLABUS_ID, MST_COURSE.\"YEAR\", MST_COURSE.SEMESTER from COURSE_SYLLABUS_MAP, MST_COURSE WHERE COURSE_SYLLABUS_MAP.COURSE_ID= ? and "
          + "MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID))";

  String getEligibleFacultyMembers =
      "select  DISTINCT a.EMPLOYEE_ID, "
          + "          b.FIRST_NAME, "
          + "          b.LAST_NAME, "
          + "          MST_DEPT_OFFICE.SHORT_NAME, "
          + "          a.DEPT_OFFICE, "
          + "          c.DESIGNATION_NAME "
          + "          from EMPLOYEES a,EMP_PERSONAL_INFO b,MST_DESIGNATION c,MST_DEPT_OFFICE,TES_COURSE_ASSIGN d WHERE a.DEPT_OFFICE=?  AND a.EMPLOYEE_TYPE=1 AND "
          + "          a.EMPLOYEE_ID=b.EMPLOYEE_ID AND a.DESIGNATION=c.DESIGNATION_ID AND "
          + "          MST_DEPT_OFFICE.DEPT_ID=a.DEPT_OFFICE AND d.TEACHER_ID=a.EMPLOYEE_ID AND d.SEMESTER_ID=?";

  String getSemesterParameter =
      "SELECT SEMESTER_ID,to_char(START_DATE,'DD-MM-YYYY') START_DATE,to_char(END_DATE,'DD-MM-YYYY') END_DATE from MST_PARAMETER_SETTING WHERE SEMESTER_ID=? and PARAMETER_ID=?";

  String getFacultyListForReport =
      "select DISTINCT TEACHER_ID from TES_COURSE_ASSIGN WHERE  DEPT_ID=? AND SEMESTER_ID=?";

  String getAllFacultyListForReport = "select DISTINCT TEACHER_ID from TES_COURSE_ASSIGN WHERE SEMESTER_ID=?";

  String getParameterForReport =
      "SELECT DISTINCT COURSE_ID,TEACHER_ID,SEMESTER_ID,DEPT_ID from TES_COURSE_ASSIGN WHERE  TEACHER_ID=? AND SEMESTER_ID=?";
  String getDeptList = "SELECT DEPT_ID from MST_DEPT_OFFICE WHERE TYPE=1 ORDER  BY DEPT_ID";

  String getTotalRegisteredStudentForCourse =
      "SELECT  COUNT (a.STUDENT_ID) FROM UG_REGISTRATION_RESULT_CURR a,STUDENTS b WHERE  a.COURSE_ID=? AND b.THEORY_SECTION=? AND a.SEMESTER_ID=? "
          + "AND  a.STUDENT_ID=b.STUDENT_ID AND b.DEPT_ID=(select DEPT_ID from MST_PROGRAM where PROGRAM_ID=( "
          + "select PROGRAM_ID from SEMESTER_SYLLABUS_MAP where SEMESTER_ID=? and (SYLLABUS_ID, Year, semester) in ( "
          + "select SYLLABUS_ID, MST_COURSE.\"YEAR\", MST_COURSE.SEMESTER from COURSE_SYLLABUS_MAP, MST_COURSE WHERE COURSE_SYLLABUS_MAP.COURSE_ID= ? and "
          + "MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID)))";

  String getQuestions =
      "SELECT QUESTION_ID,QUESTION_DETAILS,SEMESTER_ID,OBSERVATION_TYPE FROM APPLICATION_TES_QUESTIONS";
  String getQuestionSemesterMap =
      "select QUESTION_ID,SEMESTER_ID,STATUS from APPLICATION_TES_SET_QUESTIONS WHERE SEMESTER_ID=?";
  String getSectionList =
      "SELECT COURSE_ID,ASSIGNED_SECTION from TES_COURSE_ASSIGN WHERE COURSE_ID=? AND SEMESTER_ID=? AND TEACHER_ID=? ORDER BY ASSIGNED_SECTION";
  String getAllSectionForSelectedCourse =
      "select COURSE_ID,\"SECTION\" from COURSE_TEACHER WHERE COURSE_ID=? AND SEMESTER_ID=? AND TEACHER_ID=? ORDER BY \"SECTION\"";
  String getDeptListByFacultyId = "SELECT DISTINCT  DEPT_ID FROM MST_PROGRAM WHERE CATEGORY_ID=?";
  String getCourseForQuestionWiseReport =
      "SELECT DISTINCT a.COURSE_ID FROM MST_COURSE a,COURSE_TEACHER b WHERE a.COURSE_TYPE=1 AND a.OFFER_BY=? AND a.\"YEAR\"=? AND a.SEMESTER=? AND b.SEMESTER_ID=? AND a.COURSE_ID=b.COURSE_ID";

  String getTeacherListForQuestionWiseReport =
      "SELECT DISTINCT TEACHER_ID FROM COURSE_TEACHER WHERE  COURSE_ID=? AND  SEMESTER_ID=?";

  @Override
  public List<ApplicationTES> getCourseForQuestionWiseReport(String pDeptId, Integer pYear, Integer pSemester,
      Integer pSemesterId) {
    String query = getCourseForQuestionWiseReport;
    return mJdbcTemplate.query(query, new Object[] {pDeptId, pYear, pSemester, pSemesterId},
        new ApplicationTESRowMapperForQuestionWiseReport());
  }

  @Override
  public List<ApplicationTES> getTeacherListForQuestionWiseReport(String pCourseId, Integer pSemesterId) {
    String query = getTeacherListForQuestionWiseReport;
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId},
        new ApplicationTESRowMapperForFacultyListReport());
  }

  @Override
  public List<ApplicationTES> getDeptListByFacultyId(Integer pFacultyId) {
    String query = getDeptListByFacultyId;
    return mJdbcTemplate.query(query, new Object[] {pFacultyId}, new ApplicationTESRowMapperForDeptList());
  }

  @Override
  public List<ApplicationTES> getSectionList(String pCourseId, Integer pSemesterId, String pTeacherId) {
    String query = getSectionList;
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId, pTeacherId},
        new ApplicationTESRowMapperForSectionList());
  }

  @Override
  public List<ApplicationTES> getAllSectionForSelectedCourse(String pCourseId, String pTeacherId, Integer pSemesterId) {
    String query = getAllSectionForSelectedCourse;
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId, pTeacherId},
        new ApplicationTESRowMapperForAllSection());
  }

  @Override
  public List<ApplicationTES> getQuestionSemesterMap(Integer pSemesterId) {
    String query = getQuestionSemesterMap;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTESRowMapperForQuestionsMapping());
  }

  @Override
  public List<MutableApplicationTES> getQuestions() {
    String query = getQuestions;
    return mJdbcTemplate.query(query, new ApplicationTESRowMapperForQuestions());
  }

  @Override
  public Integer getTotalRegisteredStudentForCourse(String pCourseId, String pSection, Integer pSemesterId) {
    String query = getTotalRegisteredStudentForCourse;
    return mJdbcTemplate.queryForObject(query, new Object[] {pCourseId, pSection, pSemesterId, pSemesterId, pCourseId},
        Integer.class);
  }

  @Override
  public List<ApplicationTES> getDeptList() {
    String query = getDeptList;
    return mJdbcTemplate.query(query, new ApplicationTESRowMapperForDeptList());
  }

  @Override
  public List<ApplicationTES> getParametersForReport(String pTeacherId, Integer pSemesterId) {
    String query = getParameterForReport;
    return mJdbcTemplate.query(query, new Object[] {pTeacherId, pSemesterId},
        new ApplicationTESRowMapperForReportParameter());
  }

  @Override
  public List<ApplicationTES> getFacultyListForReport(String pDeptId, Integer pSemesterId) {
    String query = "";
    if(pDeptId.equals("08") || pDeptId.equals("09") || pDeptId.equals("10") || pDeptId.equals("11")
        || pDeptId.equals("12") || pDeptId.equals("13")) {
      query = getAllFacultyListForReport;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTESRowMapperForFacultyListReport());
    }
    else {
      query = getFacultyListForReport;
      return mJdbcTemplate.query(query, new Object[] {pDeptId, pSemesterId},
          new ApplicationTESRowMapperForFacultyListReport());
    }
  }

  @Override
  public List<ApplicationTES> getDeadlines(String pParameterId, Integer pSemesterId) {
    String query = getSemesterParameter;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pParameterId},
        new ApplicationTESRowMapperForSemesterParameter());
  }

  @Override
  public List<ApplicationTES> getEligibleFacultyMembers(String pDeptId, Integer pSemesterId) {
    String query = getEligibleFacultyMembers;
    return mJdbcTemplate.query(query, new Object[] {pDeptId, pSemesterId},
        new ApplicationTESRowMapperForFacultyMembers());
  }

  @Override
  public String getCourseDepartmentMap(String pCourseId, Integer pSemesterId) {
    String query = getCourseDepartmentMap;
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, pCourseId}, String.class);

  }

  private String checkNull(String value) {
    if(value.equals("") || value.equals(null)) {
      return "None";
    }
    else {
      return value;
    }

  }

  @Override
  public List<ApplicationTES> getAssignedReviewableCoursesList(String pTeacherId, Integer pSemesterId) {
    String query = getAssignedReviewableCoursesList;
    return mJdbcTemplate.query(query, new Object[] {pTeacherId, pSemesterId},
        new ApplicationTESRowMapperForgetAssignedReviewableCoursesList());
  }

  @Override
  public List<ApplicationTES> getAllSemesterNameList() {
    String query = getSemesterNameList;
    return mJdbcTemplate.query(query, new ApplicationTESRowMapperForSemesterNameList());
  }

  @Override
  public String getQuestionDetails(Integer pQuestionId) {
    String query = getQuestionDetails;
    return mJdbcTemplate.queryForObject(query, new Object[] {pQuestionId}, String.class);
  }

  @Override
  public Integer getObservationType(Integer pQuestionId) {
    String query = getObservationType;
    return mJdbcTemplate.queryForObject(query, new Object[] {pQuestionId}, Integer.class);
  }

  @Override
  public Integer getTotalStudentNumber(String pTeacherId, String pCourseId, Integer pSemesterId) {
    String query = getTotalStudentNo;
    return mJdbcTemplate.queryForObject(query, new Object[] {pTeacherId, pCourseId, pSemesterId}, Integer.class);
  }

  @Override
  public Double getAverageScore(String pTeacherId, String pCourseId, Integer pQuestionId, Integer pSemesterId) {
    String query = getAvgScore;
    return mJdbcTemplate.queryForObject(query, new Object[] {pTeacherId, pCourseId, pQuestionId, pSemesterId},
        Double.class);
  }

  @Override
  public List<ApplicationTES> getDetailedResult(String pTeacherId, String pCourseId, Integer pSemesterId) {
    String query = getDetailedScore;
    return mJdbcTemplate.query(query, new Object[] {pTeacherId, pCourseId, pSemesterId},
        new ApplicationTESRowMapperForGetDetailedResult());
  }

  @Override
  public List<ApplicationTES> getRivewedCoursesForReadOnlyMode(String pCourseId, String pTeacherId, String pStudentId,
      Integer pSemesterId) {
    String query = getDataForReadOnly;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId, pCourseId, pTeacherId},
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
  public Long addQuestions(MutableApplicationTES pMutableList) {
    String query = addQuestion;
    String getMax = "SELECT max(QUESTION_ID) from APPLICATION_TES_QUESTIONS";
    Integer questionId = mJdbcTemplate.queryForObject(getMax, Integer.class);
    questionId = questionId + 1;

    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(query, id, questionId, pMutableList.getQuestionDetails(), pMutableList.getSemester(),
        pMutableList.getObservationType());
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
    mJdbcTemplate.batchUpdate(setQuestion,  parameters);
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

  @Override
  public int delete(List<MutableApplicationTES> pMutableList) {
    String query = DELETE_ALL + " WHERE QUESTION_ID=? AND SEMESTER_ID=?";
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

  // return mJdbcTemplate.update(query, pMutableList.getId());

  private List<Object[]> getInsertParamList(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getQuestionId(), app.getPoint(), app.getComment(),
          app.getTeacherId(), app.getObservationType(), app.getReviewEligibleCourseId(), app.getStudentId(),
          app.getSemester()});
    }

    return params;
  }

  private List<Object[]> getInsertParamListForHeadTes(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getReviewEligibleCourseId(), app.getTeacherId(),
          app.getSemester(), app.getSection(), app.getDeptId(), app.getStatus()});
    }

    return params;
  }

  private List<Object[]> getInsertParamListForSetQuestions(List<MutableApplicationTES> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationTES app : pMutableApplicationTES) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getQuestionId(), app.getSemester(), app.getStatus()});
    }

    return params;
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    String query = getAllQuestions;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ApplicationTESRowMapperForGetAllQuestions());
  }

  @Override
  public List<MutableApplicationTES> getMigrationQuestions(Integer pSemesterId) {
    String query = getAllQuestions;
    return mJdbcTemplate
        .query(query, new Object[] {pSemesterId}, new ApplicationTESRowMapperForGetMigrationQuestions());
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
  class ApplicationTESRowMapperForGetMigrationQuestions implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
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
  class ApplicationTESRowMapperForgetAssignedReviewableCoursesList implements RowMapper<ApplicationTES> {
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

  class ApplicationTESRowMapperForSemesterParameter implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setSemesterStartDate(pResultSet.getString("START_DATE"));
      application.setSemesterEndDate(pResultSet.getString("END_DATE"));
      return application;
    }
  }
  class ApplicationTESRowMapperForFacultyListReport implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      return application;
    }
  }
  class ApplicationTESRowMapperForQuestionWiseReport implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      return application;
    }
  }
  class ApplicationTESRowMapperForQuestions implements RowMapper<MutableApplicationTES> {
    @Override
    public MutableApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getInt("QUESTION_ID"));
      application.setQuestionDetails(pResultSet.getString("QUESTION_DETAILS"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setObservationType(pResultSet.getInt("OBSERVATION_TYPE"));
      return application;
    }
  }
  class ApplicationTESRowMapperForAllSection implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setSection(pResultSet.getString("SECTION"));
      return application;
    }
  }
  class ApplicationTESRowMapperForSectionList implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      return application;
    }
  }
  class ApplicationTESRowMapperForQuestionsMapping implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setQuestionId(pResultSet.getInt("QUESTION_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setStatus(pResultSet.getInt("STATUS"));
      return application;
    }
  }
  class ApplicationTESRowMapperForDeptList implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setDeptId(pResultSet.getString("DEPT_ID"));
      return application;
    }
  }
  class ApplicationTESRowMapperForReportParameter implements RowMapper<ApplicationTES> {
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

  class ApplicationTESRowMapperForGetDetailedResult implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setQuestionId(pResultSet.getInt("QUESTION_ID"));
      application.setPoint(pResultSet.getInt("POINT"));
      application.setObservationType(pResultSet.getInt("OBSERVATION_TYPE"));
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
