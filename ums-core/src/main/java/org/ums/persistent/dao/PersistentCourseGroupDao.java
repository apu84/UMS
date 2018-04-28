package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.persistent.model.PersistentCourseGroup;
import org.ums.decorator.CourseGroupDaoDecorator;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.mutable.MutableCourseGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentCourseGroupDao extends CourseGroupDaoDecorator {

  static String SELECT_ALL = "SELECT ID, NAME, LAST_MODIFIED FROM OPT_COURSE_GROUP ";
  static String UPDATE_ONE = "UPDATE OPT_COURSE_GROUP SET GROUP_NAME = ?, LAST_MODIFIED = " + getLastModifiedSql()
      + " ";
  static String DELETE_ONE = "DELETE FROM OPT_COURSE_GROUP ";
  static String INSERT_ONE = "INSERT INTO OPT_COURSE_GROUP(NAME, LAST_MODIFIED) " + "VALUES(?, " + getLastModifiedSql()
      + ")";

  private JdbcTemplate mJdbcTemplate;

  public PersistentCourseGroupDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public CourseGroup get(final Integer pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new CourseGroupRowMapper());
  }

  @Override
  public List<CourseGroup> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new CourseGroupRowMapper());
  }

  @Override
  public int update(final MutableCourseGroup pCourseGroup) {
    String query = UPDATE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pCourseGroup.getName(), pCourseGroup.getId());
  }

  public int delete(final MutableCourseGroup pCourseGroup) {
    String query = DELETE_ONE + "WHERE GROUP_ID = ?";
    return mJdbcTemplate.update(query, pCourseGroup.getId());
  }

  @Override
  public Integer create(final MutableCourseGroup pCourseGroup) {
    mJdbcTemplate.update(INSERT_ONE, pCourseGroup.getName());
    return pCourseGroup.getId();
  }

  class CourseGroupRowMapper implements RowMapper<CourseGroup> {
    @Override
    public CourseGroup mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentCourseGroup courseGroup = new PersistentCourseGroup();
      courseGroup.setId(resultSet.getInt("ID"));
      courseGroup.setName(resultSet.getString("NAME"));
      courseGroup.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<CourseGroup> atomicReference = new AtomicReference<>(courseGroup);
      return atomicReference.get();
    }
  }
}
