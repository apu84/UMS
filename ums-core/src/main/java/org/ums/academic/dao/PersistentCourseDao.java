package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentCourse;
import org.ums.domain.model.regular.Course;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;
import org.ums.manager.ContentManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentCourseDao extends ContentDaoDecorator<Course, MutableCourse, String, ContentManager<Course, MutableCourse, String>> {
  static String SELECT_ALL = "SELECT COURSE_ID, COURSE_NO, COURSE_TITLE, CRHR, SYLLABUS_ID, OPT_GROUP_ID, OFFER_BY," +
      "VIEW_ORDER, YEAR, SEMESTER, COURSE_TYPE, COURSE_CATEGORY, LAST_MODIFIED FROM MST_COURSE ";
  static String UPDATE_ONE = "UPDATE MST_COURSE SET COURSE_NO = ?, COURSE_TITLE = ?, CRHR = ?, SYLLABUS_ID = ?, " +
      "OPT_GROUP_ID = ?, OFFER_BY = ?, VIEW_ORDER = ?, YEAR = ?, SEMESTER = ?, COURSE_TYPE = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM MST_COURSE ";
  static String INSERT_ONE = "INSERT INTO MST_COURSE(COURSE_ID, COURSE_NO, COURSE_TITLE, CRHR, SYLLABUS_ID, OPT_GROUP_ID, OFFER_BY," +
      "VIEW_ORDER, YEAR, SEMESTER, COURSE_TYPE, COURSE_CATEGORY, LAST_MODIFIED) " +
      "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  static String ORDER_BY = " ORDER BY VIEW_ORDER";

  private JdbcTemplate mJdbcTemplate;

  public PersistentCourseDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Course get(final String pId) throws Exception {
    String query = SELECT_ALL + "WHERE COURSE_ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new CourseRowMapper());
  }

  public List<Course> getAll() throws Exception {
    String query = SELECT_ALL + ORDER_BY;
    return mJdbcTemplate.query(query, new CourseRowMapper());
  }

  public void update(final MutableCourse pCourse) throws Exception {
    String query = UPDATE_ONE + "WHERE GORUP_ID = ?";
    mJdbcTemplate.update(query,
        pCourse.getNo(),
        pCourse.getTitle(),
        pCourse.getCrHr(),
        pCourse.getSyllabus().getId(),
        pCourse.getCourseGroup(pCourse.getSyllabus().getId()).getId(),
        pCourse.getOfferedBy().getId(),
        pCourse.getViewOrder(),
        pCourse.getYear(),
        pCourse.getSemester(),
        pCourse.getCourseType().ordinal(),
        pCourse.getCourseCategory().ordinal());
  }

  public void delete(final MutableCourse pCourse) throws Exception {
    String query = DELETE_ONE + "WHERE COURSE_ID = ?";
    mJdbcTemplate.update(query, pCourse.getId());
  }

  public void create(final MutableCourse pCourse) throws Exception {
    mJdbcTemplate.update(INSERT_ONE,
        pCourse.getId(),
        pCourse.getNo(),
        pCourse.getTitle(),
        pCourse.getCrHr(),
        pCourse.getSyllabusId(),
        pCourse.getCourseGroupId(),
        pCourse.getOfferedBy().getId(),
        pCourse.getViewOrder(),
        pCourse.getYear(),
        pCourse.getSemester(),
        pCourse.getCourseType().ordinal(),
        pCourse.getCourseCategory().ordinal());
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
      if (resultSet.getObject("OFFER_BY") != null) {
        course.setOfferedDepartmentId(resultSet.getInt("OFFER_BY"));
      }
      course.setViewOrder(resultSet.getInt("VIEW_ORDER"));
      course.setYear(resultSet.getInt("YEAR"));
      course.setSemester(resultSet.getInt("SEMESTER"));
      course.setCourseType(CourseType.get(resultSet.getInt("COURSE_TYPE")));
      if (resultSet.getObject("COURSE_CATEGORY") != null) {
        course.setCourseCategory(CourseCategory.get(resultSet.getInt("COURSE_CATEGORY")));
      }
      course.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Course> atomicReference = new AtomicReference<>(course);
      return atomicReference.get();
    }
  }
}
