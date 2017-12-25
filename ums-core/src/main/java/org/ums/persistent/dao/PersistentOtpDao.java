package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.OtpDaoDecorator;
import org.ums.domain.model.immutable.Otp;
import org.ums.domain.model.mutable.MutableOtp;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentOtp;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentOtpDao extends OtpDaoDecorator {

  String SELECT_ALL = "SELECT ID, USER_ID, TYPE, IS_USED, GENERATED_ON, EXPIRED_ON, USED_ON, TRY_COUNT, OTP From  OTP ";
  String INSERT = "INSERT INTO OTP(USER_ID, TYPE, IS_USED, GENERATED_ON, EXPIRED_ON, USED_ON, TRY_COUNT, OTP) "
      + "VALUES(?, ?, ?, ?, ?  ?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOtpDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableOtp pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT, pMutable.getUserId(), pMutable.getType(), pMutable.isUsed(),
        pMutable.getGeneratedOn(), pMutable.getExpiredOn(), pMutable.getUsedOn(), pMutable.getTryCount(),
        pMutable.getOtp());
    return id;
  }

  @Override
  public List<Otp> getAll() {
    throw new NotImplementedException();
  }

  @Override
  public Otp get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentOtpDao.OtpRowMapper());
  }

  @Override
  public Otp get(String pUserId, String pType, String pOtp) {
    String query = SELECT_ALL + "WHERE USER_ID=? AND TYPE=? AND OTP=?";
    return mJdbcTemplate
        .queryForObject(query, new Object[] {pUserId, pType, pOtp}, new PersistentOtpDao.OtpRowMapper());
  }

  @Override
  public Otp get(String pUserId, String pType) {
    String query = SELECT_ALL + "WHERE  USER_ID=? AND TYPE=? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pUserId, pType}, new PersistentOtpDao.OtpRowMapper());
  }

  private class OtpRowMapper implements RowMapper<Otp> {
    @Override
    public Otp mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableOtp otp = new PersistentOtp();
      otp.setId(rs.getLong("ID"));
      otp.setUserId(rs.getString("USER_ID"));
      otp.setType(rs.getString("TYPE"));
      otp.setUsed(rs.getBoolean("IS_USED"));
      otp.setGeneratedOn(rs.getDate("GENERATED_ON"));
      otp.setExpiredOn(rs.getDate("EXPIRED_ON"));
      otp.setUsedOn(rs.getDate("USED_ON"));
      otp.setTryCount(rs.getInt("TRY_COUNT"));
      otp.setOtp(rs.getString("OTP"));
      otp.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Otp> atomicReference = new AtomicReference<>(otp);
      return atomicReference.get();
    }
  }

}
