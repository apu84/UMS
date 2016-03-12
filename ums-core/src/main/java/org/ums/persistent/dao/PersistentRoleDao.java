package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.persistent.model.PersistentRole;
import org.ums.decorator.RoleDaoDecorator;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.domain.model.immutable.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentRoleDao extends RoleDaoDecorator {
  static String SELECT_ALL = "SELECT ROLE_ID, ROLE_NAME FROM MST_ROLE ";
  static String UPDATE_ALL = "UPDATE MST_ROLE set ROLE_NAME=? ";
  static String DELETE_ALL = "DELETE FROM MST_ROLE ";
  static String INSERT_ALL = "INSERT INTO MST_ROLE(ROLE_ID, ROLE_NAME) VALUES(? , ?) ";
  private JdbcTemplate mJdbcTemplate;

  public PersistentRoleDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<Role> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new RoleRowMapper());
  }

  @Override
  public Role get(Integer pId) throws Exception {
    String query = SELECT_ALL + "WHERE ROLE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new RoleRowMapper());
  }

  @Override
  public int update(MutableRole pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE ROLE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int delete(MutableRole pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE ROLE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableRole pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getName());
  }

  class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableRole role = new PersistentRole();
      role.setId(rs.getInt("ROLE_ID"));
      role.setName(rs.getString("ROLE_NAME"));
      AtomicReference<Role> atomicReference = new AtomicReference<>(role);
      return atomicReference.get();
    }
  }
}
