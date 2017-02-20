package org.ums.persistent.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentPermission;
import org.ums.decorator.PermissionDaoDecorator;
import org.ums.domain.model.mutable.MutablePermission;
import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.immutable.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentPermissionDao extends PermissionDaoDecorator {
  static String PERMISSION_SEPARATOR = ",";
  String SELECT_ALL = "SELECT PERMISSION_ID, ROLE_ID, PERMISSIONS, LAST_MODIFIED FROM PERMISSIONS ";
  String INSERT_ALL =
      "INSERT INTO PERMISSIONS (PERMISSION_ID, ROLE_ID, PERMISSIONS, LAST_MODIFIED) VALUES (?, ?, ?, ? "
          + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE PERMISSIONS SET ROLE_ID = ?, PERMISSIONS = ?, LAST_MODIFIED = "
      + getLastModifiedSql() + " WHERE PERMISSION_ID = ? ";
  String DELETE_ALL = "DELETE FROM PERMISSIONS ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentPermissionDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Permission> getPermissionByRole(Role pRole) {
    String query = SELECT_ALL + "WHERE ROLE_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pRole.getId()}, new PermissionRowMapper());
  }

  @Override
  public List<Permission> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new PermissionRowMapper());
  }

  @Override
  public Permission get(Long pId) {
    String query = SELECT_ALL + "WHERE PERMISSION_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PermissionRowMapper());
  }

  @Override
  public int update(MutablePermission pMutable) {
    String query = UPDATE_ALL + "WHERE PERMISSION_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getRole().getId(), Joiner.on(PERMISSION_SEPARATOR)
        .join(pMutable.getPermissions()));
  }

  @Override
  public int delete(MutablePermission pMutable) {
    String query = DELETE_ALL + "WHERE PERMISSION_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutablePermission pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getRole().getId(), Joiner
        .on(PERMISSION_SEPARATOR).join(pMutable.getPermissions()));
    return id;
  }

  class PermissionRowMapper implements RowMapper<Permission> {
    @Override
    public Permission mapRow(ResultSet resultSet, int i) throws SQLException {
      MutablePermission permission = new PersistentPermission();
      permission.setId(resultSet.getLong("PERMISSION_ID"));
      permission.setRoleId(resultSet.getInt("ROLE_ID"));

      String permissions = resultSet.getString("PERMISSIONS");
      if(!StringUtils.isEmpty(permission)) {
        permission.setPermissions(Sets.newHashSet(permissions.split(PERMISSION_SEPARATOR)));
      }

      permission.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Permission> atomicReference = new AtomicReference<>(permission);
      return atomicReference.get();
    }
  }
}
