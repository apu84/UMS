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

  static String SELECT_ALL = "SELECT DISTRICT_ID, DIVISION_ID, DIST_NAME FROM MST_DISTRICT ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDistrictDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public District get(final Integer pId) {
    String query = SELECT_ALL + " WHERE DISTRICT_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentDistrictDao.DistrictRowMapper());
  }

  @Override
  public List<District> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentDistrictDao.DistrictRowMapper());
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
