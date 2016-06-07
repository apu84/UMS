package org.ums.statistics;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class UMSJdbcTemplate extends JdbcTemplate {
  @Autowired
  QueryLogger mQueryLogger;

  @Override
  public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
    // Do some logging
    long currentTime = System.currentTimeMillis();
    List<T> returned = super.query(sql, args, rowMapper);
    long afterTime = System.currentTimeMillis();
    mQueryLogger.log(sql, args, SecurityUtils.getSubject().toString(), (afterTime - currentTime));
    return returned;
  }
}
