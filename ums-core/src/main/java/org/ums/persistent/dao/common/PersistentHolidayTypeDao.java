package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.HolidayTypeDaoDecorator;
import org.ums.domain.model.immutable.common.HolidayType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentHolidayType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class PersistentHolidayTypeDao extends HolidayTypeDaoDecorator {
  String SELECT_ALL = "select * from holiday_type";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentHolidayTypeDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<HolidayType> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new HolidayTypeRowMapper());
  }

  @Override
  public HolidayType get(Long pId) {
    String query = SELECT_ALL + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new HolidayTypeRowMapper());
  }

  class HolidayTypeRowMapper implements RowMapper<HolidayType> {
    @Override
    public HolidayType mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentHolidayType holidayType = new PersistentHolidayType();
      holidayType.setId(rs.getLong("id"));
      holidayType.setHolidayName(rs.getString("name"));
      holidayType.setMoonDependency(HolidayType.SubjectToMoon.get(rs.getInt("subject_to_moon")));
      holidayType.setLastModified(rs.getString("last_modified"));
      return holidayType;
    }
  }
}
