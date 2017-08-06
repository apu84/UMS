package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.BearerAccessTokenDaoDecorator;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.persistent.model.PersistentBearerAccessToken;
import org.ums.security.TokenBuilder;

public class BearerAccessTokenDao extends BearerAccessTokenDaoDecorator {
  String SELECT_ALL = "SELECT TOKEN, REFRESH_TOKEN, USER_ID, LAST_ACCESS_TIME, LAST_MODIFIED FROM BEARER_ACCESS_TOKEN ";
  String INSERT_ALL =
      "INSERT INTO BEARER_ACCESS_TOKEN(TOKEN, REFRESH_TOKEN, USER_ID, LAST_ACCESS_TIME, LAST_MODIFIED) VALUES (?, ?, ?,"
          + " SYSDATE, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE BEARER_ACCESS_TOKEN SET TOKEN = ?, LAST_ACCESS_TIME = SYSDATE, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM BEARER_ACCESS_TOKEN ";

  private JdbcTemplate mJdbcTemplate;
  private TokenBuilder mTokenBuilder;

  public BearerAccessTokenDao(final JdbcTemplate pJdbcTemplate, final TokenBuilder pTokenBuilder) {
    mJdbcTemplate = pJdbcTemplate;
    mTokenBuilder = pTokenBuilder;
  }

  @Override
  public String create(MutableBearerAccessToken pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getRefreshToken(), pMutable.getUserId());
    return pMutable.getId();
  }

  @Override
  public int delete(MutableBearerAccessToken pMutable) {
    String query = DELETE_ALL + "WHERE TOKEN = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(MutableBearerAccessToken pMutable) {
    String query = UPDATE_ALL + "WHERE TOKEN = ?";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getId());
  }

  @Override
  public BearerAccessToken get(String pId) {
    String query = SELECT_ALL + "WHERE TOKEN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new BearerAccessTokenRowMapper());
  }

  @Override
  public List<BearerAccessToken> getByAccessToken(String pId) {
    String query = SELECT_ALL + "WHERE TOKEN = ?";
    return mJdbcTemplate.query(query, new Object[] {pId}, new BearerAccessTokenRowMapper());
  }

  @Override
  public List<BearerAccessToken> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new BearerAccessTokenRowMapper());
  }

  @Override
  public BearerAccessToken getByUser(String userId) {
    String query = SELECT_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {userId}, new BearerAccessTokenRowMapper());
  }

  @Override
  public List<BearerAccessToken> getByRefreshToken(String pRefreshToken) {
    String query = SELECT_ALL + "WHERE REFRESH_TOKEN = ?";
    return mJdbcTemplate.query(query, new Object[] {pRefreshToken}, new BearerAccessTokenRowMapper());
  }

  @Override
  public String newAccessToken(String pRefreshToken) {
    String query = UPDATE_ALL + "WHERE REFRESH_TOKEN = ?";
    String token = mTokenBuilder.accessToken();
    mJdbcTemplate.update(query, token, pRefreshToken);
    return token;
  }

  class BearerAccessTokenRowMapper implements RowMapper<BearerAccessToken> {
    @Override
    public BearerAccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBearerAccessToken accessToken = new PersistentBearerAccessToken();
      accessToken.setId(rs.getString("TOKEN"));
      accessToken.setUserId(rs.getString("USER_ID"));
      accessToken.setLastAccessedTime(rs.getTimestamp("LAST_ACCESS_TIME"));
      accessToken.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<BearerAccessToken> reference = new AtomicReference<>(accessToken);
      return reference.get();
    }
  }
}
