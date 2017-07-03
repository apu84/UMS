package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.ThanaDaoDecorator;
import org.ums.domain.model.immutable.common.Thana;
import org.ums.persistent.model.common.PersistentThana;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentThanaDao extends ThanaDaoDecorator {

  static String SELECT_ALL = "SELECT THANA_ID, DISTRICT_ID, THANA_NAME FROM MST_THANA ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentThanaDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Thana get(final Integer pId) {
    String query = SELECT_ALL + " WHERE THANA_ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentThanaDao.ThanaRowMapper());
  }

  @Override
  public List<Thana> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentThanaDao.ThanaRowMapper());
  }

  class ThanaRowMapper implements RowMapper<Thana> {
    @Override
    public Thana mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentThana thana = new PersistentThana();
      thana.setId(resultSet.getInt("thana_id"));
      thana.setDistrictId(resultSet.getInt("district_id"));
      thana.setThanaName(resultSet.getString("thana_name"));
      return thana;
    }
  }
}
