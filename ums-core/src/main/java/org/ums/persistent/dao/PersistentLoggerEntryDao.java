package org.ums.persistent.dao;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.LoggerEntry;
import org.ums.domain.model.mutable.MutableLoggerEntry;
import org.ums.manager.LoggerEntryManager;
import org.ums.persistent.model.PersistentLoggerEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentLoggerEntryDao extends
    ContentDaoDecorator<LoggerEntry, MutableLoggerEntry, Integer, LoggerEntryManager> implements
    LoggerEntryManager {
  static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
  static final String DB_TIME_STAMP_FORMAT = "yyyy-MM-dd hh24:mi:ss";
  static String SELECT_ALL =
      "SELECT ID, SQL, USER_NAME, EXECUTION_TIME, EXECUTION_TIME_STAMP FROM DB_LOGGER ";
  static String UPDATE_ONE =
      "UPDATE DB_LOGGER SET SQL = ?, USER_NAME = ?, EXECUTION_TIME = ?, EXECUTION_TIME_STAMP = ? ";
  static String DELETE_ONE = "DELETE FROM DB_LOGGER ";
  static String INSERT_ONE =
      "INSERT INTO DB_LOGGER(SQL, USER_NAME, EXECUTION_TIME, EXECUTION_TIME_STAMP) "
          + "VALUES(?, ?, ?, TO_DATE(?, '" + DB_TIME_STAMP_FORMAT + "'))";

  private JdbcTemplate mJdbcTemplate;
  private DateFormat mDateFormat = new SimpleDateFormat(TIME_STAMP_FORMAT);

  public PersistentLoggerEntryDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(List<MutableLoggerEntry> pMutableList) {
    return mJdbcTemplate.batchUpdate(INSERT_ONE, getInsertParamList(pMutableList)).length;
  }

  @Override
  public int create(MutableLoggerEntry pMutable) {
    return mJdbcTemplate
        .update(INSERT_ONE, getInsertParamList(Lists.newArrayList(pMutable)).get(0));
  }

  @Override
  public int delete(MutableLoggerEntry pMutable) {
    String query = DELETE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(MutableLoggerEntry pMutable) {
    String query = UPDATE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getSql(), pMutable.getUserName(),
        pMutable.getExecutionTime(), mDateFormat.format(pMutable.getTimestamp()), pMutable.getId());
  }

  @Override
  public LoggerEntry get(Integer pId) {
    String query = SELECT_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new LoggerEntryRowMapper());
  }

  @Override
  public List<LoggerEntry> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new LoggerEntryRowMapper());
  }

  private List<Object[]> getInsertParamList(List<MutableLoggerEntry> pMutableLoggerEntries) {
    List<Object[]> params = new ArrayList<>();
    for(LoggerEntry entry : pMutableLoggerEntries) {
      params.add(new Object[] {entry.getSql(), entry.getUserName(), entry.getExecutionTime(),
          mDateFormat.format(entry.getTimestamp())});
    }

    return params;
  }

  class LoggerEntryRowMapper implements RowMapper<LoggerEntry> {
    @Override
    public LoggerEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableLoggerEntry loggerEntry = new PersistentLoggerEntry();
      loggerEntry.setId(rs.getInt("ID"));
      loggerEntry.setSql(rs.getString("SQL"));
      loggerEntry.setUserName(rs.getString("USER_NAME"));
      loggerEntry.setExecutionTime(rs.getLong("EXECUTION_TIME"));
      loggerEntry.setTimestamp(rs.getTimestamp("EXECUTION_TIME_STAMP"));
      AtomicReference<LoggerEntry> atomicReference = new AtomicReference<>(loggerEntry);
      return atomicReference.get();
    }
  }
}
