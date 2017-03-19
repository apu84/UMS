package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.LoggerEntry;
import org.ums.domain.model.mutable.MutableLoggerEntry;
import org.ums.generator.IdGenerator;
import org.ums.manager.LoggerEntryManager;
import org.ums.persistent.model.PersistentLoggerEntry;

import com.google.common.collect.Lists;

public class PersistentLoggerEntryDao extends
    ContentDaoDecorator<LoggerEntry, MutableLoggerEntry, Long, LoggerEntryManager> implements LoggerEntryManager {
  static String SELECT_ALL = "SELECT ID, SQL, USER_NAME, EXECUTION_TIME, EXECUTION_TIME_STAMP FROM DB_LOGGER ";
  static String UPDATE_ONE =
      "UPDATE DB_LOGGER SET SQL = ?, USER_NAME = ?, EXECUTION_TIME = ?, EXECUTION_TIME_STAMP = ? ";
  static String DELETE_ONE = "DELETE FROM DB_LOGGER ";
  static String INSERT_ONE = "INSERT INTO DB_LOGGER(ID, SQL, USER_NAME, EXECUTION_TIME, EXECUTION_TIME_STAMP) "
      + "VALUES(?, ?, ?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentLoggerEntryDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Long> create(List<MutableLoggerEntry> pMutableList) {
    List<Object[]> params = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ONE, params);
    return params.stream().map(param -> (Long) param[0])
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public Long create(MutableLoggerEntry pMutable) {
    List<Object[]> params = getInsertParamList((Lists.newArrayList(pMutable)));
    mJdbcTemplate.update(INSERT_ONE, params.get(0));
    return (Long) params.get(0)[0];
  }

  @Override
  public int delete(MutableLoggerEntry pMutable) {
    String query = DELETE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(MutableLoggerEntry pMutable) {
    String query = UPDATE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getSql(), pMutable.getUserName(), pMutable.getExecutionTime(),
        pMutable.getTimestamp(), pMutable.getId());
  }

  @Override
  public LoggerEntry get(Long pId) {
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
      params.add(new Object[] {mIdGenerator.getNumericId(), entry.getSql(), entry.getUserName(),
          entry.getExecutionTime(), entry.getTimestamp()});
    }
    return params;
  }

  class LoggerEntryRowMapper implements RowMapper<LoggerEntry> {
    @Override
    public LoggerEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableLoggerEntry loggerEntry = new PersistentLoggerEntry();
      loggerEntry.setId(rs.getLong("ID"));
      loggerEntry.setSql(rs.getString("SQL"));
      loggerEntry.setUserName(rs.getString("USER_NAME"));
      loggerEntry.setExecutionTime(rs.getLong("EXECUTION_TIME"));
      loggerEntry.setTimestamp(rs.getTimestamp("EXECUTION_TIME_STAMP"));
      AtomicReference<LoggerEntry> atomicReference = new AtomicReference<>(loggerEntry);
      return atomicReference.get();
    }
  }
}
