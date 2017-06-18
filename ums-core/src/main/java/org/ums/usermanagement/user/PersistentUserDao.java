package org.ums.usermanagement.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.usermanagement.role.Role;

public class PersistentUserDao extends UserDaoDecorator {
  static String SELECT_ALL =
      "SELECT USER_ID, PASSWORD, ROLE_ID,EMPLOYEE_ID, STATUS, TEMP_PASSWORD,PR_TOKEN,TOKEN_GENERATED_ON, LAST_MODIFIED FROM USERS ";
  static String UPDATE_ALL =
      "UPDATE USERS SET PASSWORD = ?, ROLE_ID = ?, STATUS = ?, TEMP_PASSWORD = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  static String UPDATE_PASSWORD = "UPDATE USERS SET PASSWORD=?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String CLEAR_PASSWORD_RESET_TOKEN =
      "UPDATE USERS SET PR_TOKEN=NULL, TOKEN_GENERATED_ON=NULL, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ALL = "DELETE FROM USERS ";
  static String INSERT_ALL =
      "INSERT INTO USERS(USER_ID, PASSWORD, ROLE_ID, STATUS, TEMP_PASSWORD, LAST_MODIFIED) VALUES "
          + "(?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  static String UPDATE_PASSWORD_RESET_TOKEN =
      "Update USERS Set PR_TOKEN=?, TOKEN_GENERATED_ON=SYSDATE, LAST_MODIFIED = " + getLastModifiedSql()
          + " Where User_Id=? ";
  String EXISTS = "SELECT COUNT(USER_ID) EXIST FROM USERS ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentUserDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<User> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new UserRowMapper());
  }

  @Override
  public User get(String pId) {
    String query = SELECT_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new UserRowMapper());
  }

  @Override
  public User getByEmployeeId(String pEmployeeId) {
    String query = SELECT_ALL + "WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, new UserRowMapper());
  }

  @Override
  public int update(MutableUser pMutable) {
    String query = UPDATE_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getPassword() == null ? "" : String.valueOf(pMutable.getPassword()),
        pMutable.getPrimaryRole().getId(), pMutable.isActive(),
        pMutable.getTemporaryPassword() == null ? "" : String.valueOf(pMutable.getPassword()), pMutable.getId());
  }

  @Override
  public int updatePassword(final String pUserId, final String pPassword) {
    String query = UPDATE_PASSWORD + "WHERE USER_ID = ?";
    return mJdbcTemplate.update(query, pPassword, pUserId);
  }

  @Override
  public int clearPasswordResetToken(final String pUserId) {
    String query = CLEAR_PASSWORD_RESET_TOKEN + "WHERE USER_ID = ?";
    return mJdbcTemplate.update(query, pUserId);
  }

  @Override
  public int delete(MutableUser pMutable) {
    String query = DELETE_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int setPasswordResetToken(String pToken, String pUserId) {
    return mJdbcTemplate.update(UPDATE_PASSWORD_RESET_TOKEN, pToken, pUserId);
  }

  @Override
  public String create(MutableUser pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getId(),
        pMutable.getPassword() == null ? "" : String.valueOf(pMutable.getPassword()),
        pMutable.getPrimaryRole().getId(), pMutable.isActive(),
        pMutable.getTemporaryPassword() == null ? "" : String.valueOf(pMutable.getTemporaryPassword()));
    return pMutable.getId();
  }

  @Override
  public List<User> getUsers() {
    String query = SELECT_ALL + "WHERE ROLE_ID != 11 AND ROLE_ID != 999";
    return mJdbcTemplate.query(query, new UserRowMapper());
  }

  @Override
  public boolean exists(String pId) {
    String query = EXISTS + "WHERE USER_ID = ?";
    return mJdbcTemplate.queryForObject(query, Boolean.class, pId);
  }

  @Override
  public List<User> getUsers(List<Role> pRoles) {
    Set<Integer> roleIds = pRoles.stream().map(Role::getId).collect(Collectors.toSet());
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("roleIds", roleIds);
    String query = SELECT_ALL + "WHERE ROLE_ID IN (:roleIds)";
    NamedParameterJdbcTemplate namedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(mJdbcTemplate.getDataSource());
    return namedParameterJdbcTemplate.query(query, parameters, new UserRowMapper());
  }

  class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUser user = new PersistentUser();
      user.setId(rs.getString("USER_ID"));
      user.setPassword(rs.getString("PASSWORD") == null ? null : rs.getString("PASSWORD").toCharArray());
      user.setPrimaryRoleId(rs.getInt("ROLE_ID"));
      user.setActive(rs.getBoolean("STATUS"));
      user.setTemporaryPassword((rs.getString("TEMP_PASSWORD") == null ? null : rs.getString("TEMP_PASSWORD")
          .toCharArray()));
      user.setPasswordResetToken(rs.getString("PR_TOKEN"));
      user.setEmployeeId(rs.getString("EMPLOYEE_ID") == null ? "" : rs.getString("EMPLOYEE_ID"));
      Timestamp timestamp = rs.getTimestamp("TOKEN_GENERATED_ON");
      if(timestamp != null) {
        user.setPasswordTokenGenerateDateTime(new java.util.Date(timestamp.getTime()));
      }
      user.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<User> atomicReference = new AtomicReference<>(user);
      return atomicReference.get();
    }
  }
}
