package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.BloodGroupDaoDecorator;
import org.ums.domain.model.immutable.common.BloodGroup;
import org.ums.persistent.model.common.PersistentBloodGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentBloodGroupDao extends BloodGroupDaoDecorator {

  static String SELECT_ALL = "SELECT ID, NAME FROM MST_BLOOD_GROUP";

  private JdbcTemplate mJdbcTemplate;

  public PersistentBloodGroupDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public BloodGroup get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentBloodGroupDao.BloodGroupRowMapper());
  }

  @Override
  public List<BloodGroup> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentBloodGroupDao.BloodGroupRowMapper());
  }

  class BloodGroupRowMapper implements RowMapper<BloodGroup> {

    @Override
    public BloodGroup mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentBloodGroup persistentBloodGroup = new PersistentBloodGroup();
      persistentBloodGroup.setId(resultSet.getInt("id"));
      persistentBloodGroup.setBloodGroup(resultSet.getString("name"));
      return persistentBloodGroup;
    }
  }
}
