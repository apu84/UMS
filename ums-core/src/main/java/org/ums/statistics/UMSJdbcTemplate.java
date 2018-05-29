package org.ums.statistics;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class UMSJdbcTemplate extends JdbcTemplate {
  @Autowired
  @Qualifier("loggerFactory")
  LoggerFactory mQueryLoggerFactory;

  @Override
  public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
    // Do some logging
    long currentTime = System.currentTimeMillis();
    List<T> returned = super.query(sql, args, rowMapper);
    long afterTime = System.currentTimeMillis();
    mQueryLoggerFactory.getQueryLogger().log(sql, args, getUserName(), (afterTime - currentTime));
    return returned;
  }

  @Override
  public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
    long currentTime = System.currentTimeMillis();
    List<T> returned = super.query(sql, rowMapper);
    long afterTime = System.currentTimeMillis();
    mQueryLoggerFactory.getQueryLogger().log(sql, getUserName(), (afterTime - currentTime));
    return returned;
  }

  @Override
  public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
    long currentTime = System.currentTimeMillis();
    T returned = super.queryForObject(sql, args, rowMapper);
    long afterTime = System.currentTimeMillis();
    mQueryLoggerFactory.getQueryLogger().log(sql, args, getUserName(), (afterTime - currentTime));
    return returned;
  }

  @Override
  public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
    long currentTime = System.currentTimeMillis();
    T returned = super.queryForObject(sql, rowMapper);
    long afterTime = System.currentTimeMillis();
    mQueryLoggerFactory.getQueryLogger().log(sql, getUserName(), (afterTime - currentTime));
    return returned;
  }

  @Override
  public int update(String sql, Object... args) throws DataAccessException {
    long currentTime = System.currentTimeMillis();
    int updated = super.update(sql, args);
    long afterTime = System.currentTimeMillis();
    mQueryLoggerFactory.getQueryLogger().log(sql, args, getUserName(), (afterTime - currentTime));
    return updated;
  }

  @Override
  public int update(String sql) throws DataAccessException {
    long currentTime = System.currentTimeMillis();
    int updated = super.update(sql);
    long afterTime = System.currentTimeMillis();
    mQueryLoggerFactory.getQueryLogger().log(sql, getUserName(), (afterTime - currentTime));
    return updated;
  }

  @Override
  public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws DataAccessException {
    long currentTime = System.currentTimeMillis();
    int[] updated = super.batchUpdate(sql, batchArgs);
    long afterTime = System.currentTimeMillis();
    mQueryLoggerFactory.getQueryLogger().log(sql, batchArgs, getUserName(), (afterTime - currentTime));
    return updated;
  }

  private String getUserName() {
    String userName="";
    if(SecurityUtils.getSubject() == null ) {
          /*org.apache.shiro.session.ExpiredSessionException: Session with id [e87d6b35-19c6-4636-b713-39af3ae09f55] has expired. Last access time: 5/29/18 10:46 PM.  Current time: 5/29/18 11:19 PM.
     Session timeout is set to 1800 seconds (30 minutes)
     -----------------------------------------------------------------------------------------------------------------------------------------
      In such a case if we try to access SecurityUtils.getSubject() .getPrincipal(), there happens a ExpiredSessionException Occurred.
      Example: If we try to reindex all library records ( which takes more than 30 minutes or micro service is up more than 30 minutes),
      At the end of the update INDEX_CONSUMER head, we get this exception
      */
      userName = "Unknown(But Shiro session timeout occurred)";
    }
    else if(SecurityUtils.getSubject().getPrincipal() == null)
        userName = "Login Request";
    else
        userName = SecurityUtils.getSubject().getPrincipal().toString();

    return userName;
  }
}
