package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.HolidaysDaoDecorator;
import org.ums.domain.model.immutable.common.Holidays;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentHolidays;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class PersistentHolidaysDao extends HolidaysDaoDecorator {

  String SELECT_ALL = "select * from mst_holidays";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentHolidaysDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Holidays> getHolidays(int pYear) {
    String query = SELECT_ALL + " where year=? order by holiday_type_id";
    return mJdbcTemplate.query(query, new Object[] {pYear}, new HolidaysRowMapper());
  }

  class HolidaysRowMapper implements RowMapper<Holidays> {
    @Override
    public Holidays mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentHolidays persistentHolidays = new PersistentHolidays();
      persistentHolidays.setId(rs.getLong("id"));
      persistentHolidays.setHolidayTypeId(rs.getLong("holiday_type_id"));
      persistentHolidays.setYear(rs.getInt("year"));
      persistentHolidays.setFromDate(rs.getString("from_date"));
      persistentHolidays.setToDate(rs.getString("to_date"));
      persistentHolidays.setLastModified(rs.getString("last_modified"));
      return persistentHolidays;
    }
  }
}
