package org.ums.usermanagement.userView;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentUserViewDao extends UserViewDaoDecorator {
  static String SELECT_ALL =
      "SELECT USER_ID, USER_NAME, GENDER, DATE_OF_BIRTH, BLOOD_GROUP, FATHER_NAME, MOTHER_NAME, MOBILE_NUMBER, MOBILE_NUMBER,"
          + " EMAIL_ADDRESS, DEPARTMENT, DESIGNATION, ROLE_ID, STATUS FROM MVIEW_USERS ";

  String EXISTS = "SELECT COUNT(USER_ID) EXIST FROM MVIEW_USERS ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentUserViewDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<UserView> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new UserRowMapper());
  }

  @Override
  public UserView get(String pId) {
    String query = SELECT_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new UserRowMapper());
  }

  @Override
  public boolean exists(String pId) {
    String query = EXISTS + "WHERE USER_ID = ?";
    return mJdbcTemplate.queryForObject(query, Boolean.class, pId);
  }

  class UserRowMapper implements RowMapper<UserView> {
    @Override
    public UserView mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUserView userView = new PersistentUserView();
      userView.setId(rs.getString("USER_ID"));
      userView.setUserName(rs.getString("USER_NAME"));
      userView.setGender(rs.getString("GENDER"));
      userView.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
      userView.setBloodGroup(rs.getString("BLOOD_GROUP"));
      userView.setFatherName(rs.getString("FATHER_NAME"));
      userView.setMotherName(rs.getString("MOTHER_NAME"));
      userView.setMobileNumber(rs.getString("MOBILE_NUMBER"));
      userView.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
      userView.setDepartment(rs.getString("DEPARTMENT"));
      userView.setDesignation(rs.getInt("DESIGNATION"));
      userView.setRoleId(rs.getInt("ROLE_ID"));
      userView.setStatus(rs.getInt("STATUS"));
      AtomicReference<UserView> atomicReference = new AtomicReference<>(userView);
      return atomicReference.get();
    }
  }
}
