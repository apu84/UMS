package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.MaritalStatusDaoDecorator;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.persistent.model.common.PersistentMaritalStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentMaritalStatusDao extends MaritalStatusDaoDecorator {

  static String SELECT_ALL = "SELECT ID, NAME FROM MST_MARITAL_STATUS";

  private JdbcTemplate mJdbcTemplate;

  public PersistentMaritalStatusDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public MaritalStatus get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentMaritalStatusDao.MaritalStatusRowMapper());
  }

  @Override
  public List<MaritalStatus> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentMaritalStatusDao.MaritalStatusRowMapper());
  }

  class MaritalStatusRowMapper implements RowMapper<MaritalStatus> {

    @Override
    public MaritalStatus mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentMaritalStatus persistentMaritalStatus = new PersistentMaritalStatus();
      persistentMaritalStatus.setId(resultSet.getInt("id"));
      persistentMaritalStatus.setMaritalStatus(resultSet.getString("name"));
      return persistentMaritalStatus;
    }
  }
}
