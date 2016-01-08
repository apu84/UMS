package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentDepartment;
import org.ums.domain.model.Department;
import org.ums.domain.model.MutableDepartment;
import org.ums.domain.model.Program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentDepartmentDao extends ContentDaoDecorator<Department, MutableDepartment, Integer> {
  static String SELECT_ALL = "SELECT DEPT_ID, SHORT_NAME, LONG_NAME, TYPE, LAST_MODIFIED FROM MST_DEPT_OFFICE ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDepartmentDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public Department get(final Integer pId) throws Exception {
    String query = SELECT_ALL + "WHERE DEPT_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new DepartmentRowMapper());
  }

  public List<Department> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new DepartmentRowMapper());
  }

  class DepartmentRowMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentDepartment department = new PersistentDepartment();
      department.setId(resultSet.getString("DEPT_ID"));
      department.setLongName(resultSet.getString("LONG_NAME"));
      department.setShortName(resultSet.getString("SHORT_NAME"));
      department.setType(resultSet.getInt("TYPE"));
      department.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Department> atomicReference = new AtomicReference<>(department);
      return atomicReference.get();
    }
  }
}
