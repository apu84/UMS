package org.ums.academic.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentUser;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.readOnly.User;
import org.ums.manager.ContentManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentUserDao extends ContentDaoDecorator<User, MutableUser, String, ContentManager<User, MutableUser, String>> {
  static String SELECT_ALL = "SELECT USER_ID, PASSWORD, ROLE_ID, STATUS, TEMP_PASSWORD FROM USERS ";
  static String UPDATE_ALL = "UPDATE USERS SET PASSWORD = ?, ROLE_ID = ?, STATUS = ?, TEMP_PASSWORD = ? ";
  static String DELETE_ALL = "DELETE FROM USERS ";
  static String INSERT_ALL = "INSERT INTO USERS(USER_ID, PASSWORD, ROLE_ID, STATUS, TEMP_PASSWORD) VALUES " +
      "(?, ?, ?, ?, ?)";

  static String SELECT_PASSWORD_RESET_TOKEN="Select PR_TOKEN,TOKEN_EXP_DATE, " +
      "CASE WHEN TOKEN_EXP_DATE IS NULL THEN 'INVALID' " +
      "     WHEN TOKEN_EXP_DATE>=SYSDATE THEN 'INVALID' " +
      "          ELSE 'VALID' " +
      "     END AS 'STATUS' " +
      " from USERS Where User_Id=? ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentUserDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<User> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new UserRowMapper());
  }

  @Override
  public User get(String pId) throws Exception {
    String query = SELECT_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new UserRowMapper());
  }

  @Override
  public void update(MutableUser pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE USER_ID = ?";
    mJdbcTemplate.update(query, pMutable.getPassword() == null ? "" : String.valueOf(pMutable.getPassword()),
        pMutable.getRole().getId(), pMutable.isActive(),
        pMutable.getTemporaryPassword() == null ? "" : String.valueOf(pMutable.getPassword()), pMutable.getId());
  }

  @Override
  public void delete(MutableUser pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE USER_ID = ?";
    mJdbcTemplate.update(query, pMutable.getId());
  }

  public User getUserPasswordResetToken(String pUserId) throws Exception{
    String query = SELECT_PASSWORD_RESET_TOKEN;
    return mJdbcTemplate.queryForObject(query, new Object[]{pUserId}, new UserRowMapper());
  }

  @Override
  public void create(MutableUser pMutable) throws Exception {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getPassword() == null ? "" : String.valueOf(pMutable.getPassword()),
        pMutable.getRole().getId(), pMutable.isActive(), pMutable.getTemporaryPassword() == null ? "" : String.valueOf(pMutable.getTemporaryPassword()));
  }

  class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUser user = new PersistentUser();
      user.setId(rs.getString("USER_ID"));
      user.setPassword(rs.getString("PASSWORD") == null ? null : rs.getString("PASSWORD").toCharArray());
      user.setRoleId(rs.getInt("ROLE_ID"));
      user.setActive(rs.getBoolean("STATUS"));
      user.setTemporaryPassword((rs.getString("TEMP_PASSWORD") == null ? null : rs.getString("TEMP_PASSWORD").toCharArray()));
      AtomicReference<User> atomicReference = new AtomicReference<>(user);
      return atomicReference.get();
    }
  }
}
