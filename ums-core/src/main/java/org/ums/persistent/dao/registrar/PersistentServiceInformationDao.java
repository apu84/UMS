package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.ServiceInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.persistent.model.registrar.PersistentServiceInformation;

import javax.print.DocFlavor;
import javax.ws.rs.GET;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentServiceInformationDao extends ServiceInformationDaoDecorator {

  static String INSERT_ONE = "INSERT INTO EMP_SERVICE_INFO (EMPLOYEE_ID, DEPARTMENT, DESIGNATION, "
      + " EMPLOYMENT, JOINING_DATE, RESIGN_DATE, ROOM_NO, EXT_NO, ACADEMIC_INITIAL, STATUS, LAST_MODIFIED) "
      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";

  static String GET_ONE = "SELECT ID, EMPLOYEE_ID, DEPARTMENT, DESIGNATION, EMPLOYMENT, JOINING_DATE, RESIGN_DATE, "
      + "ROOM_NO, EXT_NO, ACADEMIC_INITIAL, STATUS FROM EMP_SERVICE_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_SERVICE_INFO SET DEPARTMENT = ?, DESIGNATION = ?, "
          + " EMPLOYMENT = ?, JOINING_DATE = ?, RESIGN_DATE = ?, ROOM_NO = ?, EXT_NO = ?, ACADEMIC_INITIAL = ?, STATUS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_SERVICE_INFO ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentServiceInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveServiceInformation(List<MutableServiceInformation> pMutableServiceInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getServiceInformationInsertParams(pMutableServiceInformation)).length;
  }

  private List<Object[]> getServiceInformationInsertParams(List<MutableServiceInformation> pMutableServiceInformation) {
    List<Object[]> params = new ArrayList<>();
    for(ServiceInformation serviceInformation : pMutableServiceInformation) {
      params
          .add(new Object[] {serviceInformation.getEmployeeId(), serviceInformation.getDepartment().getId(),
              serviceInformation.getDesignation().getId(), serviceInformation.getEmployment().getId(),
              serviceInformation.getJoiningDate(), serviceInformation.getResignDate(), serviceInformation.getRoomNo(),
              serviceInformation.getExtNo(), serviceInformation.getAcademicInitial(),
              serviceInformation.getCurrentStatus()});

    }
    return params;
  }

  @Override
  public List<ServiceInformation> getServiceInformation(String pEmployeeId) {
    String query = GET_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new PersistentServiceInformationDao.RoleRowMapper());
  }

  @Override
  public int updateServiceInformation(List<MutableServiceInformation> pMutableServiceInformation) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getServiceInformationUpdateParams(pMutableServiceInformation)).length;
  }

  private List<Object[]> getServiceInformationUpdateParams(List<MutableServiceInformation> pMutableServiceInformation) {
    List<Object[]> params = new ArrayList<>();
    for(ServiceInformation serviceInformation : pMutableServiceInformation) {
      params.add(new Object[] {serviceInformation.getDepartment().getId(), serviceInformation.getDesignation().getId(),
          serviceInformation.getEmployment().getId(), serviceInformation.getJoiningDate(),
          serviceInformation.getResignDate(), serviceInformation.getRoomNo(), serviceInformation.getExtNo(),
          serviceInformation.getAcademicInitial()});

    }
    return params;
  }

  @Override
  public int deleteServiceInformation(List<MutableServiceInformation> pMutableServiceInformation) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getServiceInformationDeleteParams(pMutableServiceInformation)).length;
  }

  private List<Object[]> getServiceInformationDeleteParams(List<MutableServiceInformation> pMutableServiceInformation) {
    List<Object[]> params = new ArrayList<>();
    for(ServiceInformation serviceInformation : pMutableServiceInformation) {
      params.add(new Object[] {serviceInformation.getId(), serviceInformation.getEmployeeId()});
    }
    return params;
  }

  class RoleRowMapper implements RowMapper<ServiceInformation> {
    @Override
    public ServiceInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentServiceInformation persistentServiceInformation = new PersistentServiceInformation();
      persistentServiceInformation.setId(resultSet.getInt("ID"));
      persistentServiceInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      persistentServiceInformation.setDepartmentId(resultSet.getString("DEPARTMENT"));
      persistentServiceInformation.setDesignationId(resultSet.getInt("DESIGNATION"));
      persistentServiceInformation.setEmploymentId(resultSet.getInt("EMPLOYMENT"));
      persistentServiceInformation.setJoiningDate(resultSet.getDate("JOINING_DATE"));
      persistentServiceInformation.setResignDate(resultSet.getDate("RESIGN_DATE"));
      persistentServiceInformation.setRoomNo(resultSet.getString("ROOM_NO"));
      persistentServiceInformation.setExtNo(resultSet.getString("EXT_NO"));
      persistentServiceInformation.setAcademicInitial(resultSet.getString("ACADEMIC_INITIAL"));
      persistentServiceInformation.setCurrentStatus(resultSet.getInt("STATUS"));
      return persistentServiceInformation;
    }
  }
}
