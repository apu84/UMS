package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.manager.CourseTeacherManager;
import org.ums.persistent.model.PersistentCourseTeacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentCourseTeacherDao extends
    AbstractAssignedTeacherDao<CourseTeacher, MutableCourseTeacher, Integer> implements
    CourseTeacherManager {

  String SELECT_ALL =
      "SELECT SEMESTER_ID, TEACHER_ID, COURSE_ID, SECTION, LAST_MODIFIED, ID FROM COURSE_TEACHER ";
  String UPDATE_ALL =
      "UPDATE COURSE_TEACHER SET SEMESTER_ID = ?, TEACHER_ID = ?, COURSE_ID = ?, SECTION = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM COURSE_TEACHER ";
  String INSERT_ONE =
      "INSERT INTO COURSE_TEACHER(SEMESTER_ID, TEACHER_ID, COURSE_ID, SECTION, LAST_MODIFIED) VALUES"
          + "(?, ?, ?, ?," + getLastModifiedSql() + ")";

  private String SELECT_BY_SEMESTER_PROGRAM = "SELECT t3.*,\n" + "       t4.teacher_id,\n"
      + "       t4.section,\n" + "       t4.last_modified,\n" + "       t4.id\n"
      + "  FROM    (  SELECT DISTINCT t1.SEMESTER_ID,\n" + "                    T2.COURSE_ID\n"
      + "               FROM semester_syllabus_map t1, mst_course t2 ,COURSE_SYLLABUS_MAP t3\n"
      + "              WHERE     t1.program_id = ?\n"
      + "                    AND t1.semester_id = ?\n"
      + "                    AND t1.syllabus_id = t3.syllabus_id\n"
      + "                    AND t1.year = t2.year\n"
      + "                    AND(T1.SEMESTER = t2.SEMESTER or t2.SEMESTER IS NULL)\n"
      + "                    AND T2.COURSE_ID=T3.COURSE_ID\n" + "%s" + "%s"
      + "                    AND t1.syllabus_id = t3.syllabus_id\n"
      + "                    AND t2.OFFER_BY = ? " + "           ORDER BY t3.syllabus_id,\n"
      + "                    t2.year,\n" + "                    t2.semester) t3\n"
      + "       LEFT JOIN\n" + "          course_teacher t4\n"
      + "       ON t3.course_id = t4.course_id  and t3.semester_id = t4.semester_id " + "%s"
      + "ORDER BY t3.COURSE_ID, t4.TEACHER_ID, t4.SECTION";

  public PersistentCourseTeacherDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  protected String getSelectSql() {
    return SELECT_ALL;
  }

  @Override
  protected String getSelectBySemesterProgram() {
    return SELECT_BY_SEMESTER_PROGRAM;
  }

  @Override
  protected RowMapper<CourseTeacher> getRowMapper() {
    return new CourseTeacherRowMapper();
  }

  @Override
  public int delete(MutableCourseTeacher pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableCourseTeacher pMutable) {
    return mJdbcTemplate.update(INSERT_ONE, pMutable.getSemester().getId(), pMutable.getTeacher()
        .getId(), pMutable.getCourse().getId(), pMutable.getSection());
  }

  @Override
  public int update(MutableCourseTeacher pMutable) {
    String query = UPDATE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getSemester().getId(), pMutable.getTeacher()
        .getId(), pMutable.getCourse().getId(), pMutable.getSection(), pMutable.getId());
  }

  @Override
  public List<CourseTeacher> getAssignedCourses(Integer pSemesterId, String pTeacherId) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID = ? AND TEACHER_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pTeacherId}, getRowMapper());
  }

  @Override
  public List<CourseTeacher> getAssignedSections(Integer pSemesterId, String pCourseId,
      String pTeacherId) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID = ? AND COURSE_ID = ? AND TEACHER_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pTeacherId},
        getRowMapper());
  }

  class CourseTeacherRowMapper implements RowMapper<CourseTeacher> {
    @Override
    public CourseTeacher mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableCourseTeacher courseTeacher = new PersistentCourseTeacher();
      courseTeacher.setId(rs.getInt("ID"));
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
