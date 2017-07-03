package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.ReligionDaoDecorator;
import org.ums.domain.model.immutable.common.Religion;
import org.ums.persistent.model.common.PersistentReligion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentReligionDao extends ReligionDaoDecorator {

  static String SELECT_ALL = "SELECT ID, NAME FROM MST_RELIGION";

  private JdbcTemplate mJdbcTemplate;

  public PersistentReligionDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Religion get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentReligionDao.ReligionRowMapper());
  }

  @Override
  public List<Religion> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentReligionDao.ReligionRowMapper());
  }

  class ReligionRowMapper implements RowMapper<Religion> {
    @Override
    public Religion mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentReligion persistentReligion = new PersistentReligion();
      persistentReligion.setId(resultSet.getInt("id"));
      persistentReligion.setReligion(resultSet.getString("name"));
      return persistentReligion;
    }
  }
}
