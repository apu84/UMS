package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.DeptDesignationMapDaoDecorator;
import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.persistent.model.PersistentDeptDesignationMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentDeptDesignationMapDao extends DeptDesignationMapDaoDecorator {

  static String SELECT_ALL =
      "SELECT ID, DEPARTMENT, EMPLOYEE_TYPE, DESIGNATION, LAST_MODIFIED FROM DEPT_DESIGNATION_MAP";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDeptDesignationMapDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public DeptDesignationMap get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentDeptDesignationMapDao.DeptDesignationRowMapper());
  }

  @Override
  public List<DeptDesignationMap> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentDeptDesignationMapDao.DeptDesignationRowMapper());
  }

  @Override
  public List<DeptDesignationMap> getDeptDesignationMap(final String pDepartment, final int pEmployeeType) {
    String query = SELECT_ALL + " WHERE DEPARTMENT = ? AND EMPLOYEE_TYPE = ?";
    return mJdbcTemplate.query(query, new Object[] {pDepartment, pEmployeeType},
        new PersistentDeptDesignationMapDao.DeptDesignationRowMapper());
  }

  @Override
  public List<DeptDesignationMap> getDeptDesignationMap(final String pDepartment) {
    String query = SELECT_ALL + " WHERE DEPARTMENT = ? ";
    return mJdbcTemplate.query(query, new Object[] {pDepartment},
        new PersistentDeptDesignationMapDao.DeptDesignationRowMapper());
  }

  class DeptDesignationRowMapper implements RowMapper<DeptDesignationMap> {

    @Override
    public DeptDesignationMap mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentDeptDesignationMap persistentDeptDesignationMap = new PersistentDeptDesignationMap();
      persistentDeptDesignationMap.setId(rs.getInt("ID"));
      persistentDeptDesignationMap.setDepartmentId(rs.getString("DEPARTMENT"));
      persistentDeptDesignationMap.setEmployeeTypeId(rs.getInt("EMPLOYEE_TYPE"));
      persistentDeptDesignationMap.setDesignationId(rs.getInt("DESIGNATION"));
      persistentDeptDesignationMap.setLastModified(rs.getString("LAST_MODIFIED"));
      return persistentDeptDesignationMap;
    }
  }

}
