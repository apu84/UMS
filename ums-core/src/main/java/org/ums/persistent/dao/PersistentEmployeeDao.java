package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.EmployeeDaoDecorator;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.persistent.model.PersistentEmployee;

public class PersistentEmployeeDao extends EmployeeDaoDecorator {

  static String SELECT_ALL = "SELECT EMPLOYEE_ID, SHORT_NAME, DESIGNATION," + "EMPLOYMENT_TYPE,DEPT_OFFICE,"
      + "JOINING_DATE,STATUS,EMPLOYEE_TYPE,LAST_MODIFIED FROM EMPLOYEES";

  static String INSERT_ONE = "INSERT INTO EMPLOYEES (EMPLOYEE_ID, SHORT_NAME, DESIGNATION,EMPLOYMENT_TYPE,DEPT_OFFICE,"
      + "JOINING_DATE,STATUS,EMPLOYEE_TYPE,LAST_MODIFIED) " + "values (?,?,?,?,?,?,?,?," + getLastModifiedSql() + ")";

  static String UPDATE_ONE = "UPDATE EMPLOYEES SET SHORT_NAME = ?,DESIGNATION=?,EMPLOYMENT_TYPE=?,DEPT_OFFICE=?,"
      + "JOINING_DATE=?,STATUS=?,EMPLOYEE_TYPE=?,LAST_MODIFIED=" + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMPLOYEES ";

  static String EXISTS = "SELECT COUNT(*) AS COUNTER FROM EMPLOYEES ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentEmployeeDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Employee get(String pId) {
    String query = SELECT_ALL + " WHERE EMPLOYEE_ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new EmployeeRowMapper());
  }

  @Override
  public List<Employee> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new EmployeeRowMapper());
  }

  @Override
  public int update(MutableEmployee pMutable) {
    String query = UPDATE_ONE + " WHERE EMPLOYEE_ID=? ";
    return mJdbcTemplate.update(query, pMutable.getShortName(), pMutable.getDesignation(),
        pMutable.getEmploymentType(), pMutable.getDepartment().getId(), pMutable.getJoiningDate(),
        pMutable.getStatus(), pMutable.getEmployeeType(), pMutable.getId());
  }

  @Override
  public int delete(MutableEmployee pMutable) {
    String query = DELETE_ONE + " WHERE EMPLOYEE_ID=? ";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public List<Employee> getByDesignation(String pDesignationId) {
    String query = SELECT_ALL + " WHERE DESIGNATION=?";
    return mJdbcTemplate.query(query, new Object[] {pDesignationId}, new EmployeeRowMapper());
  }

  @Override
  public Employee getByShortName(String pShortName) {
    String query = SELECT_ALL + " WHERE SHORT_NAME=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pShortName}, new EmployeeRowMapper());
  }

  @Override
  public List<Employee> getActiveTeachersOfDept(String deptId) {
    String query = SELECT_ALL + " WHERE DEPT_OFFICE=? AND STATUS=1 ORDER BY DESIGNATION";
    return mJdbcTemplate.query(query, new Object[] {deptId}, new EmployeeRowMapper());
  }

  @Override
  public List<Employee> getEmployees(String pDeparmtentId) {
    String query = SELECT_ALL + " WHERE DEPT_OFFICE=? ORDER BY DESIGNATION";
    return mJdbcTemplate.query(query, new Object[] {pDeparmtentId}, new EmployeeRowMapper());
  }

  public List<Employee> getEmployees(String pDeptId, String pPublicationStatus) {
    String query =
        SELECT_ALL
            + " WHERE DEPT_OFFICE=? AND STATUS='1' AND EMPLOYEE_ID IN (SELECT DISTINCT EMPLOYEE_ID FROM EMP_PUBLICATION_INFO WHERE STATUS=?)";
    return mJdbcTemplate.query(query, new Object[] {pDeptId, pPublicationStatus}, new EmployeeRowMapper());
  }

  @Override
  public String create(MutableEmployee pMutable) {
    mJdbcTemplate.update(INSERT_ONE, pMutable.getId(), pMutable.getShortName(), pMutable.getDesignation(),
        pMutable.getEmploymentType(), pMutable.getDepartment().getId(), pMutable.getJoiningDate(),
        pMutable.getStatus(), pMutable.getEmployeeType());
    return pMutable.getId();
  }

  @Override
  public String getLastEmployeeId(String pDepartmentId, int pEmployeeType) {
    String query = "SELECT MAX(EMPLOYEE_ID) AS EMPLOYEE_ID FROM EMPLOYEES WHERE DEPT_OFFICE = ? AND EMPLOYEE_TYPE = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pDepartmentId, pEmployeeType}, String.class);
  }

  @Override
  public boolean validateShortName(String pShortName) {
    String query = EXISTS + " WHERE SHORT_NAME = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pShortName}, Boolean.class);
  }

  class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet pResultSet, int pI) throws SQLException {

      PersistentEmployee persistentEmployee = new PersistentEmployee();
      persistentEmployee.setId(pResultSet.getString("EMPLOYEE_ID"));
      persistentEmployee.setShortName(pResultSet.getString("SHORT_NAME"));
      persistentEmployee.setDesignationId(pResultSet.getInt("DESIGNATION"));
      persistentEmployee.setEmploymentType(pResultSet.getString("EMPLOYMENT_TYPE"));
      persistentEmployee.setDepartmentId(pResultSet.getString("DEPT_OFFICE"));
      persistentEmployee.setJoiningDate(pResultSet.getDate("JOINING_DATE"));
      persistentEmployee.setStatus(pResultSet.getInt("STATUS"));
      persistentEmployee.setEmployeeType(pResultSet.getInt("EMPLOYEE_TYPE"));
      persistentEmployee.setLastModified(pResultSet.getString("LAST_MODIFIED"));

      AtomicReference<Employee> atomicReference = new AtomicReference<>(persistentEmployee);
      return atomicReference.get();

    }
  }
}
