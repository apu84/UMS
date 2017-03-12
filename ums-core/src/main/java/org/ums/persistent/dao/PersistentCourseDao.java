package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.CourseDaoDecorator;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;
import org.ums.persistent.model.PersistentCourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentCourseDao extends CourseDaoDecorator {
  static String SELECT_ALL =
      "SELECT MST_COURSE.COURSE_ID, COURSE_NO, COURSE_TITLE, CRHR, SYLLABUS_ID, OPT_GROUP_ID, OFFER_BY,"
          + "VIEW_ORDER, YEAR, SEMESTER, COURSE_TYPE, COURSE_CATEGORY,PAIR_COURSE_ID, LAST_MODIFIED,null as TOTAL_APPLIED FROM MST_COURSE,COURSE_SYLLABUS_MAP ";
  static String UPDATE_ONE =
      "UPDATE MST_COURSE SET COURSE_NO = ?, COURSE_TITLE = ?, CRHR = ?, SYLLABUS_ID = ?, "
          + "OPT_GROUP_ID = ?, OFFER_BY = ?, VIEW_ORDER = ?, YEAR = ?, SEMESTER = ?, COURSE_TYPE = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM MST_COURSE ";
  static String INSERT_ONE =
      "INSERT INTO MST_COURSE(COURSE_ID, COURSE_NO, COURSE_TITLE, CRHR, SYLLABUS_ID, OPT_GROUP_ID, OFFER_BY,"
          + "VIEW_ORDER, YEAR, SEMESTER, COURSE_TYPE, COURSE_CATEGORY, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  static String ORDER_BY = " ORDER BY YEAR, SEMESTER, COURSE_CATEGORY, VIEW_ORDER";

  static String SELECT_ALL_BY_SEMESTER_PROGRAM =
      "Select COURSE_ID,COURSE_NO,COURSE_TITLE,YEAR,SEMESTER From MST_COURSE Where Syllabus_Id In "
          + "(Select Syllabus_Id from SEMESTER_SYLLABUS_MAP Where Program_Id=? and Semester_Id=?) ";

  static String SELECT_OFFERED_COURSES =
      "Select MST_COURSE.*,TOTAL_APPLIED From OPT_COURSE_OFFER,MST_COURSE "
          + "Where OPT_COURSE_OFFER.COURSE_ID=MST_COURSE.COURSE_ID "
          + "And Semester_Id=? and Program_Id=? and OPT_COURSE_OFFER.Year=? and OPT_COURSE_OFFER.Semester=? ";

  static String SELECT_CALL_FOR_APPLICATION_COURSES =
      "Select MST_COURSE.*,TOTAL_APPLIED From OPT_COURSE_OFFER,MST_COURSE "
          + "Where OPT_COURSE_OFFER.COURSE_ID=MST_COURSE.COURSE_ID "
          + "And Semester_Id=? and Program_Id=? and OPT_COURSE_OFFER.Year=? and OPT_COURSE_OFFER.Semester=? And CALL_FOR_APPLICATION='Y' ";

  static String SELECT_APPROVED_COURSES =
      "Select MST_COURSE.*,null as TOTAL_APPLIED From OPT_COURSE_OFFER,MST_COURSE "
          + "Where OPT_COURSE_OFFER.COURSE_ID=MST_COURSE.COURSE_ID "
          + "And Semester_Id=? and Program_Id=? and OPT_COURSE_OFFER.Year=? and OPT_COURSE_OFFER.Semester=? And APPROVED='Y'";

  static String SELECT_APPROVED_CALL_FOR_APPLICATION_COURSES =
      "Select  MST_COURSE.*,null as TOTAL_APPLIED From OPT_COURSE_OFFER, "
          + "MST_COURSE  Where Call_For_Application='Y' and Approved='Y' "
          + "And Semester_Id=? and Program_Id=? and OPT_COURSE_OFFER.Year=? and OPT_COURSE_OFFER.Semester=? "
          + "And MST_COURSE.course_id=OPT_COURSE_OFFER.course_id ";

  static String SELECT_OFFERED_TO_DEPT = "Select Dept_Id From ( "
      + "Select syllabus_id from COURSE_SYLLABUS_MAP " + "Where Course_id = ? and syllabus_id in  "
      + "( " + "Select distinct syllabus_id from SEMESTER_SYLLABUS_MAP Where Semester_Id=? " + ") "
      + ")tmp1, MST_SYLLABUS, MST_PROGRAM " + "Where Tmp1.Syllabus_Id = MST_SYLLABUS. Syllabus_Id "
      + "And MST_SYLLABUS.Program_Id = MST_PROGRAM.Program_Id ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentCourseDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Course get(final String pId) {
    String query =
        SELECT_ALL
            + "WHERE MST_COURSE.COURSE_ID = ? And MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new CourseRowMapper());
  }

  public List<Course> getAll() {
    String query =
        SELECT_ALL + " WHERE MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID " + ORDER_BY;
    return mJdbcTemplate.query(query, new CourseRowMapper());
  }

  public int update(final MutableCourse pCourse) {
    String query = UPDATE_ONE + "WHERE GORUP_ID = ?";
    return mJdbcTemplate.update(query, pCourse.getNo(), pCourse.getTitle(), pCourse.getCrHr(),
        pCourse.getSyllabus().getId(), pCourse.getCourseGroup(pCourse.getSyllabus().getId())
            .getId() > 0 ? pCourse.getCourseGroup(pCourse.getSyllabus().getId()).getId() : null,
        pCourse.getOfferedBy().getId(), pCourse.getViewOrder(), pCourse.getYear(), pCourse
            .getSemester(), pCourse.getCourseType().ordinal(), pCourse.getCourseCategory()
            .ordinal());
  }

  public int delete(final MutableCourse pCourse) {
    String query = DELETE_ONE + "WHERE COURSE_ID = ?";
    return mJdbcTemplate.update(query, pCourse.getId());
  }

  public String create(final MutableCourse pCourse) {
    mJdbcTemplate.update(INSERT_ONE, pCourse.getId(), pCourse.getNo(), pCourse.getTitle(), pCourse
        .getCrHr(), pCourse.getSyllabusId(), pCourse.getCourseGroupId() > 0 ? pCourse
        .getCourseGroup(pCourse.getSyllabus().getId()).getId() : null, pCourse.getOfferedBy()
        .getId(), pCourse.getViewOrder(), pCourse.getYear(), pCourse.getSemester(), pCourse
        .getCourseType().ordinal(), pCourse.getCourseCategory().ordinal());
    return pCourse.getId();
  }

  @Override
  public List<Course> getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pOrder) {

    // int totalRecord= getAll().size();
    int startIndex = pItemPerPage * pPage;
    int endIndex = startIndex + pItemPerPage;
    String query =
        "Select tmp2.*,ind  From (Select ROWNUM ind, tmp1.* From (" + SELECT_ALL
            + " Where MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID " + pOrder
            + ")tmp1 ) tmp2" + " WHERE ind >=? and ind<=?  ";
    return mJdbcTemplate.query(query, new Object[] {startIndex, endIndex}, new CourseRowMapper());
  }

  @Override
  public List<Course> getBySyllabus(String pSyllabusId) {
    String query =
        SELECT_ALL
            + "WHERE SYLLABUS_ID = ?  And COURSE_SYLLABUS_MAP.Course_Id=MST_COURSE.Course_Id "
            + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {pSyllabusId}, new CourseRowMapper());
  }

  @Override
  public List<Course> getBySemesterProgram(String pSemesterId, String pProgramId) {
    String query =
        SELECT_ALL
            + "Where MST_COURSE.Course_id=COURSE_SYLLABUS_MAP.Course_Id And Syllabus_Id In (Select Syllabus_Id from SEMESTER_SYLLABUS_MAP Where Semester_Id=? And Program_Id=? ) "
            + ORDER_BY;
    return mJdbcTemplate
        .query(query, new Object[] {pSemesterId, pProgramId}, new CourseRowMapper());
  }

  public List<Course> getByYearSemester(String pSemesterId, String pProgramId, int year,
      int semester) {
    String query =
        SELECT_ALL
            + "Where YEAR = ? and SEMESTER =? And MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID and Syllabus_Id "
            + " In (Select Syllabus_Id from SEMESTER_SYLLABUS_MAP Where Semester_Id=? And Program_Id=? AND YEAR=? AND SEMESTER=?) "
            + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {year, semester, pSemesterId, pProgramId, year,
        semester}, new CourseRowMapper());
  }

  @Override
  public List<Course> getOptionalCourseList(String pSyllabusId, Integer pYear, Integer pSemester) {
    String query =
        SELECT_ALL + "Where Syllabus_Id=? And Course_Category="
            + CourseCategory.OPTIONAL.getValue() + " And Year=? and Semester=? "
            + " Order By OPT_GROUP_ID,Course_No ";
    return mJdbcTemplate.query(query, new Object[] {pSyllabusId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getOfferedCourseList(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    String query = SELECT_OFFERED_COURSES;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getCallForApplicationCourseList(Integer pSemesterId, Integer pProgramId,
      Integer pYear, Integer pSemester) {
    String query = SELECT_CALL_FOR_APPLICATION_COURSES;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getApprovedCourseList(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    String query = SELECT_APPROVED_COURSES;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getApprovedCallForApplicationCourseList(Integer pSemesterId,
      Integer pProgramId, Integer pYear, Integer pSemester) {
    String query = SELECT_APPROVED_CALL_FOR_APPLICATION_COURSES;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getMandatoryCourses(String pSyllabusId, final Integer pYear,
      final Integer pSemester) {
    String query =
        SELECT_ALL + "Where Syllabus_Id=? And Course_Category="
            + CourseCategory.MANDATORY.getValue()
            + " AND YEAR = ? AND SEMESTER = ? Order By Course_No ";
    return mJdbcTemplate.query(query, new Object[] {pSyllabusId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getMandatoryTheoryCourses(String pSyllabusId, final Integer pYear,
      final Integer pSemester) {
    String query =
        SELECT_ALL + "Where Syllabus_Id=? And Course_Type=" + CourseType.THEORY.getId()
            + " AND YEAR = ? AND SEMESTER = ? Order By Course_No ";
    return mJdbcTemplate.query(query, new Object[] {pSyllabusId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getMandatorySesssionalCourses(String pSyllabusId, final Integer pYear,
      final Integer pSemester) {
    String query =
        SELECT_ALL + "Where Syllabus_Id=? And" + " (COURSE_TYPE = "
            + CourseType.THESIS_PROJECT.getId() + " OR Course_Type=" + CourseType.SESSIONAL.getId()
            + ") AND YEAR = ? AND SEMESTER = ? Order By Course_No ";
    return mJdbcTemplate.query(query, new Object[] {pSyllabusId, pYear, pSemester},
        new CourseRowMapper());
  }

  @Override
  public Course getByCourseNo(String pCourseName, String pSyllabusId) {
    String query = SELECT_ALL + " WHERE COURSE_NO = ? AND SYLLABUS_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pCourseName, pSyllabusId},
        new CourseRowMapper());
  }

  @Override
  public List<Course> getByTeacher(String pTeacherId) {

    String query =
        "SELECT "
            + "  MST_COURSE.COURSE_ID, "
            + "  COURSE_NO, "
            + "  COURSE_TITLE, "
            + "  CRHR, "
            + "  SYLLABUS_ID, "
            + "  OPT_GROUP_ID, "
            + "  OFFER_BY, "
            + "  VIEW_ORDER, "
            + "  YEAR, "
            + "  SEMESTER, "
            + "  COURSE_TYPE, "
            + "  COURSE_CATEGORY, "
            + "  PAIR_COURSE_ID, "
            + "  LAST_MODIFIED, "
            + "  NULL AS TOTAL_APPLIED "
            + "FROM MST_COURSE, COURSE_SYLLABUS_MAP "
            + "WHERE MST_COURSE.COURSE_ID IN (SELECT DISTINCT (c.COURSE_ID) "
            + "                               FROM COURSE_TEACHER C, MST_SEMESTER S "
            + "                               WHERE s.STATUS = 1 AND s.SEMESTER_ID = c.SEMESTER_ID AND TEACHER_ID = ?) "
            + "and MST_COURSE.COURSE_ID=COURSE_SYLLABUS_MAP.COURSE_ID";
    return mJdbcTemplate.query(query, new Object[] {pTeacherId}, new CourseRowMapper());
  }

  @Override
  public String getOfferedToDept(final Integer pSemesterId, final String pCourseId) {
    return mJdbcTemplate.queryForObject(SELECT_OFFERED_TO_DEPT, new Object[] {pCourseId,
        pSemesterId}, String.class);
  }

  class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentCourse course = new PersistentCourse();
      course.setId(resultSet.getString("COURSE_ID"));
      course.setNo(resultSet.getString("COURSE_NO"));
      course.setTitle(resultSet.getString("COURSE_TITLE"));
      course.setCrHr(resultSet.getFloat("CRHR"));
      course.setSyllabusId(resultSet.getString("SYLLABUS_ID"));
      course.setCourseGroupId(resultSet.getInt("OPT_GROUP_ID"));
      if(resultSet.getObject("OFFER_BY") != null) {
        course.setOfferedDepartmentId(resultSet.getString("OFFER_BY"));
      }
      course.setViewOrder(resultSet.getInt("VIEW_ORDER"));
      course.setYear(resultSet.getInt("YEAR"));
      course.setSemester(resultSet.getInt("SEMESTER"));
      course.setCourseType(CourseType.get(resultSet.getInt("COURSE_TYPE")));
      if(resultSet.getObject("COURSE_CATEGORY") != null) {
        course.setCourseCategory(CourseCategory.get(resultSet.getInt("COURSE_CATEGORY")));
      }
      course.setPairCourseId(resultSet.getString("PAIR_COURSE_ID"));
      course.setLastModified(resultSet.getString("LAST_MODIFIED"));

      if(resultSet.getObject("TOTAL_APPLIED") != null) {
        course.setTotalApplied(resultSet.getInt("TOTAL_APPLIED"));
      }
      course.setPairCourseId(resultSet.getString("PAIR_COURSE_ID"));
      AtomicReference<Course> atomicReference = new AtomicReference<>(course);
      return atomicReference.get();
    }
  }

}
