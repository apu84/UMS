package org.ums.persistent.dao.registrar.employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.employee.EmployeeInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.employee.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableEmployeeInformation;
import org.ums.persistent.model.registrar.employee.PersistentEmployeeInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentEmployeeInformationDao extends EmployeeInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMPLOYEES1 (EMPLOYEE_ID, EMPLOYMENT_TYPE, DESIGNATION, DEPT_OFFICE, JOB_JOINING_DATE, JOB_PERMANENT_DATE, RESIGN_DATE, EXT_NO, SHORT_NAME, ROOM_NO) VALUES (? ,? ,?, ?, ? ,? ,? ,? ,? ,?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentEmployeeInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveEmployeeInformation(MutableEmployeeInformation pMutableEmployeeInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableEmployeeInformation.getEmployeeId(),
        pMutableEmployeeInformation.getEmploymentType(), pMutableEmployeeInformation.getDesignation(),
        pMutableEmployeeInformation.getDeptOffice(), pMutableEmployeeInformation.getJoiningDate(),
        pMutableEmployeeInformation.getJobPermanentDate(), pMutableEmployeeInformation.getJobTerminationDate(),
        pMutableEmployeeInformation.getExtNo(), pMutableEmployeeInformation.getShortName(),
        pMutableEmployeeInformation.getRoomNo());
  }

  class RoleRowMapper implements RowMapper<EmployeeInformation> {
    @Override
    public EmployeeInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableEmployeeInformation employeeInformation = new PersistentEmployeeInformation();
      employeeInformation.setEmployeeId(resultSet.getInt("employee_id"));
      employeeInformation.setEmploymentType(resultSet.getString("employment_id"));
      employeeInformation.setDesignation(resultSet.getInt("designation"));
      employeeInformation.setDeptOffice(resultSet.getString("dept_office"));
      employeeInformation.setJoiningDate(resultSet.getString("job_joining_date"));
      employeeInformation.setJobPermanentDate(resultSet.getString("job_permanent_date"));
      employeeInformation.setJobTerminationDate(resultSet.getString("resign_date"));
      employeeInformation.setExtNo(resultSet.getInt("ext_no"));
      employeeInformation.setShortName(resultSet.getString("short_name"));
      employeeInformation.setRoomNo(resultSet.getString("room_no"));
      return employeeInformation;
    }
  }
}
