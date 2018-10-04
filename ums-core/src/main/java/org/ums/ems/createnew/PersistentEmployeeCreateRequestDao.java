package org.ums.ems.createnew;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentEmployeeCreateRequestDao extends EmployeeCreateRequestDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_CREATE_REQUESTS(EMPLOYEE_ID, EMAIL, SALUTATION, EMPLOYEE_NAME, ACADEMIC_INITIAL, "
          + "DEPARTMENT, EMPLOYEE_TYPE, DESIGNATION, EMPLOYMENT_TYPE, JOINING_DATE, CREATE_ACCOUNT, REQUESTED_BY, "
          + "REQUESTED_ON, ACTION_TAKEN_BY, ACTION_TAKEN_ON, ACTION_STATUS, LAST_MODIFIED ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, "
          + "?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + " ) ";

  static String SELECT_ALL =
      "SELECT EMPLOYEE_ID, EMAIL, SALUTATION, EMPLOYEE_NAME, ACADEMIC_INITIAL, DEPARTMENT, EMPLOYEE_TYPE, "
          + "DESIGNATION, EMPLOYMENT_TYPE, JOINING_DATE, CREATE_ACCOUNT, REQUESTED_BY, REQUESTED_ON, ACTION_TAKEN_BY, "
          + "ACTION_TAKEN_ON, ACTION_STATUS, LAST_MODIFIED FROM EMP_CREATE_REQUESTS";

  static String UPDATE_ONE =
      "UPDATE EMP_CREATE_REQUESTS SET EMPLOYEE_ID = ?, EMAIL = ?, SALUTATION = ?, EMPLOYEE_NAME = ?, ACADEMIC_INITIAL = ?, "
          + " DEPARTMENT = ?, EMPLOYEE_TYPE = ?, DESIGNATION = ?, EMPLOYMENT_TYPE = ?, JOINING_DATE = ?, CREATE_ACCOUNT = ?, REQUESTED_BY = ?, "
          + " REQUESTED_ON = ?, ACTION_TAKEN_BY = ?, ACTION_TAKEN_ON = ?, ACTION_STATUS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_CREATE_REQUESTS ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_CREATE_REQUESTS ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentEmployeeCreateRequestDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public String create(MutableEmployeeCreateRequest pMutable) {
    mJdbcTemplate.update(INSERT_ONE, pMutable.getId(), pMutable.getEmail(), pMutable.getSalutation(),
        pMutable.getEmployeeName(), pMutable.getAcademicInitial(), pMutable.getDepartmentId(),
        pMutable.getEmployeeType(), pMutable.getDesignation(), pMutable.getEmploymentType(), pMutable.getJoiningDate(),
        pMutable.getCreateAccount(), pMutable.getRequestedBy(), pMutable.getRequestedOn(), pMutable.getActionTakenBy(),
        pMutable.getActionTakenOn(), pMutable.getActionStatus());
    return pMutable.getId();
  }

  @Override
  public List<EmployeeCreateRequest> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new PersistentEmployeeCreateRequestDao.EmployeeCreateRequestRowMapper());
  }

  @Override
  public List<EmployeeCreateRequest> getAll(Integer pActionStatus) {
    return mJdbcTemplate.query(SELECT_ALL + " WHERE ACTION_STATUS = ? ", new Object[] {pActionStatus},
        new PersistentEmployeeCreateRequestDao.EmployeeCreateRequestRowMapper());
  }

  @Override
  public EmployeeCreateRequest get(String pEmployeeId) {
    String query = SELECT_ALL + " WHERE EMPLOYEE_ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId},
        new PersistentEmployeeCreateRequestDao.EmployeeCreateRequestRowMapper());
  }

  @Override
  public int update(MutableEmployeeCreateRequest pMutable) {
    String query = UPDATE_ONE + " WHERE EMPLOYEE_ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmail(), pMutable.getSalutation(),
        pMutable.getEmployeeName(), pMutable.getAcademicInitial(), pMutable.getDesignation(),
        pMutable.getEmployeeType(), pMutable.getDesignation(), pMutable.getEmployeeType(), pMutable.getJoiningDate(),
        pMutable.getCreateAccount(), pMutable.getRequestedBy(), pMutable.getRequestedOn(), pMutable.getActionTakenBy(),
        pMutable.getActionTakenOn(), pMutable.getActionStatus(), pMutable.getId());
  }

  @Override
  public int delete(MutableEmployeeCreateRequest pMutable) {
    String query = DELETE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class EmployeeCreateRequestRowMapper implements RowMapper<EmployeeCreateRequest> {

    @Override
    public EmployeeCreateRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentEmployeeCreateRequest persistentEmployeeCreateRequest = new PersistentEmployeeCreateRequest();
      persistentEmployeeCreateRequest.setId(rs.getString("EMPLOYEE_ID"));
      persistentEmployeeCreateRequest.setEmail(rs.getString("EMAIL"));
      persistentEmployeeCreateRequest.setSalutation(rs.getInt("SALUTATION"));
      persistentEmployeeCreateRequest.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
      persistentEmployeeCreateRequest.setAcademicInitial(rs.getString("ACADEMIC_INITIAL"));
      persistentEmployeeCreateRequest.setDepartmentId(rs.getString("DEPARTMENT"));
      persistentEmployeeCreateRequest.setEmployeeType(rs.getInt("EMPLOYEE_TYPE"));
      persistentEmployeeCreateRequest.setDesignation(rs.getInt("DESIGNATION"));
      persistentEmployeeCreateRequest.setEmploymentType(rs.getInt("EMPLOYMENT_TYPE"));
      persistentEmployeeCreateRequest.setJoiningDate(rs.getDate("JOINING_DATE"));
      persistentEmployeeCreateRequest.setCreateAccount(rs.getInt("CREATE_ACCOUNT"));
      persistentEmployeeCreateRequest.setRequestedBy(rs.getString("REQUESTED_BY"));
      persistentEmployeeCreateRequest.setRequestedOn(rs.getDate("REQUESTED_ON"));
      persistentEmployeeCreateRequest.setActionTakenBy(rs.getString("ACTION_TAKEN_BY"));
      persistentEmployeeCreateRequest.setActionTakenOn(rs.getDate("ACTION_TAKEN_ON"));
      persistentEmployeeCreateRequest.setActionStatus(rs.getInt("ACTION_STATUS"));
      persistentEmployeeCreateRequest.setLastModified("LAST_MODIFIED");
      return persistentEmployeeCreateRequest;
    }
  }
}
