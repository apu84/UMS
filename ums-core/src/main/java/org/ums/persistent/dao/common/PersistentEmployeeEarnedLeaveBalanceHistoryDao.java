package org.ums.persistent.dao.common;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.common.EmployeeEarnedLeaveBalanceDaoDecorator;
import org.ums.decorator.common.EmployeeEarnedLeaveBalanceHistoryDaoDecorator;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalanceHistory;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.enums.common.LeaveBalanceType;
import org.ums.enums.common.LeaveMigrationType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentEmployeeEarnedLeaveBalanceHistory;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersistentEmployeeEarnedLeaveBalanceHistoryDao extends EmployeeEarnedLeaveBalanceHistoryDaoDecorator {

  private String SELECT_ALL = "select * from EMP_EL_HISTORY";
  private String INSERT_ONE =
      "INSERT INTO EMP_EL_HISTORY(ID, MIGRATION_TYPE, CHANGED_ON, DEBIT, CREDIT, BALANCE, BALANCE_TYPE, LAST_MODIFIED) VALUES (:id, :migrationType, :changedOn, :debit, :credit, :balance, :balanceType, :lastModified)";
  private String UPDATE_ONE =
      "UPDATE EMP_EL_HISTORY SET MIGRATION_TYPE=:migrationType, CHANGED_ON=:changedOn, DEBIT=:debit, CREDIT=:credit, BALANCE=:balance, BALANCE_TYPE=:balanceType, LAST_MODIFIED=:lastModified WHERE ID=:id";
  private String DELETE_ONE = "DELETE FROM EMP_EL_HISTORY WHERE ID=:id";

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentEmployeeEarnedLeaveBalanceHistoryDao(JdbcTemplate mJdbcTemplate,
      NamedParameterJdbcTemplate mNamedParameterJdbcTemplate, IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mNamedParameterJdbcTemplate = mNamedParameterJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  @Override
  public List<EmployeeEarnedLeaveBalanceHistory> getAll() {
    String query = SELECT_ALL;
    return mNamedParameterJdbcTemplate.query(query, new EmployeeEarnedLeaveBalanceHistoryRowMapper());
  }

  @Override
  public EmployeeEarnedLeaveBalanceHistory get(Long pId) {
    String query = SELECT_ALL + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new EmployeeEarnedLeaveBalanceHistoryRowMapper());
  }

  @Override
  public EmployeeEarnedLeaveBalanceHistory validate(EmployeeEarnedLeaveBalanceHistory pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableEmployeeEarnedLeaveBalanceHistory pMutable) {
    String query = UPDATE_ONE;
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int update(List<MutableEmployeeEarnedLeaveBalanceHistory> pMutableList) {
    String query = UPDATE_ONE;
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects).length;
  }

  @Override
  public int delete(MutableEmployeeEarnedLeaveBalanceHistory pMutable) {
    String query = DELETE_ONE;
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int delete(List<MutableEmployeeEarnedLeaveBalanceHistory> pMutableList) {
    String query = DELETE_ONE;
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects).length;
  }

  @Override
  public Long create(MutableEmployeeEarnedLeaveBalanceHistory pMutable) {
    return create(Arrays.asList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableEmployeeEarnedLeaveBalanceHistory> pMutableList) {
	  String query = INSERT_ONE;
	  Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects);
    return pMutableList.stream().map(i->i.getId()).collect(Collectors.toList());
  }

  @Override
  public boolean exists(Long pId) {
    try {
      EmployeeEarnedLeaveBalanceHistory history = get(pId);
      return true;
    } catch(EmptyResultDataAccessException pE) {
      return false;
    }
  }

  private Map<String, Object>[] getParameterObjects(
      List<MutableEmployeeEarnedLeaveBalanceHistory> pMutableEmployeeEarnedLeaveBalanceHistories) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableEmployeeEarnedLeaveBalanceHistories.size()];
    for(int i = 0; i < pMutableEmployeeEarnedLeaveBalanceHistories.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableEmployeeEarnedLeaveBalanceHistories.get(i));
    }

    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(
      MutableEmployeeEarnedLeaveBalanceHistory pMutableEmployeeEarnedLeaveBalanceHistory) {
    Map parameter = new HashMap();
    parameter.put("id", pMutableEmployeeEarnedLeaveBalanceHistory.getId());
    parameter.put("employeeId", pMutableEmployeeEarnedLeaveBalanceHistory.getEmployeeId());
    parameter.put("migrationType", pMutableEmployeeEarnedLeaveBalanceHistory.getLeaveMigrationType().getId());
    parameter.put("changedOn", pMutableEmployeeEarnedLeaveBalanceHistory.getChangedOn());
    parameter.put("debit", pMutableEmployeeEarnedLeaveBalanceHistory.getDebit());
    parameter.put("credit", pMutableEmployeeEarnedLeaveBalanceHistory.getCredit());
    parameter.put("balance", pMutableEmployeeEarnedLeaveBalanceHistory.getBalance());
    parameter.put("balanceType", pMutableEmployeeEarnedLeaveBalanceHistory.getBalanceType().getId());
    parameter.put("lastModified", UmsUtils.formatDate(new java.util.Date(), "YYYYMMDDHHMMSS"));

    return parameter;
  }

  class EmployeeEarnedLeaveBalanceHistoryRowMapper implements RowMapper<EmployeeEarnedLeaveBalanceHistory> {
    @Override
    public EmployeeEarnedLeaveBalanceHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableEmployeeEarnedLeaveBalanceHistory history = new PersistentEmployeeEarnedLeaveBalanceHistory();
      history.setId(rs.getLong("id"));
      history.setEmployeeId(rs.getString("employee_id"));
      history.setLeaveMigrationType(LeaveMigrationType.get(rs.getInt("migration_type")));
      history.setChangedOn(rs.getDate("changed_on"));
      history.setDebit(rs.getBigDecimal("debit"));
      history.setCredit(rs.getBigDecimal("credit"));
      history.setBalance(rs.getBigDecimal("balance"));
      history.setBalanceType(LeaveBalanceType.get(rs.getInt("balance_type")));
      history.setLastModified(rs.getString("last_modified"));
      return history;
    }
  }

}
