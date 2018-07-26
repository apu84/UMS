package org.ums.persistent.dao.common;

import org.eclipse.collections.impl.bimap.mutable.HashBiMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.common.EmployeeEarnedLeaveBalanceDaoDecorator;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentEmployeeEarnedLeaveBalance;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public class PersistentEmployeeEarnedLeaveBalanceDao extends EmployeeEarnedLeaveBalanceDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;


  private String SELECT_ALL="select * from emp_el_balance";
  private String INSERT_ONE="insert into EMP_EL_BALANCE(ID, EMPLOYEE_ID, FULL_PAY, HALF_PAY, CREATED_ON, UPDATED_ON, LAST_MODIFIED) values (:id, :employeeId, :fullPay, :halfPay, :createdOn, :updatedOn, :lastModified)";
  private String UPDATE_ONE="update EMP_EL_BALANCE set EMPLOYEE_ID=:employeeId, FULL_PAY=:fullPay, HALF_PAY=:halfPay, CREATED_ON=:createdOn, UPDATED_ON=:updatedOn, LAST_MODIFIED=:lastModified where id=:id";
  private String DELETE_ONE="delete from EMP_EL_BALANCE where id=:id";

  public PersistentEmployeeEarnedLeaveBalanceDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }


  @Override
  public List<EmployeeEarnedLeaveBalance> getAll() {
    String query=SELECT_ALL;
    return mNamedParameterJdbcTemplate.query(query, new EmployeeEarnedLeaveBalanceRowMapper());
  }

  @Override
  public EmployeeEarnedLeaveBalance get(Long pId) {
    String query=SELECT_ALL+" where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new EmployeeEarnedLeaveBalanceRowMapper());
  }

  @Override
  public EmployeeEarnedLeaveBalance validate(EmployeeEarnedLeaveBalance pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableEmployeeEarnedLeaveBalance pMutable) {
    String query=UPDATE_ONE;
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int update(List<MutableEmployeeEarnedLeaveBalance> pMutableList) {
    String query=UPDATE_ONE;
    Map<String, Object>[] updateParameterList = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, updateParameterList).length;
  }

  @Override
  public int delete(MutableEmployeeEarnedLeaveBalance pMutable) {
    String query = DELETE_ONE;
    Map parameters = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameters );
  }

  @Override
  public int delete(List<MutableEmployeeEarnedLeaveBalance> pMutableList) {
    String query = DELETE_ONE;
    Map<String, Object>[] parameterList = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterList).length;
  }

  @Override
  public Long create(MutableEmployeeEarnedLeaveBalance pMutable) {
    String query=INSERT_ONE;
    Map insertParameters = getInsertOrUpdateParameters(pMutable);
    mNamedParameterJdbcTemplate.update(query, insertParameters);
    return pMutable.getId();
  }

  @Override
  public List<Long> create(List<MutableEmployeeEarnedLeaveBalance> pMutableList) {
    String query=INSERT_ONE;
    Map<String, Object>[] createParameterList = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, createParameterList);
    return pMutableList.stream()
        .map(i->i.getId())
        .collect(Collectors.toList());
  }

  @Override
  public boolean exists(Long pId) {
    return get(pId)==null?false: true;
  }


  private Map<String, Object>[] getParameterObjects(List<MutableEmployeeEarnedLeaveBalance> pMutableEmployeeEarnedLeaveBalances){
    Map<String, Object>[] parameterMaps = new HashBiMap[pMutableEmployeeEarnedLeaveBalances.size()];
    for(int i=0; i<pMutableEmployeeEarnedLeaveBalances.size(); i++){
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableEmployeeEarnedLeaveBalances.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableEmployeeEarnedLeaveBalance pMutableEmployeeEarnedLeaveBalance){
    Map parameter = new HashBiMap();
    parameter.put("id", pMutableEmployeeEarnedLeaveBalance.getId());
    parameter.put("employeeId", pMutableEmployeeEarnedLeaveBalance.getEmployeeId());
    parameter.put("fullPay", pMutableEmployeeEarnedLeaveBalance.getFullPay());
    parameter.put("halfPay", pMutableEmployeeEarnedLeaveBalance.getHalfPay());
    parameter.put("createdOn", pMutableEmployeeEarnedLeaveBalance.getCreatedOn());
    parameter.put("updatedOn", pMutableEmployeeEarnedLeaveBalance.getUpdatedOn());
    parameter.put("lastModified", UmsUtils.formatDate(new java.util.Date(), "YYYYMMDDHHMMSS"));
    return parameter;
  }

  class EmployeeEarnedLeaveBalanceRowMapper implements RowMapper<EmployeeEarnedLeaveBalance>{
    @Override
    public EmployeeEarnedLeaveBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableEmployeeEarnedLeaveBalance balance = new PersistentEmployeeEarnedLeaveBalance();
      balance.setId(rs.getLong("id"));
      balance.setEmployeeId(rs.getString("employee_id"));
      balance.setFullPay(rs.getBigDecimal("full_pay"));
      balance.setHalfPay(rs.getBigDecimal("half_pay"));
      balance.setCreatedOn(rs.getDate("created_on"));
      balance.setUpdatedOn(rs.getDate("updated_on"));
      balance.setLastModified(rs.getString("last_modified"));
      return balance;
    }
  }

}
