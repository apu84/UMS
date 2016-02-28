package org.ums.academic.dao;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.academic.model.PersistentAdditionalRolePermissions;
import org.ums.domain.model.mutable.MutableAdditionalRolePermission;
import org.ums.domain.model.readOnly.AdditionalRolePermissions;
import org.ums.domain.model.readOnly.Role;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class AdditionalRolePermissionsDao extends AdditionalRolePermissionsDaoDecorator {

  String SELECT_ALL = "SELECT ID, USER_ID, ROLE_ID, PERMISSIONS, VALID_FROM, VALID_TO, STATUS, LAST_MODIFIED FROM ADDITIONAL_ROLE_PERMISSIONS ";
  String UPDATE_ALL = "UPDATE ADDITIONAL_ROLE_PERMISSIONS SET USER_ID = ?, ROLE_ID = ?, PERMISSIONS = ?, VALID_FROM = TO_DATE(?, '" + Constants.DATE_FORMAT + "')," +
      "VALID_TO = TO_DATE(?, '" + Constants.DATE_FORMAT + "'), STATUS = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String INSERT_ALL = "INSERT INTO ADDITIONAL_ROLE_PERMISSIONS (USER_ID, ROLE_ID, PERMISSIONS, VALID_FROM, VALID_TO, STATUS, LAST_MODIFIED) VALUES (" +
      "?, ?, ?, TO_DATE(?, '" + Constants.DATE_FORMAT + "', TO_DATE(?, '" + Constants.DATE_FORMAT + "'), ?, " + getLastModifiedSql() + ") ";
  String DELETE_ALL = "DELETE FROM ADDITIONAL_ROLE_PERMISSIONS ";

  private JdbcTemplate mJdbcTemplate;
  private DateFormat mDateFormat;

  public AdditionalRolePermissionsDao(final JdbcTemplate pJdbcTemplate,
                                      final DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  @Override
  public List<AdditionalRolePermissions> getPermissionsByUser(String pUserId) {
    String query = SELECT_ALL + "WHERE USER_ID = ? AND STATUS = 1 AND VALID_FROM <= SYSDATE AND VALID_TO > SYSDATE";
    return mJdbcTemplate.query(query, new Object[]{pUserId}, new RolePermissionsMapper());
  }

  @Override
  public int create(MutableAdditionalRolePermission pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL,
        pMutable.getUser().getId(),
        pMutable.getRole().getId(),
        Joiner.on(PersistentPermissionDao.PERMISSION_SEPARATOR).join(pMutable.getPermission()),
        mDateFormat.format(pMutable.getValidFrom()),
        mDateFormat.format(pMutable.getValidTo()),
        pMutable.isActive() ? 1 : 0);
  }

  @Override
  public int delete(MutableAdditionalRolePermission pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query);
  }

  @Override
  public int update(MutableAdditionalRolePermission pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query,
        pMutable.getUser().getId(),
        pMutable.getRole().getId(),
        Joiner.on(PersistentPermissionDao.PERMISSION_SEPARATOR).join(pMutable.getPermission()),
        mDateFormat.format(pMutable.getValidFrom()),
        mDateFormat.format(pMutable.getValidTo()),
        pMutable.isActive() ? 1 : 0,
        pMutable.getId());
  }

  @Override
  public AdditionalRolePermissions get(Integer pId) throws Exception {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new RolePermissionsMapper());
  }

  @Override
  public List<AdditionalRolePermissions> getAll() throws Exception {
    return mJdbcTemplate.query(SELECT_ALL, new RolePermissionsMapper());
  }


  @Override
  public int addPermissions(String pUserId, Set<String> pPermissions, Date pFromDate, Date pToDate) {
    return mJdbcTemplate.update(INSERT_ALL,
        pUserId,
        0,
        Joiner.on(PersistentPermissionDao.PERMISSION_SEPARATOR).join(pPermissions),
        mDateFormat.format(pFromDate),
        mDateFormat.format(pToDate),
        1);
  }

  @Override
  public int addRole(String pUserId, Role pRole, Date pFromDate, Date pToDate) {
    return mJdbcTemplate.update(INSERT_ALL,
        pUserId,
        pRole.getId(),
        "",
        mDateFormat.format(pFromDate),
        mDateFormat.format(pToDate),
        1);
  }

  class RolePermissionsMapper implements RowMapper<AdditionalRolePermissions> {
    @Override
    public AdditionalRolePermissions mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableAdditionalRolePermission rolePermissions = new PersistentAdditionalRolePermissions();
      rolePermissions.setId(rs.getInt("ID"));
      rolePermissions.setUserId(rs.getString("USER_ID"));
      rolePermissions.setRoleId(rs.getInt("ROLE_ID"));

      String permissions = rs.getString("PERMISSIONS");
      if (!StringUtils.isEmpty(permissions)) {
        rolePermissions.setPermission(Sets.newHashSet(permissions.split(PersistentPermissionDao.PERMISSION_SEPARATOR)));
      }
      rolePermissions.setValidFrom(rs.getDate("VALID_FROM"));
      rolePermissions.setValidTo(rs.getDate("VALID_TO"));
      rolePermissions.setActive(rs.getBoolean("STATUS"));
      rolePermissions.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<AdditionalRolePermissions> reference = new AtomicReference<>(rolePermissions);
      return reference.get();
    }
  }
}
