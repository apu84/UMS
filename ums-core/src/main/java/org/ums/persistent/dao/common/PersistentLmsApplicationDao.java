package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.common.LmsApplicationDaoDecorator;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentLmsApplication;
import org.ums.util.UmsUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class PersistentLmsApplicationDao extends LmsApplicationDaoDecorator {

  private String SELECT_ALL = "select * from lms_application ";
  private String SELECT_ONE = "select * from lms_application ";
  private String DELETE_ONE = "delete from lms_application ";
  private String UPDATE_ONE =
      "update lms_application set employee_id=:employeeId, type_id=:typeId, applied_on=:appliedOn, from_date=:fromDate, to_date=:toDate, reason=:reason,  app_status=:appStatus, total_days=:totalDays, last_modified=:lastModified";
  private String INSERT_ONE =
      "INSERT INTO LMS_APPLICATION (ID,EMPLOYEE_ID, TYPE_ID, APPLIED_ON, FROM_DATE, TO_DATE, REASON,APP_STATUS, TOTAL_DAYS, LAST_MODIFIED) VALUES (:id,:employeeId,:typeId,:appliedOn,:fromDate,:toDate,:reason,:appStatus, :totalDays,:lastModified)";

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentLmsApplicationDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  private Map<String, Object>[] getParamObjects(List<MutableLmsApplication> pMutableList) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableList.size()];
    for(int i = 0; i < pMutableList.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableList.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableLmsApplication pMutableLmsApplication) {
    Map parameter = new HashMap();
    parameter.put("id", pMutableLmsApplication.getId());
    parameter.put("employeeId", pMutableLmsApplication.getEmployeeId());
    parameter.put("typeId", pMutableLmsApplication.getLeaveTypeId());
    parameter.put("appliedOn", pMutableLmsApplication.getAppliedOn() == null ? new java.util.Date()
        : pMutableLmsApplication.getAppliedOn());
    parameter.put("fromDate", pMutableLmsApplication.getFromDate());
    parameter.put("toDate", pMutableLmsApplication.getToDate());
    parameter.put("reason", pMutableLmsApplication.getReason());
    parameter.put("totalDays", pMutableLmsApplication.getTotalDays());
    parameter.put("appStatus", pMutableLmsApplication.getApplicationStatus().getId());
    parameter.put("lastModified", UmsUtils.formatDate(new java.util.Date(), "YYYYMMDDHHMMSS"));
    return parameter;
  }

  @Override
  public List<LmsApplication> getAll() {
    String query = SELECT_ALL;
    return mNamedParameterJdbcTemplate.query(query, new LmsApplicationRowMapper());
  }

  @Override
  public List<LmsApplication> getPendingLmsApplication(String pEmployeeId) {
    String query = SELECT_ALL + " where app_status=2 and employee_id=:employeeId";
    Map parameterMap = new HashMap();
    parameterMap.put("employeeId", pEmployeeId);
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new LmsApplicationRowMapper());
  }

  @Override
  public LmsApplication get(Long pId) {
    String query = SELECT_ONE + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new LmsApplicationRowMapper());
  }

  @Override
  public LmsApplication validate(LmsApplication pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableLmsApplication pMutable) {
    String query = UPDATE_ONE + " where id=:id";
    return mNamedParameterJdbcTemplate.update(query, getInsertOrUpdateParameters(pMutable));
  }

  @Override
  public int update(List<MutableLmsApplication> pMutableList) {
    String query = "UPDATE_ONE" + " where id=:id";
    Map<String, Object>[] parameterObjects = getParamObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects).length;
  }

  @Override
  public int delete(MutableLmsApplication pMutable) {
    String query = DELETE_ONE + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pMutable.getId());
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int delete(List<MutableLmsApplication> pMutableList) {
    String query = DELETE_ONE + " where id=:id";
    Map<String, Object>[] parameterObjects = getParamObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects).length;
  }

  @Override
  public Long create(MutableLmsApplication pMutable) {
    String query = INSERT_ONE;
    pMutable.setId(mIdGenerator.getNumericId());
    mNamedParameterJdbcTemplate.update(query, getInsertOrUpdateParameters(pMutable));
    return pMutable.getId();
  }

  @Override
  public List<Long> create(List<MutableLmsApplication> pMutableList) {
    String query = INSERT_ONE;
    pMutableList.forEach(l->l.setId(mIdGenerator.getNumericId()));
    Map<String, Object>[] parameterObjects = getParamObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects);
    return pMutableList.stream().map(l-> l.getId()).collect(Collectors.toList());
  }

  @Override
  public boolean exists(Long pId) {
    String query = "select count(*) from lms_application " + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class) == 1 ? true : false;
  }

  @Override
  public List<LmsApplication> getLmsApplication(String pEmployeeId, int pYear) {
    String query =
        "select * from LMS_APPLICATION where EMPLOYEE_ID=:employeeId and (extract(year from APPLIED_ON)=:year or type_id in (select id from LMS_TYPE where DURATION_TYPE=1))";
    Map parameterMap = new HashMap();
    parameterMap.put("employeeId", pEmployeeId);
    parameterMap.put("year", pYear);
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new LmsApplicationRowMapper());
  }

  @Override
  public int updateApplicationStatus(Long pApplicationid, LeaveApplicationApprovalStatus pLeaveApplicationStatus) {
    String query =
        "update lms_application set app_status=:appStatus, last_modified=" + getLastModifiedSql() + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("appStatus", pLeaveApplicationStatus.getId());
    parameterMap.put("id", pApplicationid);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public List<LmsApplication> getApprovedApplicationsWithinDateRange(String pEmployeeId, String startDate,
      String endDate) {
    String query =
        "SELECT * "
            + "FROM LMS_APPLICATION "
            + "WHERE "
            + "  EMPLOYEE_ID = :employeeId AND APP_STATUS = 7 AND :startDate>= FROM_DATE AND :modifiedStartDate<= TO_DATE "
            + "  AND :endDate >= FROM_DATE AND :endDate <= TO_DATE";
    Map parameterMap = new HashMap();
    parameterMap.put("employeeId", pEmployeeId);
    try {
      parameterMap.put("startDate", UmsUtils.convertToDate(startDate, "dd-MM-yyyy"));
      parameterMap.put("endDate", UmsUtils.convertToDate(endDate, "dd-MM-yyyy"));
      java.util.Date modifiedStartDate = UmsUtils.incrementDate(UmsUtils.convertToDate(startDate, "dd-MM-yyyy"), 1);
      parameterMap.put("modifiedStartDate", modifiedStartDate);
      java.util.Date modifiedEndDate = UmsUtils.incrementDate(UmsUtils.convertToDate(endDate, "dd-MM-yyyy"), 1);
      parameterMap.put("modifiedEndDate", modifiedEndDate);

    } catch(Exception e) {
      e.printStackTrace();
    }
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new LmsApplicationRowMapper());
  }

  class LmsApplicationRowMapper implements RowMapper<LmsApplication> {
    @Override
    public LmsApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentLmsApplication application = new PersistentLmsApplication();
      application.setId(rs.getLong("id"));
      application.setEmployeeId(rs.getString("employee_id"));
      application.setLeaveTypeId(rs.getInt("type_id"));
      application.setAppliedOn(rs.getDate("applied_on"));
      application.setFromDate(rs.getDate("from_date"));
      application.setToDate(rs.getDate("to_date"));
      application.setReason(rs.getString("reason"));
      application.setLastModified(rs.getString("last_modified"));
      if(rs.getInt("app_status") != 0)
        application.setLeaveApplicationStatus(LeaveApplicationApprovalStatus.get(rs.getInt("app_status")));
      return application;
    }
  }

}
