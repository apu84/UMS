package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.FCMTokenDaoDecorator;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.persistent.model.PersistentFCMToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentFCMTokenDao extends FCMTokenDaoDecorator {

  static String SELECT_ONE = "SELECT ID, TOKEN, CREATED_ON, LAST_MODIFIED FROM FCM_TOKEN";

  static String INSERT_ONE = "INSERT INTO FCM_TOKEN (ID, TOKEN, CREATED_ON, LAST_MODIFIED) VALUES (?, ?, ?,"
      + getLastModifiedSql() + " ) ";

  static String UPDATE_ONE = "UPDATE FCM_TOKEN SET TOKEN = ?, CREATED_ON = ?, LAST_MODIFIED = " + getLastModifiedSql()
      + " ";

  static String EXISTS_ONE = "SELECT COUNT(ID) FROM FCM_TOKEN ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentFCMTokenDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<FCMToken> getAll() {
    String query = SELECT_ONE;
    return mJdbcTemplate.query(query, new PersistentFCMTokenDao.FCMTokenRowMapper());
  }

  @Override
  public FCMToken get(String pId) {
    String query = SELECT_ONE + " WHERE CREATED_ON = (SELECT CREATED_ON FROM FCM_TOKEN WHERE ID = ?)";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FCMTokenRowMapper());
  }

  @Override
  public String create(MutableFCMToken pMutable) {
    String query = INSERT_ONE;
    mJdbcTemplate.update(query, pMutable.getId(), pMutable.getToken(), pMutable.getCreatedOn());
    return pMutable.getId();
  }

  @Override
  public int update(MutableFCMToken pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getToken(), pMutable.getCreatedOn(), pMutable.getId());
  }

  @Override
  public boolean exists(String pId) {
    String query = EXISTS_ONE + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, Boolean.class);
  }

  @Override
  public boolean hasDuplicate(String pToken) {
    String query = EXISTS_ONE + " WHERE TOKEN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pToken}, Boolean.class);
  }

  @Override
  public boolean isDuplicate(String pUserId, String pToken) {
    String query = EXISTS_ONE + " WHERE ID = ? AND TOKEN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pUserId, pToken}, Boolean.class);
  }

  @Override
  public FCMToken getToken(String pToken) {
    String query = SELECT_ONE + " WHERE TOKEN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pToken}, new PersistentFCMTokenDao.FCMTokenRowMapper());
  }

  class FCMTokenRowMapper implements RowMapper<FCMToken> {

    @Override
    public FCMToken mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentFCMToken persistentFCMToken = new PersistentFCMToken();
      persistentFCMToken.setId(resultSet.getString("ID"));
      persistentFCMToken.setToken(resultSet.getString("TOKEN"));
      persistentFCMToken.setCreatedOn(resultSet.getTimestamp("CREATED_ON"));
      persistentFCMToken.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return persistentFCMToken;
    }
  }
}
