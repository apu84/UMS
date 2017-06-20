package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.HolidaysDaoDecorator;
import org.ums.domain.model.immutable.common.Holidays;
import org.ums.domain.model.mutable.common.MutableHolidays;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentHolidays;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class PersistentHolidaysDao extends HolidaysDaoDecorator {

  String SELECT_ALL = "select * from mst_holidays";
  String INSERT_ONE =
      " INSERT INTO MST_HOLIDAYS (ID, HOLIDAY_TYPE_ID, YEAR, FROM_DATE, TO_DATE,LAST_MODIFIED VALUES(?,?,?,?,?,"
          + getLastModifiedSql() + ")";
  String UPDATE_ONE = "UPDATE MST_HOLIDAYS SET HOLIDAY_TYPE_ID=?, YEAR=?, FROM_DATE=?, TO_DATE=?, LAST_MODIFIED="
      + getLastModifiedSql() + " WHERE ID=?";

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

  @Override
  public int update(List<MutableHolidays> pMutableList) {
    mJdbcTemplate.batchUpdate(UPDATE_ONE, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Holidays item = pMutableList.get(i);
        Long id = mIdGenerator.getNumericId();
        ps.setLong(1, item.getHolidayType().getId());
        ps.setInt(2, item.getYear());
        java.sql.Date date = new java.sql.Date(item.getFromDate().getTime());
        ps.setDate(3, date);
        date = new java.sql.Date(item.getToDate().getTime());
        ps.setDate(4, date);
        ps.setLong(5, id);

      }

      @Override
      public int getBatchSize() {
        return pMutableList.size();
      }
    });
    return pMutableList.size();
  }

  @Override
  public List<Long> create(List<MutableHolidays> pMutableList) {
    mJdbcTemplate.batchUpdate(INSERT_ONE, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Holidays item = pMutableList.get(i);
        Long id = mIdGenerator.getNumericId();
        ps.setLong(1, id);
        ps.setLong(2, item.getHolidayType().getId());
        ps.setInt(3, item.getYear());
        java.sql.Date date = new java.sql.Date(item.getFromDate().getTime());
        ps.setDate(4, date);
        date = new java.sql.Date(item.getToDate().getTime());
        ps.setDate(5, date);
      }

      @Override
      public int getBatchSize() {
        return pMutableList.size();
      }
    });
    return null;
  }

  class HolidaysRowMapper implements RowMapper<Holidays> {
    @Override
    public Holidays mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentHolidays persistentHolidays = new PersistentHolidays();
      persistentHolidays.setId(rs.getLong("id"));
      persistentHolidays.setHolidayTypeId(rs.getLong("holiday_type_id"));
      persistentHolidays.setYear(rs.getInt("year"));
      persistentHolidays.setFromDate(rs.getDate("from_date"));
      persistentHolidays.setToDate(rs.getDate("to_date"));
      persistentHolidays.setLastModified(rs.getString("last_modified"));
      return persistentHolidays;
    }
  }
}
