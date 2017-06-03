package org.ums.persistent.dao.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.LmsAppStatusDaoDecorator;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.enums.common.RoleType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentLmsAppStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-
 * <p>
 * E-Morshed on 07-May-17.
 */

/*
 * Instructions: Row_number is needed for pagination. But, if there is no row_number, the
 * row_mapper() shows error. So, till any solution is found please, return a row_number.
 */
public class PersistentLmsAppStatusDao extends LmsAppStatusDaoDecorator {

  private static final Logger mLogger = LoggerFactory.getLogger(PersistentLmsAppStatusDao.class);

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  private String SELECT_ALL = "select *,ROWNUM row_number from lms_app_status";
  private String INSERT_ONE =
      "insert into LMS_APP_STATUS(ID,APP_ID,ACTION_TAKEN_ON, ACTION_TAKEN_BY, COMMENTS, ACTION_STATUS, LAST_MODIFIED) values(?,?,sysdate,?,?,?,"
          + getLastModifiedSql() + ")";
  private String UPDATE_ONE =
      "update lms_app_status set app_id=?,action_taken_on=?, action_taken_by=?,comments=?,action_status=?, last_modified="
          + getLastModifiedSql() + "  where id=?";
  private String DELETE_ONE = "delete from lms_app_status where id=?";

  public PersistentLmsAppStatusDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<LmsAppStatus> getAll() {
    String query = "select a.*, rownum row_number from (select * from LMS_APP_STATUS)a";
    return mJdbcTemplate.query(query, new LmsAppStatusRowMapper());
  }

  @Override
  public LmsAppStatus get(Long pId) {
    String query = "select a.*, rownum row_number from (select * from LMS_APP_STATUS where id=?)a";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new LmsAppStatusRowMapper());
  }

  @Override
  public LmsAppStatus validate(LmsAppStatus pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableLmsAppStatus pMutable) {
    String query = UPDATE_ONE;
    return mJdbcTemplate.update(query, pMutable.getLmsApplication().getId(), pMutable.getActionTakenOn(), pMutable
        .getActionTakenBy().getId(), pMutable.getComments(), pMutable.getActionStatus().getId(), pMutable.getId());
  }

  @Override
  public int update(List<MutableLmsAppStatus> pMutableList) {
    return super.update(pMutableList);
  }

  private List<Object[]> getUpdateParams(List<MutableLmsAppStatus> pAppStatus) {
    List<Object[]> params = new ArrayList<>();
    for(LmsAppStatus status : pAppStatus) {
      params.add(new Object[] {status.getLmsApplication().getId(), status.getActionTakenOn(),
          status.getActionTakenBy().getId(), status.getComments(), status.getActionStatus().getId(), status.getId()});
    }
    return params;
  }

  @Override
  public int delete(MutableLmsAppStatus pMutable) {
    String query = DELETE_ONE;
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int delete(List<MutableLmsAppStatus> pMutableList) {
    String query = DELETE_ONE;
    return mJdbcTemplate.update(query, getDeleteParams(pMutableList));
  }

  private List<Object[]> getDeleteParams(List<MutableLmsAppStatus> pAppStatus) {
    List<Object[]> params = new ArrayList<>();
    for(LmsAppStatus status : pAppStatus) {
      params.add(new Object[] {status.getId()});
    }
    return params;
  }

  @Override
  public Long create(MutableLmsAppStatus pMutable) {
    String query = INSERT_ONE;
    Long appId = mIdGenerator.getNumericId();
    mJdbcTemplate.update(query, appId, pMutable.getLmsAppId(), pMutable.getActionTakenBy().getId(),
        pMutable.getComments(), pMutable.getActionStatus().getId());
    return appId;
  }

  @Override
  public List<Long> create(List<MutableLmsAppStatus> pMutableList) {
    String query = INSERT_ONE;
    mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        LmsAppStatus status = pMutableList.get(i);
        ps.setLong(1, mIdGenerator.getNumericId());
        ps.setLong(2, status.getLmsAppId());
        ps.setString(3, status.getActionTakenById());
        ps.setString(4, status.getComments());
        ps.setInt(5, status.getActionStatus().getId());
      }

      @Override
      public int getBatchSize() {
        return 0;
      }
    });
    return null;
  }

  private List<Object[]> getCreateParams(List<MutableLmsAppStatus> pAppStatus) {
    List<Object[]> params = new ArrayList<>();
    for(LmsAppStatus status : pAppStatus) {
      params.add(new Object[] {status.getLmsApplication().getId(), status.getActionTakenOn(),
          status.getActionTakenBy().getId(), status.getComments(), status.getActionStatus().getId()});
    }
    return params;
  }

  @Override
  public boolean exists(Long pId) {
    return get(pId) == null ? false : true;
  }

  @Override
  public List<LmsAppStatus> getAppStatus(Long pApplicationId) {
    String query =
        " select a.*, rownum row_number from (select * from LMS_APP_STATUS where APP_ID=? ORDER BY  ACTION_TAKEN_ON)a";
    return mJdbcTemplate.query(query, new Object[] {pApplicationId}, new LmsAppStatusRowMapper());
  }

  @Override
  public List<LmsAppStatus> getPendingApplications(String pEmployeeId) {
    String query =
        "SELECT "
            + "  a.*, "
            + "  rownum row_number "
            + "FROM (SELECT * "
            + "      FROM LMS_APP_STATUS "
            + "      WHERE (APP_ID,ACTION_STATUS) IN (SELECT id,APP_STATUS "
            + "                       FROM LMS_APPLICATION "
            + "                       WHERE  EMPLOYEE_ID = ? and (app_status!=3 and app_status!=5 and app_status!=6 and app_status!=7)) "
            + "      ORDER BY action_taken_on) a";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new LmsAppStatusRowMapper());
  }

  @Override
  public List<LmsAppStatus> getPendingApplications(LeaveApplicationApprovalStatus pLeaveApplicationStatus, Role pRole,
      User pUser, int pageNumber, int pageSize) {
    String query = "";
    if(pRole.getId() == RoleType.DEPT_HEAD.getId()) {
      query =
          "SELECT * "
              + "FROM ( "
              + "  SELECT "
              + "    a.*, "
              + "    ROWNUM row_number "
              + "  FROM ( "
              + "         SELECT * "
              + "         FROM LMS_APP_STATUS "
              + "         WHERE  (APP_ID,Action_status) IN ( "
              + "           SELECT ID,APP_STATUS "
              + "           FROM LMS_APPLICATION, EMPLOYEES "
              + "           WHERE APP_STATUS=? AND  EMPLOYEES.DEPT_OFFICE = ? AND EMPLOYEES.EMPLOYEE_ID = LMS_APPLICATION.EMPLOYEE_ID "
              + "         ) " + "         ORDER BY ACTION_TAKEN_ON) a " + "  WHERE ROWNUM < ((" + pageNumber + " * "
              + pageSize + ") + 1)) " + "WHERE row_number >= (((" + pageNumber + " - 1) * " + pageSize + ") + 1)";
      return mJdbcTemplate.query(query, new Object[] {pLeaveApplicationStatus.getId(), pUser.getDepartment().getId()},
          new LmsAppStatusRowMapper());
    }
    else {
      query =
          "SELECT * "
              + "FROM ( "
              + "  SELECT "
              + "    a.*, "
              + "    ROWNUM row_number "
              + "  FROM ( "
              + "         SELECT * "
              + "         FROM LMS_APP_STATUS "
              + "         WHERE (APP_ID,ACTION_STATUS) IN (SELECT ID, APP_STATUS FROM LMS_APPLICATION WHERE APP_STATUS=?) "
              + "         ORDER BY ACTION_TAKEN_ON) a " + "  WHERE ROWNUM < ((" + pageNumber + " * " + pageSize
              + ") + 1)) " + "WHERE row_number >= (((" + pageNumber + " - 1) * " + pageSize + ") + 1)";
      return mJdbcTemplate.query(query, new Object[] {pLeaveApplicationStatus.getId()}, new LmsAppStatusRowMapper());
    }

  }

  @Override
  public List<LmsAppStatus> getPendingApplications(LeaveApplicationApprovalStatus pLeaveApplicationApprovalStatus,
      User pUser, Role pRole) {
    String query = "";
    if(pRole.getId() == RoleType.DEPT_HEAD.getId()) {
      query =
          "SELECT * " + "FROM ( " + "  SELECT " + "    a.*, " + "    ROWNUM row_number " + "  FROM ( "
              + "         SELECT * " + "         FROM LMS_APP_STATUS "
              + "         WHERE ACTION_STATUS = ? AND APP_ID IN ( " + "           SELECT ID "
              + "           FROM LMS_APPLICATION, EMPLOYEES "
              + "           WHERE  EMPLOYEES.DEPT_OFFICE = ? AND EMPLOYEES.EMPLOYEE_ID = LMS_APPLICATION.EMPLOYEE_ID "
              + "         ) " + "         ORDER BY ACTION_TAKEN_ON) a)";
      return mJdbcTemplate.query(query, new Object[] {pLeaveApplicationApprovalStatus.getId(),
          pUser.getDepartment().getId()}, new LmsAppStatusRowMapper());
    }
    else {
      query =
          "SELECT * " + "FROM ( " + "  SELECT " + "    a.*, " + "    ROWNUM row_number " + "  FROM ( "
              + "         SELECT * " + "         FROM LMS_APP_STATUS " + "         WHERE ACTION_STATUS = ? "
              + "         ORDER BY ACTION_TAKEN_ON) a)";
      return mJdbcTemplate.query(query, new Object[] {pLeaveApplicationApprovalStatus.getId()},
          new LmsAppStatusRowMapper());

    }
  }

  @Override
  public LmsAppStatus getLatestStatusOfTheApplication(Long pApplicationId) {
    String query =
        "select a.*, rownum row_number from (select * from LMS_APP_STATUS where APP_ID=? ORDER BY  ACTION_STATUS DESC )a where ROWNUM=1";
    return mJdbcTemplate.queryForObject(query, new Object[] {pApplicationId}, new LmsAppStatusRowMapper());
  }

  class LmsAppStatusRowMapper implements RowMapper<LmsAppStatus> {
    @Override
    public LmsAppStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentLmsAppStatus status = new PersistentLmsAppStatus();
      status.setId(rs.getLong("id"));
      status.setLmsApplicationId(rs.getLong("app_id"));
      status.setActionTakenOn(rs.getDate("action_taken_on"));
      status.setActionTakenById(rs.getString("action_taken_by"));
      status.setComments(rs.getString("comments"));
      if(rs.getInt("action_status") != 0)
        status.setActionStatus(LeaveApplicationApprovalStatus.get(rs.getInt("action_status")));
      if(rs.getInt("row_number") != 0)
        status.setRowNumber(rs.getInt("row_number"));
      status.setLastModified(rs.getString("last_modified"));
      return status;
    }
  }
}
