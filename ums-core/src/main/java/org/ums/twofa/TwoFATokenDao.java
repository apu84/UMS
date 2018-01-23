package org.ums.twofa;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.configuration.UMSConfiguration;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TwoFATokenDao extends TwoFATokenDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, USER_ID, TYPE, IS_USED, GENERATED_ON, EXPIRY_ON, USED_ON, TRY_COUNT, OTP, LAST_MODIFIED From  TWO_FA_TOKEN ";

  String INSERT =
      "INSERT INTO TWO_FA_TOKEN(ID, USER_ID, TYPE, IS_USED, GENERATED_ON, EXPIRY_ON, TRY_COUNT, OTP, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?, SYSDATE, ?,  ?, ?, " + getLastModifiedSql() + ")";

  String UPDATE_WRONG_TRY = "UPDATE TWO_FA_TOKEN SET TRY_COUNT = TRY_COUNT+1,   LAST_MODIFIED = "
      + getLastModifiedSql();

  String UPDATE_RIGHT_TRY =
      "UPDATE TWO_FA_TOKEN SET IS_USED = 1, USED_ON = sysdate, TRY_COUNT = TRY_COUNT+1, LAST_MODIFIED =  "
          + getLastModifiedSql();

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
    String query = SELECT_ALL + "WHERE ID = ? Order by LAST_MODIFIED Desc";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new TwoFATokenRowMapper());
  }

  public TwoFAToken get(Long pId, String userId, String pType) {
    String query = SELECT_ALL + "WHERE ID = ? and User_Id=? And Type=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId, userId, pType}, new TwoFATokenRowMapper());
  }

  @Override
  public List<TwoFAToken> getTokens(String pUserId, String pState) {
    String query = SELECT_ALL + " WHERE USER_ID = ? AND ID = ? Order by LAST_MODIFIED Desc";
    return mJdbcTemplate.query(query, new Object[] {pUserId, pState}, new TwoFATokenRowMapper());
  }

  @Override
  public int update(MutableTwoFAToken pToken) {
    throw new NotImplementedException();
  }

  @Override
  public int updateWrongTryCount(Long pTokenId) {
    String query = UPDATE_WRONG_TRY + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pTokenId);
  }

  @Override
  public int updateRightTryCount(Long pTokenId) {
    String query = UPDATE_RIGHT_TRY + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pTokenId);
  }

  @Override
  public int delete(MutableTwoFAToken pMutable) {
    throw new NotImplementedException();
  }

  @Override
  public Long create(MutableTwoFAToken pToken) {
    Long id = mIdGenerator.getNumericId();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.SECOND, mUMSConfiguration.getTwoFATokenLifeTime());
    mJdbcTemplate.update(INSERT, id, pToken.getUserId(), pToken.getType(), 0, calendar, 0, pToken.getOtp());
    return id;
  }

  @Override
  public List<TwoFAToken> getUnExpiredTokens(String pUserId, String pType) {
    String query =
        SELECT_ALL + "WHERE USER_ID = ? and TYPE=? AND EXPIRY_ON > SYSDATE AND IS_USED=0 Order by LAST_MODIFIED Desc";
    return mJdbcTemplate.query(query, new Object[] {pUserId, pType}, new TwoFATokenRowMapper());
  }

  class TwoFATokenRowMapper implements RowMapper<TwoFAToken> {
    @Override
    public TwoFAToken mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableTwoFAToken token = new PersistentTwoFAToken();
      token.setId(rs.getLong("ID"));
      token.setUserId(rs.getString("USER_ID"));
      token.setType(rs.getString("TYPE"));
      token.setUsed(rs.getBoolean("IS_USED"));
      token.setGeneratedOn(new Date(rs.getTimestamp("GENERATED_ON").getTime()));
      token.setExpiredOn(new Date(rs.getTimestamp("EXPIRY_ON").getTime()));
      token.setUsedOn(rs.getTimestamp("USED_ON") == null ? null : new Date(rs.getTimestamp("USED_ON").getTime()));
      token.setTryCount(rs.getInt("TRY_COUNT"));
      token.setOtp(rs.getString("OTP"));
      token.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<TwoFAToken> reference = new AtomicReference<>(token);
      return reference.get();
    }
  }
}
