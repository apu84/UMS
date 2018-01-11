package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.MonthDaoDecorator;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.mutable.accounts.MutableMonth;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentMonth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jan-18.
 */
public class PersistentMonthDao extends MonthDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentMonthDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Month> getAll() {
    String query = "select * from mst_month";
    return mJdbcTemplate.query(query, new PersistentMonthRowMapper());
  }

  @Override
  public Month get(Long pId) {
    String query = "select * from mst_month where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentMonthRowMapper());
  }

  @Override
  public Month validate(Month pReadonly) {
    return super.validate(pReadonly);
  }

  @Override
  public int update(MutableMonth pMutable) {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableMonth> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableMonth pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableMonth> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableMonth pMutable) {
    return super.create(pMutable);
  }

  @Override
  public List<Long> create(List<MutableMonth> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class PersistentMonthRowMapper implements RowMapper<Month> {
    @Override
    public Month mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableMonth month = new PersistentMonth();
      month.setId(rs.getLong("id"));
      month.setName(rs.getString("name"));
      month.setShortName(rs.getString("short_name"));
      return month;
    }
  }

}
