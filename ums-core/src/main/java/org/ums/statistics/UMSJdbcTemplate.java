package org.ums.statistics;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class UMSJdbcTemplate extends JdbcTemplate {
  @Override
  public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
    // Do some logging
    return super.query(sql, args, rowMapper);
  }
}
