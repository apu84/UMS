package org.ums.dummy.shared.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.ums.dummy.shared.model.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

  private DataSource dataSource;

  static String SELECT_ALL = "select user_id, first_name, last_name, user_name, gender, employment_status from dummy_user";

  public UserDaoImpl(DataSource pDataSource) {
    this.dataSource = pDataSource;
  }

  public User get(final int userId) {
    String query = SELECT_ALL + " where user_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    return jdbcTemplate.queryForObject(query, new Object[]{userId}, new UserRowMapper());
  }

  public List<User> getAll() {
    String query = SELECT_ALL;
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    return jdbcTemplate.query(query, new UserRowMapper());
  }

  public void update(User user) {
    String query = "update dummy_user set first_name = ?, last_name = ?, user_name = ?, gender = ?, employment_status = ? " +
        "where user_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update(query, user.getFirstName(), user.getLastName(), user.getUserName(), user.getGender(),
        user.getEmploymentStatus(), user.getUserId());
  }

  public void remove(User user) {
    String query = "delete from dummy_user where user_id = ?";
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update(query, user.getUserId());
  }

  public void create(User user) {
    String query = "insert into dummy_user(user_name, first_name, last_name, gender, employment_status) " +
        "values (?, ?, ?, ?, ?)";
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update(query, user.getUserName(), user.getFirstName(), user.getLastName(), user.getGender(),
        user.getEmploymentStatus());
  }

  class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
      User user = new User();
      user.setUserId(resultSet.getInt("user_id"));
      user.setFirstName(resultSet.getString("first_name"));
      user.setLastName(resultSet.getString("last_name"));
      user.setEmploymentStatus(resultSet.getString("employment_status"));
      user.setGender(resultSet.getString("gender"));
      return user;
    }
  }
}
