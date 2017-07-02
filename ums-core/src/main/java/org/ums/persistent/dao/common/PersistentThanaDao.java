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

  static String SELECT_ALL = "Select THANA_ID, DISTRICT_ID, THANA_NAME From MST_THANA ";

  private JdbcTemplate mJbdcTemplate;

  public PersistentThanaDao(final JdbcTemplate pJdbcTemplate) {
    mJbdcTemplate = pJdbcTemplate;
  }

  @Override
  public List<Thana> getAll() {
    String query = SELECT_ALL;
    return mJbdcTemplate.query(query, new PersistentThanaDao.ThanaRowMapper());
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
