package org.ums.persistent.dao.accounts;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.ums.decorator.accounts.AccountDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.enums.common.AscendingOrDescendingType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class PersistentAccountDao extends AccountDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAccountDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Account> getExcludingGroups(List<String> groupCodeList) {
    if(groupCodeList.size() == 0)
      return null;
    String query = "select * from mst_account where acc_group_code not in (:groupCodeList)";
    Map parameterMap = new HashMap();
    parameterMap.put("groupCodeList", groupCodeList);
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getAccounts(Company pCompany) {
    String query = "select * from mst_account where comp_code=:compCode";
    Map parameterMap = new HashMap();
    parameterMap.put("compCode", pCompany.getId());
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getIncludingGroups(List<String> groupCodeList) {
    if(groupCodeList.size() == 0)
      return null;
    String query = "select * from mst_account where acc_group_code  in (:groupCodeList)";
    Map parameterMap = new HashMap();
    parameterMap.put("groupCodeList", groupCodeList);
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getAll() {
    String query = "select * from mst_account";
    return mJdbcTemplate.query(query, new PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getAccounts(String pAccountName) {
    String query = "select * from MST_ACCOUNT where ACCOUNT_NAME like :accountName";
    SqlParameterSource namedParameters = new MapSqlParameterSource("accountName", "%" + pAccountName + "%");
    return this.mNamedParameterJdbcTemplate.query(query, namedParameters, new PersistentAccountRowMapper());
    // return mJdbcTemplate.query(query, new Object[]{pAccountName}, new
    // PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getAccounts(GroupFlag pGroupFlag) {
    String query = "select * from MST_ACCOUNT where ACC_GROUP_CODE  in (select GROUP_CODE from MST_GROUP where FLAG=?)";
    return mJdbcTemplate.query(query, new Object[] {pGroupFlag.getValue()}, new PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getAllPaginated(int itemPerPage, int pageNumber,
      AscendingOrDescendingType pAscendingOrDescendingType) {
    int startIndex = (itemPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemPerPage - 1;
    String ascendingOrDecendingType =
        pAscendingOrDescendingType.getValue().equals(AscendingOrDescendingType.ASCENDING) ? "ASC" : "DESC";
    String query =
        "select * from (select ROWNUM row_num,mst_account.* from mst_account order by LAST_MODIFIED "
            + ascendingOrDecendingType + " )tmp where row_num>=? and row_num<=? ";
    return mJdbcTemplate.query(query, new Object[] {startIndex, endIndex}, new PersistentAccountRowMapper());
  }

  @Override
  public Integer getSize() {
    String query = "select count(*) from mst_account";
    return mJdbcTemplate.queryForObject(query, Integer.class);
  }

  @Override
  public Account get(Long pId) {
    String query = "select * from mst_account where id=:id";
    try {
      Map parameterMap = new HashMap();
      parameterMap.put("id", pId);
      return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentAccountRowMapper());
    } catch(EmptyResultDataAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Account getAccount(Long pAccountCode, Company pCompany) {
    String query = "select * from mst_account where account_code=:accountCode and comp_code=:compCode";
    Map parameterMap = new HashMap();
    parameterMap.put("accountCode", pAccountCode);
    parameterMap.put("compCode", pCompany.getId());
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentAccountRowMapper());
  }

  @Override
  public Account validate(Account pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableAccount pMutable) {
    String query =
        "update MST_ACCOUNT " + "set ACCOUNT_CODE=:accountCode, " + "  ACCOUNT_NAME=:accountName, "
            + "  ACC_GROUP_CODE=:accGroupCode, " + "  MODIFIED_DATE=:modifiedDate, " + "  MODIFIED_BY=:modifiedBy, "
            + "  COMP_CODE=:compCode, RESERVED=:reserved, " + "  LAST_MODIFIED=:lastModified " + "where ID=:id";
    Map parameterMap = getAccountParameterMap(pMutable, pMutable.getId());
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int update(List<MutableAccount> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableAccount pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableAccount> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableAccount pMutable) {
    String query =
        "insert into MST_ACCOUNT(ID, ACCOUNT_CODE, ACCOUNT_NAME, ACC_GROUP_CODE, RESERVED,  MODIFIED_DATE, MODIFIED_BY, COMP_CODE, LAST_MODIFIED) "
            + "            VALUES (:id,:accountCode,:accountName,:accGroupCode, :reserved,:modifiedDate,:modifiedBy, :compCode,:lastModified )";
    Long id = pMutable.getId() == null ? mIdGenerator.getNumericId() : pMutable.getId();
    Map parameterMap = getAccountParameterMap(pMutable, id);
    mNamedParameterJdbcTemplate.update(query, parameterMap);
    return id;
  }

  @NotNull
  private Map getAccountParameterMap(MutableAccount pMutable, Long id) {
    Map parameterMap = new HashMap();
    parameterMap.put("id", id);
    parameterMap.put("accountCode", pMutable.getAccountCode());
    parameterMap.put("accountName", pMutable.getAccountName());
    parameterMap.put("accGroupCode", pMutable.getAccGroupCode());
    parameterMap.put("modifiedDate", pMutable.getModifiedDate());
    parameterMap.put("modifiedBy", pMutable.getModifiedBy());
    parameterMap.put("compCode", pMutable.getCompanyId());
    parameterMap.put("reserved", pMutable.getReserved().equals(true) ? 1 : false);
    parameterMap.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    return parameterMap;
  }

  @Override
  public List<Long> create(List<MutableAccount> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(Long pId) {
    String query = "select count(*) from mst_account where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class) == 0 ? false : true;
  }

  @Override
  public boolean exists(Long pAccountCode, Company pCompany) {
    String query = "select count(*) from mst_account where account_code=:accountCode and comp_code=:compCode";
    Map parameterMap = new HashMap();
    parameterMap.put("accountCode", pAccountCode);
    parameterMap.put("compCode", pCompany.getId());
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class) == 0 ? false : true;
  }

  class PersistentAccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableAccount account = new PersistentAccount();
      account.setId(rs.getLong("id"));
      try {
        account.setRowNumber(rs.getInt("row_num"));
      } catch(Exception e) {
        // e.printStackTrace();
      }
      account.setAccountCode(rs.getLong("account_code"));
      account.setAccountName(rs.getString("account_name"));
      account.setAccGroupCode(rs.getString("acc_group_code"));
      account.setReserved(rs.getBoolean("reserved"));
      account.setTaxLimit(rs.getBigDecimal("tax_limit"));
      account.setTaxCode(rs.getString("tax_code"));
      account.setStatFlag(rs.getString("stat_flag"));
      account.setStatUpFlag(rs.getString("stat_up_flag"));
      account.setModifiedDate(rs.getDate("modified_date"));
      account.setModifiedBy(rs.getString("modified_by"));
      account.setLastModified(rs.getString("last_modified"));
      account.setCompanyId(rs.getString("comp_code"));
      return account;
    }
  }
}
