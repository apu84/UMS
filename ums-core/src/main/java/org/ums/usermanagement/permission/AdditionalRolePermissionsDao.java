package org.ums.usermanagement.permission;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class AdditionalRolePermissionsDao extends AdditionalRolePermissionsDaoDecorator {

  String SELECT_ALL =
      "SELECT ID, USER_ID, ROLE_ID, PERMISSIONS, VALID_FROM, VALID_TO, STATUS, LAST_MODIFIED, ASSIGNED_BY FROM ADDITIONAL_ROLE_PERMISSIONS ";
  String UPDATE_ALL =
      "UPDATE ADDITIONAL_ROLE_PERMISSIONS SET USER_ID = ?, ROLE_ID = ?, PERMISSIONS = ?, VALID_FROM = ?,"
          + "VALID_TO = ?, STATUS = ?, LAST_MODIFIED = " + getLastModifiedSql() + ", ASSIGNED_BY = ? ";
  String INSERT_ALL =
      "INSERT INTO ADDITIONAL_ROLE_PERMISSIONS (ID, USER_ID, ROLE_ID, PERMISSIONS, VALID_FROM, VALID_TO, STATUS, LAST_MODIFIED, ASSIGNED_BY) VALUES ("
          + "?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ", ?) ";
  String DELETE_ALL = "DELETE FROM ADDITIONAL_ROLE_PERMISSIONS ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public AdditionalRolePermissionsDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<AdditionalRolePermissions> getPermissionsByUser(String pUserId) {
    String query = SELECT_ALL + "WHERE USER_ID = ? AND STATUS = 1 AND VALID_FROM <= SYSDATE AND VALID_TO > SYSDATE";
    return mJdbcTemplate.query(query, new Object[] {pUserId}, new RolePermissionsMapper());
  }

  @Override
  public List<AdditionalRolePermissions> getUserPermissionsByAssignedUser(String pUserId, String pAssignedBy) {
    String query = SELECT_ALL + "WHERE USER_ID = ? AND ASSIGNED_BY = ? AND STATUS = 1 ";
    return mJdbcTemplate.query(query, new Object[] {pUserId, pAssignedBy}, new RolePermissionsMapper());
  }

  @Override
  public Long create(MutableAdditionalRolePermissions pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getUser().getId(), pMutable.getRole() == null ? 0 : pMutable
        .getRole().getId(), Joiner.on(PersistentPermissionDao.PERMISSION_SEPARATOR).join(pMutable.getPermission()),
        pMutable.getValidFrom(), pMutable.getValidTo(), pMutable.isActive() ? 1 : 0, pMutable.getAssignedBy().getId());
    return id;
  }

  @Override
  public int delete(MutableAdditionalRolePermissions pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query);
  }

  @Override
  public int update(MutableAdditionalRolePermissions pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getUser().getId(), pMutable.getRole().getId(),
        Joiner.on(PersistentPermissionDao.PERMISSION_SEPARATOR).join(pMutable.getPermission()),
        pMutable.getValidFrom(), pMutable.getValidTo(), pMutable.isActive() ? 1 : 0, pMutable.getId());
  }

  @Override
  public AdditionalRolePermissions get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new RolePermissionsMapper());
  }

  @Override
  public List<AdditionalRolePermissions> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new RolePermissionsMapper());
  }

  @Override
  public List<AdditionalRolePermissions> getAdditionalRole(String pDepartmentId) {
    String query =
        "select * from ADDITIONAL_ROLE_PERMISSIONS where USER_ID in (select SHORT_NAME from EMPLOYEES where DEPT_OFFICE=?)";
    return mJdbcTemplate.query(query, new Object[] {pDepartmentId}, new RolePermissionsMapper());
  }

  @Override
  public int removeExistingAdditionalRolePermissions(String pUserId, String pAssignedBy) {
    String query = DELETE_ALL + "WHERE ASSIGNED_BY = ? AND USER_ID = ?";
    return mJdbcTemplate.update(query, pAssignedBy, pUserId);
  }

  @Override
  public int addPermissions(String pUserId, Set<String> pPermissions, User pAssignedBy, Date pFromDate, Date pToDate) {
    String query = DELETE_ALL + "WHERE ASSIGNED_BY = ? AND USER_ID = ?";
    mJdbcTemplate.update(query, pAssignedBy.getId(), pUserId);

    return mJdbcTemplate.update(INSERT_ALL, pUserId, 0,
        Joiner.on(PersistentPermissionDao.PERMISSION_SEPARATOR).join(pPermissions), pFromDate, pToDate, 1,
        pAssignedBy.getId());
  }

  @Override
  public int addRole(String pUserId, Role pRole, User pAssignedBy, Date pFromDate, Date pToDate) {
    return mJdbcTemplate.update(INSERT_ALL, pUserId, pRole.getId(), "", pFromDate, pToDate, 1, pAssignedBy.getId());
  }

  class RolePermissionsMapper implements RowMapper<AdditionalRolePermissions> {
    @Override
    public AdditionalRolePermissions mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableAdditionalRolePermissions rolePermissions = new PersistentAdditionalRolePermissions();
      rolePermissions.setId(rs.getLong("ID"));
      rolePermissions.setUserId(rs.getString("USER_ID"));
      if(rs.getInt("ROLE_ID") > 0) {
        rolePermissions.setRoleId(rs.getInt("ROLE_ID"));
      }

      String permissions = rs.getString("PERMISSIONS");
      if(!StringUtils.isEmpty(permissions)) {
        rolePermissions.setPermission(Sets.newHashSet(permissions.split(PersistentPermissionDao.PERMISSION_SEPARATOR)));
      }
      rolePermissions.setAssignedByUserId(rs.getString("ASSIGNED_BY"));
      rolePermissions.setValidFrom(rs.getDate("VALID_FROM"));
      rolePermissions.setValidTo(rs.getDate("VALID_TO"));
      rolePermissions.setActive(rs.getBoolean("STATUS"));
      rolePermissions.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<AdditionalRolePermissions> reference = new AtomicReference<>(rolePermissions);
      return reference.get();
    }
  }
}
