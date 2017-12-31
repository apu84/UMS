package org.ums.twofa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.configuration.UMSConfiguration;
import org.ums.generator.IdGenerator;

public class TwoFATokenDao extends TwoFATokenDaoDecorator {
  String SELECT_ALL = "SELECT ID, STATE, TOKEN, TOKEN_EXPIRY, USER_ID, LAST_MODIFIED FROM TWO_FA_TOKEN ";
  String INSERT_ALL = "INSERT INTO TWO_FA_TOKEN (ID, STATE, TOKEN, TOKEN_EXPIRY, USER_ID, LAST_MODIFIED) VALUES "
      + "(?, ?, ?, ? ,?, " + getLastModifiedSql() + " )";
  String UPDATE_ALL = "UPDATE TWO_FA_TOKEN SET STATE = ?, TOKEN = ?, TOKEN_EXPIRY = ?, USER_ID = ?, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM TWO_FA_TOKEN ";

  private JdbcTemplate mJdbcTemplate;
  private UMSConfiguration mUMSConfiguration;
  private IdGenerator mIdGenerator;

  public TwoFATokenDao(JdbcTemplate pJdbcTemplate, UMSConfiguration pUMSConfiguration, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mUMSConfiguration = pUMSConfiguration;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<TwoFAToken> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new TwoFATokenRowMapper());
  }

  @Override
  public TwoFAToken get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new TwoFATokenRowMapper());
  }

  @Override
  public int update(MutableTwoFAToken pToken) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pToken.getState(), pToken.getToken(), pToken.getTokenExpiry(),
        pToken.getUserId());
  }

  @Override
  public int delete(MutableTwoFAToken pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableTwoFAToken pToken) {
    Long id = mIdGenerator.getNumericId();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, mUMSConfiguration.getTwoFATokenExpiry());
    mJdbcTemplate.update(INSERT_ALL, id, pToken.getState(), pToken.getToken(), calendar.getTime(), pToken.getUserId());
    return id;
  }

  @Override
  public List<TwoFAToken> getUnExpiredTokens(String pUserId) {
    String query = SELECT_ALL + "WHERE USER_ID = ? AND TOKEN_EXPIRY > SYSDATE ORDER BY LAST_MODIFIED DESC";
    return mJdbcTemplate.query(query, new Object[] {pUserId}, new TwoFATokenRowMapper());
  }

  @Override
  public List<TwoFAToken> getTokens(String pUserId, String pState) {
    String query = SELECT_ALL + "WHERE USER_ID = ? AND STATE = ? ORDER BY LAST_MODIFIED DESC";
    return mJdbcTemplate.query(query, new Object[] {pUserId, pState}, new TwoFATokenRowMapper());
  }

  class TwoFATokenRowMapper implements RowMapper<TwoFAToken> {
    @Override
    public TwoFAToken mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableTwoFAToken token = new PersistentTwoFAToken();
      token.setId(rs.getLong("ID"));
      token.setState(rs.getString("STATE"));
      token.setToken(rs.getString("TOKEN"));
      token.setTokenExpiry(rs.getTimestamp("TOKEN_EXPIRY"));
      token.setLastModified(rs.getString("LAST_MODIFIED"));
      token.setUserId(rs.getString("USER_ID"));
      AtomicReference<TwoFAToken> reference = new AtomicReference<>(token);
      return reference.get();
    }
  }
}
