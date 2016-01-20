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

  static String SELECT_BY_SEMESTER_PROGRAM =
      "SELECT t3.*,\n" +
          "       t4.teacher_id,\n" +
          "       t4.section,\n" +
          "       t4.last_modified,\n" +
          "       t4.id\n" +
          "  FROM    (  SELECT t1.SEMESTER_ID,\n" +
          "                    T2.COURSE_ID\n" +
          "               FROM semester_syllabus_map t1, mst_course t2\n" +
          "              WHERE     t1.program_id = ?\n" +
          "                    AND t1.semester_id = ?\n" +
          "                    AND t1.syllabus_id = t2.syllabus_id\n" +
          "                    AND t1.year = t2.year\n" +
          "                    AND T1.SEMESTER = t2.semester\n" +
          "%s" +
          "           ORDER BY t2.syllabus_id,\n" +
          "                    t2.syllabus_id,\n" +
          "                    t2.year,\n" +
          "                    t2.semester) t3\n" +
          "       LEFT JOIN\n" +
          "          course_teacher t4\n" +
          "       ON t3.course_id = t4.course_id";

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
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId) {
    String query = String.format(SELECT_BY_SEMESTER_PROGRAM, "");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId}, new CourseTeacherRowMapper());
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    String query = String.format(SELECT_BY_SEMESTER_PROGRAM, " AND t2.year = ? AND T2.SEMESTER = ? ");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId, pYear, pSemester}, new CourseTeacherRowMapper());
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
