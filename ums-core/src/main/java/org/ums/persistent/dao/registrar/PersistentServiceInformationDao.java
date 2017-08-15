package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.ServiceInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.registrar.PersistentServiceInformation;

import javax.print.DocFlavor;
import javax.ws.rs.GET;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentServiceInformationDao extends ServiceInformationDaoDecorator {

  static String INSERT_ONE = "INSERT INTO EMP_SERVICE_INFO (ID, EMPLOYEE_ID, DEPARTMENT, DESIGNATION, "
      + " EMPLOYMENT, JOINING_DATE, RESIGN_DATE, LAST_MODIFIED) " + "VALUES (?, ?, ?, ?, ?, ?, ?, "
      + getLastModifiedSql() + ")";

  static String GET_ONE = "SELECT ID, EMPLOYEE_ID, DEPARTMENT, DESIGNATION, EMPLOYMENT, JOINING_DATE, RESIGN_DATE "
      + "FROM EMP_SERVICE_INFO ";

  static String UPDATE_ONE = "UPDATE EMP_SERVICE_INFO SET DEPARTMENT = ?, DESIGNATION = ?, "
      + " EMPLOYMENT = ?, JOINING_DATE = ?, RESIGN_DATE = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_SERVICE_INFO ";

  static String GET_SERVICE_ID = "SELECT ID FROM EMP_SERVICE_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentServiceInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long saveServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    String query = INSERT_ONE;
    Long serviceId = mIdGenerator.getNumericId();
    mJdbcTemplate.update(query, serviceId, pMutableServiceInformation.getEmployeeId(), pMutableServiceInformation
        .getDepartment().getId(), pMutableServiceInformation.getDesignation().getId(), pMutableServiceInformation
        .getEmployment().getId(), pMutableServiceInformation.getJoiningDate(), pMutableServiceInformation
        .getResignDate());
    return serviceId;
  }

  @Override
  public List<ServiceInformation> getServiceInformation(String pEmployeeId) {
    String query = GET_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new PersistentServiceInformationDao.RoleRowMapper());
  }

  @Override
  public int updateServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutableServiceInformation.getDepartment().getId(), pMutableServiceInformation
        .getDesignation().getId(), pMutableServiceInformation.getEmployment().getId(), pMutableServiceInformation
        .getJoiningDate(), pMutableServiceInformation.getResignDate(), pMutableServiceInformation.getId(),
        pMutableServiceInformation.getEmployeeId());
  }

  @Override
  public int deleteServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutableServiceInformation.getId(), pMutableServiceInformation.getEmployeeId());
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
