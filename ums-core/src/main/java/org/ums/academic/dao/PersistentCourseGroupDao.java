package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentCourseGroup;
import org.ums.domain.model.CourseGroup;
import org.ums.domain.model.MutableCourseGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentCourseGroupDao extends CourseGroupDaoDecorator {

  static String SELECT_ALL = "SELECT SYLLABUS_ID, GROUP_ID, GROUP_NAME, LAST_MODIFIED FROM OPT_COURSE_GROUP ";
  static String UPDATE_ONE = "UPDATE OPT_COURSE_GROUP SET SYLLABUS_ID = ?, GROUP_NAME = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM OPT_COURSE_GROUP ";
  static String INSERT_ONE = "INSERT INTO OPT_COURSE_GROUP(SYLLABUS_ID, GROUP_ID, GROUP_NAME, LAST_MODIFIED) " +
      "VALUES(?, ?, ?, " + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;

  public PersistentCourseGroupDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public CourseGroup get(final Integer pId) throws Exception {
    String query = SELECT_ALL + "WHERE GROUP_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new CourseGroupRowMapper());
  }

  @Override
  public CourseGroup getBySyllabus(final Integer pId, final String pSyllabusId) throws Exception {
    String query = SELECT_ALL + "WHERE GROUP_ID = ? AND SYLLABUS_ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId, pSyllabusId}, new CourseGroupRowMapper());
  }

  @Override
  public List<CourseGroup> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new CourseGroupRowMapper());
  }

  @Override
  public void update(final MutableCourseGroup pCourseGroup) throws Exception {
    String query = UPDATE_ONE + "WHERE GORUP_ID = ?";
    mJdbcTemplate.update(query,
        pCourseGroup.getSyllabus().getId(),
        pCourseGroup.getName(),
        pCourseGroup.getId());
  }

  public void delete(final MutableCourseGroup pCourseGroup) throws Exception {
    String query = DELETE_ONE + "WHERE GROUP_ID = ?";
    mJdbcTemplate.update(query, pCourseGroup.getId());
  }

  @Override
  public void create(final MutableCourseGroup pCourseGroup) throws Exception {
    mJdbcTemplate.update(INSERT_ONE,
        pCourseGroup.getSyllabus().getId(),
        pCourseGroup.getId(),
        pCourseGroup.getName());
  }

  class CourseGroupRowMapper implements RowMapper<CourseGroup> {
    @Override
    public CourseGroup mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentCourseGroup courseGroup = new PersistentCourseGroup();
      courseGroup.setId(resultSet.getInt("GROUP_ID"));
      courseGroup.setName(resultSet.getString("GROUP_NAME"));
      courseGroup.setSyllabusId(resultSet.getString("SYLLABUS_ID"));
      courseGroup.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<CourseGroup> atomicReference = new AtomicReference<>(courseGroup);
      return atomicReference.get();
    }
  }
}
