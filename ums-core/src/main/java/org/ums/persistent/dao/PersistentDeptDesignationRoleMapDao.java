package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.DeptDesignationRoleMapDaoDecorator;
import org.ums.domain.model.immutable.DeptDesignationRoleMap;
import org.ums.persistent.model.PersistentDeptDesignationRoleMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentDeptDesignationRoleMapDao extends DeptDesignationRoleMapDaoDecorator {

  static String SELECT_ALL =
      "SELECT ID, DEPARTMENT, EMPLOYEE_TYPE, DESIGNATION, ROLE_ID, LAST_MODIFIED FROM DEPT_DESIGNATION_ROLE_MAP";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDeptDesignationRoleMapDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public DeptDesignationRoleMap get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new DeptDesignationRoleRowMapper());
  }

  @Override
  public List<DeptDesignationRoleMap> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new DeptDesignationRoleRowMapper());
  }

  @Override
  public List<DeptDesignationRoleMap> getDeptDesignationMap(final String pDepartment, final int pEmployeeType) {
    String query = SELECT_ALL + " WHERE DEPARTMENT = ? AND EMPLOYEE_TYPE = ?";
    return mJdbcTemplate.query(query, new Object[] {pDepartment, pEmployeeType}, new DeptDesignationRoleRowMapper());
  }

  @Override
  public List<DeptDesignationRoleMap> getDeptDesignationMap(final String pDepartment) {
    String query = SELECT_ALL + " WHERE DEPARTMENT = ? ";
    return mJdbcTemplate.query(query, new Object[] {pDepartment}, new DeptDesignationRoleRowMapper());
  }

  class DeptDesignationRoleRowMapper implements RowMapper<DeptDesignationRoleMap> {

    @Override
    public DeptDesignationRoleMap mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentDeptDesignationRoleMap persistentDeptDesignationRoleMap = new PersistentDeptDesignationRoleMap();
      persistentDeptDesignationRoleMap.setId(rs.getInt("ID"));
      persistentDeptDesignationRoleMap.setDepartmentId(rs.getString("DEPARTMENT"));
      persistentDeptDesignationRoleMap.setEmployeeTypeId(rs.getInt("EMPLOYEE_TYPE"));
      persistentDeptDesignationRoleMap.setDesignationId(rs.getInt("DESIGNATION"));
      persistentDeptDesignationRoleMap.setRoleId(rs.getInt("ROLE_ID"));
      persistentDeptDesignationRoleMap.setLastModified(rs.getString("LAST_MODIFIED"));
      return persistentDeptDesignationRoleMap;
    }
  }

}
