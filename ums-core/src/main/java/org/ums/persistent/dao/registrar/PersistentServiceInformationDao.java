package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.ServiceInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.persistent.model.registrar.PersistentServiceInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentServiceInformationDao extends ServiceInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_SERVICE_INFO (EMPLOYEE_ID, EMPLOYMENT_TYPE, DESIGNATION, DEPT_OFFICE, JOB_JOINING_DATE, JOB_CONTRACTUAL_DATE, JOB_PROBATION_DATE, JOB_PERMANENT_DATE, RESIGN_DATE, EXT_NO, SHORT_NAME, ROOM_NO) VALUES (? ,? ,?, ?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY') ,TO_DATE(?, 'DD/MM/YYYY') ,TO_DATE(?, 'DD/MM/YYYY') ,TO_DATE(?, 'DD/MM/YYYY') ,?, ? ,?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentServiceInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveServiceInformation(List<MutableServiceInformation> pMutableServiceInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeServiceInformationParams(pMutableServiceInformation)).length;
  }

  private List<Object[]> getEmployeeServiceInformationParams(List<MutableServiceInformation> pMutableServiceInformation) {
    List<Object[]> params = new ArrayList<>();
    for(ServiceInformation serviceInformation : pMutableServiceInformation) {
      params.add(new Object[] {serviceInformation.getEmployeeId(), serviceInformation.getEmploymentType(),
          serviceInformation.getDesignation(), serviceInformation.getDeptOffice(), serviceInformation.getJoiningDate(),
          serviceInformation.getJobContractualDate(), serviceInformation.getJobProbationDate(),
          serviceInformation.getJobPermanentDate(), serviceInformation.getJobTerminationDate(),
          serviceInformation.getExtNo(), serviceInformation.getShortName(), serviceInformation.getRoomNo()});

    }
    return params;
  }

  class RoleRowMapper implements RowMapper<ServiceInformation> {
    @Override
    public ServiceInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableServiceInformation employeeInformation = new PersistentServiceInformation();
      employeeInformation.setEmployeeId(resultSet.getInt("employee_id"));
      employeeInformation.setEmploymentType(resultSet.getInt("employment_id"));
      employeeInformation.setDesignation(resultSet.getInt("designation"));
      employeeInformation.setDeptOffice(resultSet.getInt("dept_office"));
      employeeInformation.setJoiningDate(resultSet.getString("job_joining_date"));
      employeeInformation.setJoiningDate(resultSet.getString("job_contractual_date"));
      employeeInformation.setJoiningDate(resultSet.getString("job_probation_date"));
      employeeInformation.setJobPermanentDate(resultSet.getString("job_permanent_date"));
      employeeInformation.setJobTerminationDate(resultSet.getString("resign_date"));
      employeeInformation.setExtNo(resultSet.getInt("ext_no"));
      employeeInformation.setShortName(resultSet.getString("short_name"));
      employeeInformation.setRoomNo(resultSet.getString("room_no"));
      return employeeInformation;
    }
  }
}
