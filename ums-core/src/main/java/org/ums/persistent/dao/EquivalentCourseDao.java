package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.EquivalentCourseDaoDecorator;
import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.mutable.MutableEquivalentCourse;
import org.ums.persistent.model.PersistentEquivalentCourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EquivalentCourseDao extends EquivalentCourseDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, OLD_COURSE_ID, NEW_COURSE_ID, LAST_MODIFIED FROM EQUIVALENT_COURSE ";
  String INSERT_ALL =
      "INSERT INTO EQUIVALENT_COURSE(OLD_COURSE_ID, NEW_COURSE_ID, LAST_MODIFIED) VALUES (?, ? , "
          + getLastModifiedSql() + ") ";
  String UPDATE_ALL =
      "UPDATE EQUIVALENT_COURSE SET OLD_COURSE_ID = ?, NEW_COURSE_ID = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM EQUIVALENT_COURSE ";
  String EXISTS = "SELECT COUNT(ID) EXIST FROM EQUIVALENT_COURSE ";

  private JdbcTemplate mJdbcTemplate;

  public EquivalentCourseDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<EquivalentCourse> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new EquivalentCourseMapper());
  }

  @Override
  public EquivalentCourse get(Integer pId) {
    String query = SELECT_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new EquivalentCourseMapper());
  }

  @Override
  public int update(MutableEquivalentCourse pMutable) {
    String query = UPDATE_ALL + " WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getOldCourseId(), pMutable.getNewCourseId(),
        pMutable.getId());
  }

  @Override
  public int delete(MutableEquivalentCourse pMutable) {
    String query = DELETE_ALL + " WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableEquivalentCourse pMutable) {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getOldCourseId(), pMutable.getNewCourseId());
  }

  @Override
  public boolean exists(Integer pId) {
    String query = EXISTS + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, Boolean.class, pId);
  }

  class EquivalentCourseMapper implements RowMapper<EquivalentCourse> {
    @Override
    public EquivalentCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableEquivalentCourse equivalentCourse = new PersistentEquivalentCourse();
      equivalentCourse.setId(rs.getInt("ID"));
      equivalentCourse.setOldCourseId(rs.getString("OLD_COURSE_ID"));
      equivalentCourse.setNewCourseId(rs.getString("NEW_COURSE_ID"));
      equivalentCourse.setLastModified(rs.getString("LAST_MODIFIED"));
      return null;
    }
  }
}
