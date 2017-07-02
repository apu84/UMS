package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.common.DistrictDaoDecorator;
import org.ums.domain.model.immutable.common.District;
import org.ums.persistent.model.common.PersistentDistrict;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentDistrictDao extends DistrictDaoDecorator {

  static String SELECT_ALL = "Select DISTRICT_ID, DIVISION_ID, DIST_NAME From MST_DISTRICT ";

  private JdbcTemplate mJbdcTemplate;

  public PersistentDistrictDao(final JdbcTemplate pJdbcTemplate) {
    mJbdcTemplate = pJdbcTemplate;
  }

  @Override
  public List<District> getAll() {
    String query = SELECT_ALL;
    return mJbdcTemplate.query(query, new PersistentDistrictDao.DistrictRowMapper());
  }

  class DistrictRowMapper implements RowMapper<District> {
    @Override
    public District mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentDistrict district = new PersistentDistrict();
      district.setId(resultSet.getInt("district_id"));
      district.setDivisionId(resultSet.getInt("division_id"));
      district.setDistrictName(resultSet.getString("dist_name"));
      return district;
    }
  }
}
