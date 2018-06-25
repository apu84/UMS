package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.SystemAccountMapDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.enums.accounts.definitions.account.balance.AccountType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentSystemAccountMap;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
public class PersistentSystemAccountMapDao extends SystemAccountMapDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  private String SELECT_ALL = "select * from system_account_map";
  private String INSERT_ONE =
      "insert into SYSTEM_ACCOUNT_MAP(ID, ACCOUNT_TYPE, ACCOUNT_ID, COMP_CODE, MODIFIED_BY, MODIFIED_DATE, LAST_MODIFIED)  "
          + "    VALUES (:id, :accountType, :accountId, :compCode, :modifiedBy, :modifiedDate, :lastModified)";
  private String UPDATE_ONE =
      "UPDATE SYSTEM_ACCOUNT_MAP SET ACCOUNT_TYPE=:accountType, ACCOUNT_ID=:accountId, COMP_CODE=:compCode,  "
          + "  MODIFIED_BY=:modifiedBy, MODIFIED_DATE=:modifiedDate, LAST_MODIFIED=:lastModified where ID=:id";
  private String DELETE_ONE = "delete from system_group_map where id=:id";

  public PersistentSystemAccountMapDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<SystemAccountMap> getAll() {
    String query = SELECT_ALL;
    return mNamedParameterJdbcTemplate.query(query, new PersistentSystemAccountMapRowMapper());
  }

  @Override
  public List<SystemAccountMap> getAll(Company pCompany) {
    String query = SELECT_ALL + " where comp_code=?";
    return mJdbcTemplate.query(query, new Object[] {pCompany.getId()}, new PersistentSystemAccountMapRowMapper());
  }

  @Override
  public SystemAccountMap get(AccountType pAccountType, Company pCompany) {
    String query = SELECT_ALL + " where account_type=? and comp_code=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pAccountType.getValue(), pCompany.getId()},
        new PersistentSystemAccountMapRowMapper());
  }

  @Override
  public SystemAccountMap get(Long pId) {
    String query = SELECT_ALL + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentSystemAccountMapRowMapper());
  }

  @Override
  public SystemAccountMap validate(SystemAccountMap pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableSystemAccountMap pMutable) {
    return update(Arrays.asList(pMutable));
  }

  @Override
  public int update(List<MutableSystemAccountMap> pMutableList) {
    String query = UPDATE_ONE;
    Map<String, Object>[] parameterList = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterList).length;
  }

  @Override
  public int delete(MutableSystemAccountMap pMutable) {
    return delete(Arrays.asList(pMutable));
  }

  @Override
  public int delete(List<MutableSystemAccountMap> pMutableList) {
    String query = DELETE_ONE;
    Map<String, Object>[] parameterList = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterList).length;
  }

  @Override
  public Long create(MutableSystemAccountMap pMutable) {
    return create(Arrays.asList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableSystemAccountMap> pMutableList) {
    String query = INSERT_ONE;
    Map<String, Object>[] parameterList = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, parameterList);
    return pMutableList.stream()
        .map(p -> p.getId())
        .collect(Collectors.toList());
  }

  @Override
  public boolean exists(Long pId) {
    String query = "select count(*) from system_account_map where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    Integer count = mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class);
    return count == 0 ? false : true;
  }

  public Map getInsertOrUpdateParameters(MutableSystemAccountMap pMutableSystemAccountMap) {
    Map parameter = new HashMap();
    parameter.put("id", pMutableSystemAccountMap.getId());
    parameter.put("accountType", pMutableSystemAccountMap.getAccountType().getValue());
    parameter.put("accountId", pMutableSystemAccountMap.getAccountId());
    parameter.put("modifiedBy", pMutableSystemAccountMap.getModifiedBy());
    parameter.put("modifiedDate", new Date());
    parameter.put("compCode", pMutableSystemAccountMap.getCompanyId());
    parameter.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    return parameter;
  }

  private Map<String, Object>[] getParameterObjects(List<MutableSystemAccountMap> pMutableSystemAccountMaps) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableSystemAccountMaps.size()];
    for(int i = 0; i < pMutableSystemAccountMaps.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableSystemAccountMaps.get(i));
    }
    return parameterMaps;
  }

  class PersistentSystemAccountMapRowMapper implements RowMapper<SystemAccountMap> {
    @Override
    public SystemAccountMap mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableSystemAccountMap accountMap = new PersistentSystemAccountMap();
      accountMap.setId(rs.getLong("id"));
      accountMap.setAccountType(AccountType.get(rs.getLong("account_type")));
      accountMap.setAccountId(rs.getLong("account_id"));
      accountMap.setCompanyId(rs.getString("comp_code"));
      accountMap.setModifiedBy(rs.getString("modified_by"));
      accountMap.setModifiedDate(rs.getDate("modified_date"));
      accountMap.setLastModified(rs.getString("last_modified"));
      return accountMap;
    }
  }

}
