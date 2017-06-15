package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.EmployeeDaoDecorator;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.persistent.model.PersistentEmployee;

public class PersistentEmployeeDao extends EmployeeDaoDecorator {

  static String SELECT_ALL = "SELECT EMPLOYEE_ID, SHORT_NAME, EMPLOYEE_NAME,DESIGNATION,"
      + "EMPLOYMENT_TYPE,DEPT_OFFICE,FATHER_NAME,MOTHER_NAME,BIRTH_DATE,GENDER,BLOOD_GROUP,"
      + "PRESENT_ADDRESS,PERMANENT_ADDRESS,MOBILE_NUMBER,PHONE_NUMBER,EMAIL_ADDRESS,"
      + "JOINING_DATE,JOB_PERMANENT_DATE,STATUS,LAST_MODIFIED FROM EMPLOYEES";

  static String INSERT_ONE =
      "INSERT INTO EMPLOYEES (EMPLOYEE_ID, EMPLOYEE_NAME, SHORT_NAME, DESIGNATION,EMPLOYMENT_TYPE,DEPT_OFFICE,"
          + "FATHER_NAME,MOTHER_NAME,BIRTH_DATE,GENDER,BLOOD_GROUP,PRESENT_ADDRESS,PERMANENT_ADDRESS,"
          + "MOBILE_NUMBER,PHONE_NUMBER,EMAIL_ADDRESS,JOINING_DATE,JOB_PERMANENT_DATE,STATUS,LAST_MODIFIED) "
          + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," + getLastModifiedSql() + ")";

  static String UPDATE_ONE =
      "UPDATE EMPLOYEES SET EMPLOYEE_NAME=?, SHORT_NAME = ?, DESIGNATION=?,EMPLOYMENT_TYPE=?,DEPT_OFFICE=?,"
          + "FATHER_NAME=?,MOTHER_NAME=?,BIRTH_DATE=?,GENDER=?,BLOOD_GROUP=?,PRESENT_ADDRESS=?,PERMANENT_ADDRESS=?,"
          + "MOBILE_NUMBER=?,PHONE_NUMBER=?,EMAIL_ADDRESS=?,JOINING_DATE=?,JOB_PERMANENT_DATE=?,STATUS=? ,LAST_MODIFIED="
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMPLOYEES ";

  String EXIST_EMAIL = "SELECT COUNT(EMPLOYEE_ID) EXIST FROM EMPLOYEES ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentEmployeeDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public boolean existenceByEmail(String pEmailAddress) {
    String query = EXIST_EMAIL + " WHERE EMAIL_ADDRESS = ?";
    return mJdbcTemplate.queryForObject(query, Boolean.class, pEmailAddress);
  }

  @Override
  public Employee get(String pId) {
    String query = SELECT_ALL + " WHERE EMPLOYEE_ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new EmployeeRowMapper());
  }

  @Override
  public Employee getByEmail(String pEmailAddress) {
    String query = SELECT_ALL + " WHERE EMAIL_ADDRESS = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmailAddress}, new EmployeeRowMapper());
  }

  @Override
  public List<Employee> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new EmployeeRowMapper());
  }

  @Override
  public int update(MutableEmployee pMutable) {
    String query = UPDATE_ONE + " WHERE EMPLOYEE_ID=? ";
    return mJdbcTemplate.update(query, pMutable.getEmployeeName(), pMutable.getShortName(), pMutable.getDesignation(),
        pMutable.getEmploymentType(), pMutable.getDepartment().getId(), pMutable.getFatherName(),
        pMutable.getMotherName(), pMutable.getBirthDate(), pMutable.getGender(), pMutable.getBloodGroup(),
        pMutable.getPresentAddress(), pMutable.getPermanentAddress(), pMutable.getMobileNumber(),
        pMutable.getPhoneNumber(), pMutable.getEmailAddress(), pMutable.getJoiningDate(),
        pMutable.getJobPermanentDate(), pMutable.getStatus(), pMutable.getId());
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
    mJdbcTemplate.update(INSERT_ONE, pMutable.getId(), pMutable.getEmployeeName(), pMutable.getShortName(),
        pMutable.getDesignation(), pMutable.getEmploymentType(), pMutable.getDepartment().getId(),
        pMutable.getFatherName(), pMutable.getMotherName(), pMutable.getBirthDate(), pMutable.getGender(),
        pMutable.getBloodGroup(), pMutable.getPresentAddress(), pMutable.getPermanentAddress(),
        pMutable.getMobileNumber(), pMutable.getPhoneNumber(), pMutable.getEmailAddress(), pMutable.getJoiningDate(),
        pMutable.getJobPermanentDate(), pMutable.getStatus());
    return pMutable.getId();
  }

  class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet pResultSet, int pI) throws SQLException {

      PersistentEmployee persistentEmployee = new PersistentEmployee();
      persistentEmployee.setId(pResultSet.getString("EMPLOYEE_ID"));
      persistentEmployee.setEmployeeName(pResultSet.getString("EMPLOYEE_NAME"));
      persistentEmployee.setShortName(pResultSet.getString("SHORT_NAME"));
      persistentEmployee.setDesignation(pResultSet.getInt("DESIGNATION"));
      persistentEmployee.setEmploymentType(pResultSet.getString("EMPLOYMENT_TYPE"));
      persistentEmployee.setDepartmentId(pResultSet.getString("DEPT_OFFICE"));
      persistentEmployee.setFatherName(pResultSet.getString("FATHER_NAME"));
      persistentEmployee.setMotherName(pResultSet.getString("MOTHER_NAME"));
      persistentEmployee.setBirthDate(pResultSet.getDate("BIRTH_DATE"));
      persistentEmployee.setGender(pResultSet.getString("GENDER"));
      persistentEmployee.setBloodGroup(pResultSet.getString("BLOOD_GROUP"));
      persistentEmployee.setPresentAddress(pResultSet.getString("PRESENT_ADDRESS"));
      persistentEmployee.setPermanentAddress(pResultSet.getString("PERMANENT_ADDRESS"));
      persistentEmployee.setMobileNumber(pResultSet.getString("MOBILE_NUMBER"));
      persistentEmployee.setPhoneNumber(pResultSet.getString("PHONE_NUMBER"));
      persistentEmployee.setEmailAddress(pResultSet.getString("EMAIL_ADDRESS"));
      persistentEmployee.setJoiningDate(pResultSet.getDate("JOINING_DATE"));
      persistentEmployee.setJobParmanentDate(pResultSet.getDate("JOB_PERMANENT_DATE"));
      persistentEmployee.setStatus(pResultSet.getInt("STATUS"));
      persistentEmployee.setLastModified(pResultSet.getString("LAST_MODIFIED"));

      AtomicReference<Employee> atomicReference = new AtomicReference<>(persistentEmployee);
      return atomicReference.get();

    }
  }
}
