package org.ums.academic.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentCourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.regular.CourseTeacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentCourseTeacherDao extends CourseTeacherDaoDecorator {

  static String SELECT_ALL = "SELECT SEMESTER_ID, TEACHER_ID, COURSE_ID, SECTION, LAST_MODIFIED, ID FROM COURSE_TEACHER ";
  static String UPDATE_ALL = "UPDATE SEMESTER_ID = ?, TEACHER_ID = ?, COURSE_ID = ?, SECTION = ?, " + getLastModifiedSql() + " ";
  static String DELETE_ALL = "DELETE FROM COURSE_TEACHER ";
  static String INSERT_ONE = "INSERT INTO COURSE_TEACHER(SEMESTER_ID, TEACHER_ID, COURSE_ID, SECTION, LAST_MODIFIED, ID) VALUES" +
      "(?, ?, ?, ?," + getLastModifiedSql() + ", SQN_COURSE_TEACHER_ID.nextVal)";

  static String SELECT_BY_SYLLABUS = "SELECT t1.* FROM (SELECT COURSE.COURSE_ID, COURSE.SYLLABUS_ID, COURSE.YEAR, COURSE.SEMESTER, COURSE_TEACHER.SEMESTER_ID, COURSE_TEACHER.TEACHER_ID, SECTION, LAST_MODIFIED, ID FROM COURSE " +
      "LEFT JOIN COURSE_TEACHER COURSE.COURSE_ID = COURSE_TEACHER.COURSE_ID) t1, SEMESTER_SYALLABUS_MAP WHERE t1.SYLLABUS_ID = SEMESTER_SYALLABUS_MAP.SYALLABUS_ID " +
      "AND t1.YEAR = SEMESTER_SYALLABUS_MAP.YEAR AND t1.SEMESTER = SEMESTER_SYALLABUS_MAP.SEMESTER AND t1.SEMESTER_ID = SEMESTER_SYALLABUS_MAP.SYLLABUS_ID ";

  static String ORDER_BY = " ORDER BY COURSE.SYLLABUS_ID, COURSE.YEAR, COURSE.SEMESTER, COURSE.COURSE_CATEGORY, COURSE.VIEW_ORDER, COURSE_TEACHER.TEACHER_ID, COURSE_TEACHER_SECTION";

  private JdbcTemplate mJdbcTemplate;

  public PersistentCourseTeacherDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(String pCourseId, String pSemesterId) {
    String query = SELECT_ALL + "WHERE COURSE_ID = ? AND SEMESTER_ID = ?";
    return mJdbcTemplate.query(query, new CourseTeacherRowMapper());
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(String pCourseId, String pSemesterId, Integer pYear, int pSemester) {
    String query = SELECT_ALL + "WHERE COURSE_ID = ? AND SEMESTER_ID = ? AND YEAR = ? AND SEMESTER = ?";
    return mJdbcTemplate.query(query, new CourseTeacherRowMapper());
  }

  @Override
  public CourseTeacher get(String pId) throws Exception {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new CourseTeacherRowMapper());
  }

  @Override
  public List<CourseTeacher> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new CourseTeacherRowMapper());
  }

  @Override
  public void delete(MutableCourseTeacher pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE ID = ?";
    mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public void create(MutableCourseTeacher pMutable) throws Exception {
    mJdbcTemplate.update(INSERT_ONE,
        pMutable.getSemester().getId(),
        pMutable.getTeacher().getId(),
        pMutable.getCourse().getId(),
        pMutable.getSection());
  }

  @Override
  public void update(MutableCourseTeacher pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE ID = ?";
    mJdbcTemplate.update(query,
        pMutable.getSemester().getId(),
        pMutable.getTeacher().getId(),
        pMutable.getCourse().getId(),
        pMutable.getSection(),
        pMutable.getId());
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pSemesterId, String pSyllabusId) {
    String query = SELECT_BY_SYLLABUS + "WHERE t1.SYLLABUS_ID = ? AND t1.SEMESTER_ID = ?" + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[]{pSyllabusId, pSemesterId}, new CourseTeacherRowMapper());
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pSemesterId, String pSyllabusId, Integer pYear, Integer pSemester) {
    String query = SELECT_BY_SYLLABUS + "WHERE t1.SYLLABUS_ID = ? AND t1.YEAR = ? AND t1.SEMESTER = ? AND t1.SEMESTER_ID = ?" + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[]{pSyllabusId, pYear, pSemester, pSemesterId}, new CourseTeacherRowMapper());
  }

  class CourseTeacherRowMapper implements RowMapper<CourseTeacher> {
    @Override
    public CourseTeacher mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableCourseTeacher courseTeacher = new PersistentCourseTeacher();
      courseTeacher.setId(rs.getString("ID"));
      courseTeacher.setCourseId(rs.getString("COURSE_ID"));
      courseTeacher.setSemesterId(rs.getInt("SEMESTER_ID"));
      courseTeacher.setTeacherId(rs.getString("TEACHER_ID"));
      courseTeacher.setSection(rs.getString("SECTION"));
      courseTeacher.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<CourseTeacher> atomicReference = new AtomicReference<>(courseTeacher);
      return atomicReference.get();
    }
  }
}
