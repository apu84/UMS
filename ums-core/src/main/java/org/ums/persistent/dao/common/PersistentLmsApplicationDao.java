package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.LmsApplicationDaoDecorator;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.persistent.model.common.PersistentLmsApplication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class PersistentLmsApplicationDao extends LmsApplicationDaoDecorator {

  private String SELECT_ALL = "select * from lms_application ";
  private String SELECT_ONE = "select * from lms_application ";
  private String DELETE_ONE = "delete from lms_application ";
  private String UPDATE_ONE =
      "update lms_application set employee_id=?, type_id=?, applied_on=?, from_date=?, to_date=?, reason=?, last_modified="
          + getLastModifiedSql() + " ";
  private String INSERT_ONE =
      "INSERT INTO LMS_APPLICATION (EMPLOYEE_ID, TYPE_ID, APPLIED_ON, FROM_DATE, TO_DATE, REASON, LAST_MODIFIED VALUES (?,?,?,?,?,?,"
          + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;

  public PersistentLmsApplicationDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<LmsApplication> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new LmsApplicationRowMapper());
  }

  @Override
  public LmsApplication get(Integer pId) {
    String query = SELECT_ONE + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new LmsApplicationRowMapper());
  }

  @Override
  public LmsApplication validate(LmsApplication pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableLmsApplication pMutable) {
    String query = UPDATE_ONE + " where id=?";
    return mJdbcTemplate.update(query, pMutable.getEmployee().getId(), pMutable.getLmsType().getId(),
        pMutable.getAppliedOn(), pMutable.getFromDate(), pMutable.getToDate(), pMutable.getReason(), pMutable.getId());
  }

  @Override
  public int update(List<MutableLmsApplication> pMutableList) {
    String query = "UPDATE_ONE" + " where id=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutableList)).length;
  }

  private List<Object[]> getUpdateParams(List<MutableLmsApplication> pLmsApplications) {
    List<Object[]> params = new ArrayList<>();
    for(LmsApplication application : pLmsApplications) {
      params.add(new Object[] {application.getEmployee().getId(), application.getLmsType().getId(),
          application.getAppliedOn(), application.getFromDate(), application.getToDate(), application.getReason(),
          application.getId()});
    }
    return params;
  }

  @Override
  public int delete(MutableLmsApplication pMutable) {
    String query = DELETE_ONE + " where id=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  private List<Object[]> getDeleteParams(List<MutableLmsApplication> pLmsApplications) {
    List<Object[]> params = new ArrayList<>();
    for(LmsApplication application : pLmsApplications) {
      params.add(new Object[] {application.getEmployee().getId()});
    }
    return params;
  }

  @Override
  public int delete(List<MutableLmsApplication> pMutableList) {
    String query = DELETE_ONE + " where id=?";
    return mJdbcTemplate.batchUpdate(query, getDeleteParams(pMutableList)).length;
  }

  @Override
  public Integer create(MutableLmsApplication pMutable) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutable.getEmployee().getId(), pMutable.getLmsType().getId(),
        pMutable.getAppliedOn(), pMutable.getFromDate(), pMutable.getToDate(), pMutable.getReason());
  }

  @Override
  public List<Integer> create(List<MutableLmsApplication> pMutableList) {
    String query = INSERT_ONE;
    return Arrays.asList(mJdbcTemplate.batchUpdate(query, getCreateParams(pMutableList)).length);
  }

  private List<Object[]> getCreateParams(List<MutableLmsApplication> pLmsApplications) {
    List<Object[]> params = new ArrayList<>();
    for(LmsApplication application : pLmsApplications) {
      params.add(new Object[] {application.getEmployee().getId(), application.getLmsType().getId(),
          application.getAppliedOn(), application.getFromDate(), application.getToDate(), application.getReason()});
    }
    return params;
  }

  @Override
  public boolean exists(Integer pId) {
    String query = SELECT_ONE + " where id=?";
    Integer size = mJdbcTemplate.queryForObject(query, Integer.class, pId);
    return (size != null && size > 0) ? true : false;
  }

  class LmsApplicationRowMapper implements RowMapper<LmsApplication> {
    @Override
    public LmsApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentLmsApplication application = new PersistentLmsApplication();
      application.setId(rs.getInt("id"));
      application.setEmployeeId(rs.getString("employee_id"));
      application.setLeaveTypeId(rs.getInt("type_id"));
      application.setAppliedOn(rs.getDate("applied_on"));
      application.setFromDate(rs.getDate("from_date"));
      application.setToDate(rs.getDate("to_date"));
      application.setReason(rs.getString("reason"));
      application.setLastModified(rs.getString("last_modified"));
      return application;
    }
  }

}
