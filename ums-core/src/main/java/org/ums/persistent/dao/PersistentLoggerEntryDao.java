package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.LoggerEntry;
import org.ums.domain.model.mutable.MutableLoggerEntry;
import org.ums.manager.LoggerEntryManager;

public class PersistentLoggerEntryDao extends ContentDaoDecorator<LoggerEntry, MutableLoggerEntry, Integer, LoggerEntryManager> implements LoggerEntryManager{
  static String SELECT_ALL = "SELECT ID, SQL, USER_NAME, EXECUTION_TIME, EXECUTION_TIME_STAMP FROM DB_LOGGER ";
  static String UPDATE_ONE = "UPDATE DB_LOGGER SET SQL = ?, USER_NAME = ?, EXECUTION_TIME = ?, EXECUTION_TIME_STAMP = ? " ;
  static String DELETE_ONE = "DELETE FROM DB_LOGGER ";
  static String INSERT_ONE = "INSERT INTO DB_LOGGER(SQL, USER_NAME, COURSE_TITLE, EXECUTION_TIME, EXECUTION_TIME_STAMP) " +
      "VALUES(?, ?, ?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentLoggerEntryDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }
}
