package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.FCMTokenDaoDecorator;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentFCMToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentFCMTokenDao extends FCMTokenDaoDecorator {

  static String SELECT_ONE = "SELECT ID, TOKEN, TOKEN_DELETED_ON, LAST_MODIFIED FROM FCM_TOKEN";

  static String INSERT_ONE = "INSERT INTO FCM_TOKEN (ID, TOKEN, TOKEN_DELETED_ON, LAST_MODIFIED) VALUES (?, ?, ?,"
      + getLastModifiedSql() + " ) ";

  static String UPDATE_ONE = "UPDATE FCM_TOKEN SET TOKEN = ?, TOKEN_DELETED_ON = sysdate, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";

  static String EXISTS_ONE = "SELECT ID FROM FCM_TOKEN ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

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
    String query = SELECT_ONE + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FCMTokenRowMapper());
  }

  @Override
  public String create(MutableFCMToken pMutable) {
    String query = INSERT_ONE;
    mJdbcTemplate.update(query, pMutable.getId(), pMutable.getFCMToken(), pMutable.getTokenDeleteOn());
    return pMutable.getId();
  }

  @Override
  public int update(MutableFCMToken pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getFCMToken(), pMutable.getId());
  }

  @Override
  public String hasDuplicate(String pFCMToken){
    String query = EXISTS_ONE + " WHERE TOKEN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pFCMToken}, String.class);
  }

  class FCMTokenRowMapper implements RowMapper<FCMToken> {

    @Override
    public FCMToken mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentFCMToken persistentFCMToken = new PersistentFCMToken();
      persistentFCMToken.setId(resultSet.getString("ID"));
      persistentFCMToken.setFCMToken(resultSet.getString("TOKEN"));
      persistentFCMToken.setTokenDeletedOn(resultSet.getDate("TOKEN_DELETED_ON"));
      persistentFCMToken.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return persistentFCMToken;
    }
  }
}
