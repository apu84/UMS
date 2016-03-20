package org.ums.persistent.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.BearerAccessTokenDaoDecorator;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.persistent.model.PersistentBearerAccessToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BearerAccessTokenDao extends BearerAccessTokenDaoDecorator {
  String SELECT_ALL = "SELECT TOKEN, USER_ID, LAST_ACCESS_TIME FROM BEARER_ACCESS_TOKEN ";
  String INSERT_ALL = "INSERT INTO BEARER_ACCESS_TOKEN(TOKEN, USER_ID, LAST_ACCESS_TIME) VALUES (?, ? ,SYSDATE) ";
  String UPDATE_ALL = "UPDATE BEARER_ACCESS_TOKEN SET LAST_ACCESS_TIME = SYSDATE ";
  String DELETE_ALL = "DELETE FROM BEARER_ACCESS_TOKEN ";

  private JdbcTemplate mJdbcTemplate;

  public BearerAccessTokenDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(MutableBearerAccessToken pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getUserId());
  }

  @Override
  public int delete(MutableBearerAccessToken pMutable) throws Exception {
    return deleteToken(pMutable.getId());
  }

  @Override
  public int update(MutableBearerAccessToken pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE TOKEN = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public BearerAccessToken get(String pId) throws Exception {
    String query = SELECT_ALL + "WHERE TOKEN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new BearerAccessTokenRowMapper());
  }

  @Override
  public List<BearerAccessToken> getAll() throws Exception {
    return mJdbcTemplate.query(SELECT_ALL, new BearerAccessTokenRowMapper());
  }

  @Override
  public int deleteToken(String pToken) {
    String query = DELETE_ALL + "WHERE TOKEN = ?";
    return mJdbcTemplate.update(query, pToken);
  }

  @Override
  public int invalidateTokens(String pUserId) {
    String query = DELETE_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.update(query, pUserId);
  }

  class BearerAccessTokenRowMapper implements RowMapper<BearerAccessToken> {
    @Override
    public BearerAccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBearerAccessToken accessToken = new PersistentBearerAccessToken();
      accessToken.setId(rs.getString("TOKEN"));
      accessToken.setUserId(rs.getString("USER_ID"));
      accessToken.setLastAccessedTime(rs.getDate("LAST_ACCESS_TIME"));
      AtomicReference<BearerAccessToken> reference = new AtomicReference<>(accessToken);
      return reference.get();
    }
  }
}
