package org.ums.dummy.shared.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.dummy.shared.model.User;
import org.ums.dummy.shared.model.UserRole;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRoleDaoImpl {
  DataSource dataSource;
  static String SELECT_ALL = "select id, rolename, email from USERROLE";

  public UserRoleDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<UserRole> getRoles(String email) {
    String query = SELECT_ALL + "where email = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    return jdbcTemplate.query(query, new Object[]{email}, new UserRoleMapper());
  }

  public void insertRole(UserRole role) {

  }

  class UserRoleMapper implements RowMapper<UserRole> {
    @Override
    public UserRole mapRow(ResultSet resultSet, int i) throws SQLException {
      UserRole userRole = new UserRole();
      userRole.setUseId(resultSet.getInt("id"));
      userRole.setEmail(resultSet.getString("email"));
      userRole.setRoleName(resultSet.getString("rolename"));
      return userRole;
    }
  }
}
