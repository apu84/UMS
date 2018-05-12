package org.ums.employee.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentServiceInformationDao extends ServiceInformationDaoDecorator {

  static String INSERT_ONE = "INSERT INTO EMP_SERVICE_INFO (ID, EMPLOYEE_ID, DEPARTMENT, DESIGNATION, "
      + " EMPLOYMENT, JOINING_DATE, RESIGN_DATE, LAST_MODIFIED) " + "VALUES (?, ?, ?, ?, ?, ?, ?, "
      + getLastModifiedSql() + ")";

  static String GET_ALL = "SELECT ID, EMPLOYEE_ID, DEPARTMENT, DESIGNATION, EMPLOYMENT, JOINING_DATE, RESIGN_DATE "
      + "FROM EMP_SERVICE_INFO ";

  static String UPDATE_ONE = "UPDATE EMP_SERVICE_INFO SET DEPARTMENT = ?, DESIGNATION = ?, "
      + " EMPLOYMENT = ?, JOINING_DATE = ?, RESIGN_DATE = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_SERVICE_INFO ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_SERVICE_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentServiceInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableServiceInformation pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getEmployeeId(), pMutable.getDepartment().getId(), pMutable
        .getDesignation().getId(), pMutable.getEmployment().getId(), pMutable.getJoiningDate(), pMutable
        .getResignDate());
    return id;
  }

  @Override
  public ServiceInformation get(Long pId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ? ORDER BY JOINING_DATE DESC";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentServiceInformationDao.RoleRowMapper());
  }

  @Override
  public List<ServiceInformation> get(String pEmployeeId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ? ORDER BY JOINING_DATE DESC";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new PersistentServiceInformationDao.RoleRowMapper());
  }

  @Override
  public int update(MutableServiceInformation pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getDepartment().getId(), pMutable.getDesignation().getId(), pMutable
        .getEmployment().getId(), pMutable.getJoiningDate(), pMutable.getResignDate(), pMutable.getId(), pMutable
        .getEmployeeId());
  }

  @Override
  public int delete(MutableServiceInformation pMutable) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<ServiceInformation> {
    @Override
    public ServiceInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentServiceInformation persistentServiceInformation = new PersistentServiceInformation();
      persistentServiceInformation.setId(resultSet.getLong("ID"));
      persistentServiceInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      persistentServiceInformation.setDepartmentId(resultSet.getString("DEPARTMENT"));
      persistentServiceInformation.setDesignationId(resultSet.getInt("DESIGNATION"));
      persistentServiceInformation.setEmploymentId(resultSet.getInt("EMPLOYMENT"));
      persistentServiceInformation.setJoiningDate(resultSet.getDate("JOINING_DATE"));
      persistentServiceInformation.setResignDate(resultSet.getDate("RESIGN_DATE"));
      return persistentServiceInformation;
    }
  }
}
