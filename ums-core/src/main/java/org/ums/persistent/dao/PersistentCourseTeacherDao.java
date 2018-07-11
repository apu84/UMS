package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.generator.IdGenerator;
import org.ums.manager.CourseTeacherManager;
import org.ums.persistent.model.PersistentApplicationTES;
import org.ums.persistent.model.PersistentCourseTeacher;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class PersistentCourseTeacherDao extends AbstractAssignedTeacherDao<CourseTeacher, MutableCourseTeacher, Long>
    implements CourseTeacherManager {

  String SELECT_ALL = "SELECT SEMESTER_ID, TEACHER_ID, COURSE_ID, SECTION, LAST_MODIFIED, ID FROM COURSE_TEACHER ";
  String UPDATE_ALL =
      "UPDATE COURSE_TEACHER SET SEMESTER_ID = ?, TEACHER_ID = ?, COURSE_ID = ?, SECTION = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM COURSE_TEACHER ";
  String INSERT_ONE =
      "INSERT INTO COURSE_TEACHER(ID, SEMESTER_ID, TEACHER_ID, COURSE_ID, SECTION, LAST_MODIFIED) VALUES"
          + "(?, ?, ?, ?, ?," + getLastModifiedSql() + ")";

  private String SELECT_BY_SEMESTER_PROGRAM = "SELECT t3.*, " + "       t4.teacher_id, " + "       t4.section, "
      + "       t4.last_modified, " + "       t4.id " + "  FROM    (  SELECT DISTINCT t1.SEMESTER_ID, "
      + "                    T2.COURSE_ID "
      + "               FROM semester_syllabus_map t1, mst_course t2 ,COURSE_SYLLABUS_MAP t3 "
      + "              WHERE     t1.program_id = ? " + "                    AND t1.semester_id = ? "
      + "                    AND t1.syllabus_id = t3.syllabus_id " + "                    AND t1.year = t2.year "
      + "                    AND(T1.SEMESTER = t2.SEMESTER or t2.SEMESTER IS NULL) "
      + "                    AND T2.COURSE_ID=T3.COURSE_ID " + "%s" + "%s"
      + "                    AND t1.syllabus_id = t3.syllabus_id " + "                    AND t2.OFFER_BY = ? "
      + "           ORDER BY t3.syllabus_id, " + "                    t2.year, "
      + "                    t2.semester) t3 " + "       LEFT JOIN " + "          course_teacher t4 "
      + "       ON t3.course_id = t4.course_id  and t3.semester_id = t4.semester_id " + "%s"
      + "ORDER BY t3.COURSE_ID, t4.TEACHER_ID, t4.SECTION";
  String ALL_SECTIONS_FOR_A_COURSE =
      "select COURSE_ID,\"SECTION\" from COURSE_TEACHER WHERE COURSE_ID=? AND SEMESTER_ID=? AND TEACHER_ID=? ORDER BY \"SECTION\"";

  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

  public PersistentCourseTeacherDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
  }

  @Override
  public List<ApplicationTES> getAllSectionForSelectedCourse(String pCourseId, String pTeacherId, Integer pSemesterId) {
    String query = ALL_SECTIONS_FOR_A_COURSE;
    return mJdbcTemplate.query(query, new Object[] {pCourseId, pSemesterId, pTeacherId},
        new CourseTeacherRowMapperForAllSection());
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
  public Long create(MutableCourseTeacher pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getSemester().getId(), pMutable.getTeacher().getId(), pMutable
        .getCourse().getId(), pMutable.getSection());
    return id;
  }

  @Override
  public List<Long> create(List<MutableCourseTeacher> pMutableList) {
    String query = "INSERT INTO COURSE_TEACHER(ID, SEMESTER_ID, TEACHER_ID, COURSE_ID, SECTION, LAST_MODIFIED) VALUES" +
        "      (:id, :semesterId, :teacherId, :courseId, :section, :lastModified)";
    Map<String, Object>[] parameters = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, parameters);
    return pMutableList.stream()
        .map(p -> p.getId())
        .collect(Collectors.toList());
  }

  @Override
  public int update(List<MutableCourseTeacher> pMutableList) {
    String query =
        "UPDATE COURSE_TEACHER SET SEMESTER_ID = :semesterId, TEACHER_ID = :teacherId, COURSE_ID = :courseId, SECTION = :section, LAST_MODIFIED = :lastModified where id=:id";
    Map<String, Object>[] parameters = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameters).length;
  }

  @Override
  public List<CourseTeacher> getCourseTeacher(int pSemesterId, String pCourseId) {
    String query = SELECT_ALL + " where semester_id=? and course_id=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId}, new CourseTeacherRowMapper());
  }

  @Override
  public List<CourseTeacher> getCourseTeacher(int pSemesterId) {
    String query = SELECT_ALL + " where semester_id=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new CourseTeacherRowMapper());
  }

  @Override
  public int update(MutableCourseTeacher pMutable) {
    String query = UPDATE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getSemester().getId(), pMutable.getTeacher().getId(), pMutable
        .getCourse().getId(), pMutable.getSection(), pMutable.getId());
  }

  @Override
  public List<CourseTeacher> getAssignedCourses(Integer pSemesterId, String pTeacherId) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID = ? AND TEACHER_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pTeacherId}, getRowMapper());
  }

  @Override
  public CourseTeacher getAssignedCourse(Integer pSemesterId, String pTeacherId, String pCourseId, String pSectionId) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID = ? AND TEACHER_ID = ? and Course_Id=? and Section=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, pTeacherId, pCourseId, pSectionId},
        getRowMapper());
  }

  @Override
  public List<CourseTeacher> getAssignedSections(Integer pSemesterId, String pCourseId, String pTeacherId) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID = ? AND COURSE_ID = ? AND TEACHER_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pTeacherId}, getRowMapper());
  }

  @Override
  public List<CourseTeacher> getCourseTeacher(int pProgramId, int pSemesterId, String pSection, int pYear, int pSemester) {
    String query =
        "SELECT * "
            + "FROM COURSE_TEACHER "
            + "WHERE SEMESTER_ID = ? AND SECTION LIKE '"
            + pSection
            + "%' AND COURSE_ID IN ( "
            + "  SELECT COURSE_SYLLABUS_MAP.COURSE_ID "
            + "  FROM COURSE_SYLLABUS_MAP, SEMESTER_SYLLABUS_MAP "
            + "  WHERE SEMESTER_SYLLABUS_MAP.SEMESTER_ID = ? AND SEMESTER_SYLLABUS_MAP.PROGRAM_ID=? AND SEMESTER_SYLLABUS_MAP.YEAR=? AND SEMESTER_SYLLABUS_MAP.SEMESTER=? AND SEMESTER_SYLLABUS_MAP.SYLLABUS_ID = COURSE_SYLLABUS_MAP.SYLLABUS_ID "
            + ")";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pSemesterId, pProgramId, pYear, pSemester},
        getRowMapper());
  }

  @Override
  public List<CourseTeacher> getCourseTeacher(int pSemesterId, String pCourseId, String pSection) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID=? AND COURSE_ID=? AND SECTION LIKE '" + pSection + "%'";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId}, getRowMapper());
  }

  private Map<String, Object>[] getParameterObjects(List<MutableCourseTeacher> pMutableCourseTeachers) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableCourseTeachers.size()];
    for(int i = 0; i < pMutableCourseTeachers.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableCourseTeachers.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableCourseTeacher pMutableCourseTeacher) {
    Map parameter = new HashMap();
    parameter.put("id", pMutableCourseTeacher.getId());
    parameter.put("courseId", pMutableCourseTeacher.getCourse().getId());
    parameter.put("semesterId", pMutableCourseTeacher.getSemester().getId());
    parameter.put("section", pMutableCourseTeacher.getSection());
    parameter.put("teacherId", pMutableCourseTeacher.getTeacher().getId());
    parameter.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    return parameter;
  }

  class CourseTeacherRowMapper implements RowMapper<CourseTeacher> {
    @Override
    public CourseTeacher mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableCourseTeacher courseTeacher = new PersistentCourseTeacher();
      courseTeacher.setId(rs.getLong("ID"));
      courseTeacher.setCourseId(rs.getString("COURSE_ID"));
      courseTeacher.setSemesterId(rs.getInt("SEMESTER_ID"));
      courseTeacher.setTeacherId(rs.getString("TEACHER_ID"));
      courseTeacher.setSection(rs.getString("SECTION"));
      courseTeacher.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<CourseTeacher> atomicReference = new AtomicReference<>(courseTeacher);
      return atomicReference.get();
    }
  }
  class CourseTeacherRowMapperForAllSection implements RowMapper<ApplicationTES> {
    @Override
    public ApplicationTES mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTES application = new PersistentApplicationTES();
      application.setReviewEligibleCourses(pResultSet.getString("COURSE_ID"));
      application.setSection(pResultSet.getString("SECTION"));
      return application;
    }
  }
}
