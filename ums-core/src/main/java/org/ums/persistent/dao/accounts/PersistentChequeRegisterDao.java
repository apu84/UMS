package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.ChequeRegisterDaoDecorator;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentChequeRegister;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 20-Feb-18.
 */
public class PersistentChequeRegisterDao extends ChequeRegisterDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentChequeRegisterDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String UPDATE_ONE =
      "update DT_CHEQUE_REGISTER set COMP_CODE=:compCode, TRANSACTION_ID=:transactionId, CHEQUE_NO=:chequeNo, CHEQUE_DATE=:chequeDate, STATUS=:status, REALIZATION_DATE=:realizationDate, "
          + "  STAT_FLAG=:statFlag, STAT_UP_FLAG=:statUpFlag, MODIFICATION_DATE=:modificationDate, MODIFIED_BY=:modifiedBy, LAST_MODIFIED=:lastModified where id=:id";
  String SELECT_ALL = "SELECT * FROM DT_CHEQUE_REGISTER";
  String INSERT_ONE =
      "INSERT INTO DT_CHEQUE_REGISTER(ID, COMP_CODE, TRANSACTION_ID, CHEQUE_NO, CHEQUE_DATE, STATUS, REALIZATION_DATE, STAT_FLAG, STAT_UP_FLAG, MODIFICATION_DATE, MODIFIED_BY, LAST_MODIFIED)  "
          + "VALUES (:id, :compCode, :transactionId, :chequeNo, :chequeDate, :status, :realizationDate,  :statFlag, :statUpFlag, :modificationDate, :modifiedBy, :lastModified)";
  String DELETE_ONE = "DELETE FROM DT_CHEQUE_REGISTER WHERE ID=?";

  @Override
  public int update(MutableChequeRegister pMutable) {
    String query = UPDATE_ONE;
    return mNamedParameterJdbcTemplate.update(query, getInsertOrUpdateParameters(pMutable));
  }

  @Override
  public int update(List<MutableChequeRegister> pMutableList) {
    String query = UPDATE_ONE;
    Map<String, Object>[] paramObjects = getParamObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, paramObjects).length;
  }

  @Override
  public int delete(MutableChequeRegister pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableChequeRegister> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableChequeRegister pMutable) {
    String query = INSERT_ONE;
    return new Long(mNamedParameterJdbcTemplate.update(query, getInsertOrUpdateParameters(pMutable)));
  }

  @Override
  public List<Long> create(List<MutableChequeRegister> pMutableList) {
    String query = INSERT_ONE;
    Map<String, Object>[] parameters = getParamObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, parameters);
    return pMutableList.stream().map(p -> p.getId()).collect(Collectors.toList());
  }

  private Map<String, Object>[] getParamObjects(List<MutableChequeRegister> pMutableList) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableList.size()];
    for(int i = 0; i < pMutableList.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableList.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableChequeRegister pMutableCheckRegister) {
    Map parameter = new HashMap();
    parameter.put("id", pMutableCheckRegister.getId());
    parameter.put("compCode", pMutableCheckRegister.getCompany().getId());
    parameter.put("transactionId", pMutableCheckRegister.getAccountTransaction().getId());
    parameter.put("chequeNo", pMutableCheckRegister.getCheckNo());
    parameter.put("chequeDate", pMutableCheckRegister.getChequeDate());
    parameter.put("status", pMutableCheckRegister.getStatus());
    parameter.put("realizationDate", pMutableCheckRegister.getRealizationDate());
    parameter.put("statFlag", pMutableCheckRegister.getStatFlag());
    parameter.put("statUpFlag", pMutableCheckRegister.getStatUpFlag());
    parameter.put("modificationDate", pMutableCheckRegister.getModificationDate());
    parameter.put("modifiedBy", pMutableCheckRegister.getModifiedBy());
    parameter.put("lastModified", getLastModifiedSql());
    return parameter;
  }

  class PersistentCheckRegisterRowMapper implements RowMapper<MutableChequeRegister> {
    @Override
    public MutableChequeRegister mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableChequeRegister checkRegister = new PersistentChequeRegister();
      checkRegister.setId(rs.getLong("id"));
      checkRegister.setCompanyId(rs.getString("comp_code"));
      checkRegister.setAccountTransactionId(rs.getLong("transaction_id"));
      checkRegister.setCheckNo(rs.getString("cheque_no"));
      checkRegister.setChequeDate(rs.getDate("cheque_date"));
      checkRegister.setStatus(rs.getString("status"));
      checkRegister.setRealizationDate(rs.getDate("realization_date"));
      checkRegister.setStatFlag(rs.getString("stat_flag"));
      checkRegister.setStatUpFlag(rs.getString("stat_up_flag"));
      checkRegister.setModificationDate(rs.getDate("modification_date"));
      checkRegister.setModifiedBy(rs.getString("modified_by"));
      checkRegister.setLastModified(rs.getString("last_modified"));
      return checkRegister;
    }
  }
}
