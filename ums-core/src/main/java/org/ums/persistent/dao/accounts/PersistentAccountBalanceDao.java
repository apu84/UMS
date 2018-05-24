package org.ums.persistent.dao.accounts;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.AccountBalanceDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.util.UmsUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public class PersistentAccountBalanceDao extends AccountBalanceDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  String INSERT_ONE =
      "INSERT INTO MST_ACCOUNT_BALANCE (ID, FIN_START_DATE, FIN_END_DATE, ACCOUNT_CODE, YEAR_OPEN_BALANCE, YEAR_OPEN_BALANCE_TYPE, TOT_MONTH_DB_BAL_04, TOT_MONTH_CR_BAL_04, TOT_MONTH_DB_BAL_05, TOT_MONTH_CR_BAL_05, TOT_MONTH_DB_BAL_06, TOT_MONTH_CR_BAL_06, TOT_MONTH_DB_BAL_07, TOT_MONTH_CR_BAL_07, TOT_MONTH_DB_BAL_08, TOT_MONTH_CR_BAL_08, TOT_MONTH_DB_BAL_09, TOT_MONTH_CR_BAL_09, TOT_MONTH_DB_BAL_10, TOT_MONTH_CR_BAL_10, TOT_MONTH_DB_BAL_11, TOT_MONTH_CR_BAL_11, TOT_MONTH_DB_BAL_12, TOT_MONTH_CR_BAL_12, TOT_MONTH_DB_BAL_01, TOT_MONTH_CR_BAL_01, TOT_MONTH_DB_BAL_02, TOT_MONTH_CR_BAL_02, TOT_MONTH_DB_BAL_03, TOT_MONTH_CR_BAL_03, TOT_DEBIT_TRANS, TOT_CREDIT_TRANS, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY, LAST_MODIFIED) "
          + "VALUES ( "
          + "  :ID, "
          + "  :FIN_START_DATE, "
          + "  :FIN_END_DATE, "
          + "  :ACCOUNT_CODE, "
          + "  :YEAR_OPEN_BALANCE, "
          + "  :YEAR_OPEN_BALANCE_TYPE, "
          + "  :TOT_MONTH_DB_BAL_04, "
          + "  :TOT_MONTH_CR_BAL_04, "
          + "  :TOT_MONTH_DB_BAL_05, "
          + "  :TOT_MONTH_CR_BAL_05, "
          + "  :TOT_MONTH_DB_BAL_06, "
          + "  :TOT_MONTH_CR_BAL_06, "
          + "  :TOT_MONTH_DB_BAL_07, "
          + "  :TOT_MONTH_CR_BAL_07, "
          + "  :TOT_MONTH_DB_BAL_08, "
          + "  :TOT_MONTH_CR_BAL_08, "
          + "  :TOT_MONTH_DB_BAL_09, "
          + "  :TOT_MONTH_CR_BAL_09, "
          + "  :TOT_MONTH_DB_BAL_10, "
          + "  :TOT_MONTH_CR_BAL_10, "
          + "  :TOT_MONTH_DB_BAL_11, "
          + "  :TOT_MONTH_CR_BAL_11, "
          + "  :TOT_MONTH_DB_BAL_12, "
          + "  :TOT_MONTH_CR_BAL_12, "
          + "  :TOT_MONTH_DB_BAL_01, "
          + "  :TOT_MONTH_CR_BAL_01, "
          + "  :TOT_MONTH_DB_BAL_02, "
          + "  :TOT_MONTH_CR_BAL_02, "
          + "  :TOT_MONTH_DB_BAL_03, "
          + "  :TOT_MONTH_CR_BAL_03, "
          + "  :TOT_DEBIT_TRANS, "
          + "  :TOT_CREDIT_TRANS, "
          + "  :STAT_FLAG, " + "  :STAT_UP_FLAG, " + "  :MODIFIED_DATE, " + "  :MODIFIED_BY, " + "  :LAST_MODIFIED)";

  String UPDATE_ONE = "UPDATE DB_IUMS_ACCOUNT.MST_ACCOUNT_BALANCE " + "SET FIN_START_DATE = :FIN_START_DATE, "
      + "  FIN_END_DATE        = :FIN_END_DATE, "
      + "  YEAR_OPEN_BALANCE   = :YEAR_OPEN_BALANCE, YEAR_OPEN_BALANCE_TYPE = :YEAR_OPEN_BALANCE_TYPE , "
      + "  TOT_MONTH_DB_BAL_01 = :TOT_MONTH_DB_BAL_01, " + "  TOT_MONTH_DB_BAL_02 = :TOT_MONTH_DB_BAL_02, "
      + "  TOT_MONTH_DB_BAL_03 = :TOT_MONTH_DB_BAL_03, " + "  TOT_MONTH_DB_BAL_04 = :TOT_MONTH_DB_BAL_04, "
      + "  TOT_MONTH_DB_BAL_05 = :TOT_MONTH_DB_BAL_05, " + "  TOT_MONTH_DB_BAL_06 = :TOT_MONTH_DB_BAL_06, "
      + "  TOT_MONTH_DB_BAL_07 = :TOT_MONTH_DB_BAL_07, " + "  TOT_MONTH_DB_BAL_08 = :TOT_MONTH_DB_BAL_08, "
      + "  TOT_MONTH_DB_BAL_09 = :TOT_MONTH_DB_BAL_09, " + "  TOT_MONTH_DB_BAL_10 = :TOT_MONTH_DB_BAL_10, "
      + "  TOT_MONTH_DB_BAL_11 = :TOT_MONTH_DB_BAL_11, " + "  TOT_MONTH_DB_BAL_12 = :TOT_MONTH_DB_BAL_12, "
      + "  TOT_MONTH_CR_BAL_01 = :TOT_MONTH_CR_BAL_01, " + "  TOT_MONTH_CR_BAL_02 = :TOT_MONTH_CR_BAL_02, "
      + "  TOT_MONTH_CR_BAL_03 = :TOT_MONTH_CR_BAL_03, " + "  TOT_MONTH_CR_BAL_04 = :TOT_MONTH_CR_BAL_04, "
      + "  TOT_MONTH_CR_BAL_05 = :TOT_MONTH_CR_BAL_05, " + "  TOT_MONTH_CR_BAL_06 = :TOT_MONTH_CR_BAL_06, "
      + "  TOT_MONTH_CR_BAL_07=  :TOT_MONTH_CR_BAL_07, " + "  TOT_MONTH_CR_BAL_08 = :TOT_MONTH_CR_BAL_08,  "
      + "  TOT_MONTH_CR_BAL_09 = :TOT_MONTH_CR_BAL_09, " + "  TOT_MONTH_CR_BAL_10 = :TOT_MONTH_CR_BAL_10, "
      + "  TOT_MONTH_CR_BAL_11 = :TOT_MONTH_CR_BAL_11, " + "  TOT_MONTH_CR_BAL_12 = :TOT_MONTH_CR_BAL_12, "
      + "  TOT_DEBIT_TRANS = :TOT_DEBIT_TRANS, TOT_CREDIT_TRANS = :TOT_CREDIT_TRANS, "
      + "  STAT_FLAG           = :STAT_FLAG, STAT_UP_FLAG = :STAT_UP_FLAG, "
      + "  MODIFIED_DATE       = :MODIFIED_DATE, MODIFIED_BY = :MODIFIED_BY, "
      + "  LAST_MODIFIED       = :LAST_MODIFIED WHERE ID=:ID";

  public PersistentAccountBalanceDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public MutableAccountBalance getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate, Account pAccount) {
    String query = "select * from MST_ACCOUNT_BALANCE where FIN_START_DATE=? and FIN_END_DATE=? and ACCOUNT_CODE=?";
    try {
      return mJdbcTemplate.queryForObject(query,
          new Object[] {pFinancialStartDate, pFinancialEndDate, pAccount.getId()}, new AccountBalanceRowMapper());
    } catch(EmptyResultDataAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<MutableAccountBalance> getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate, List<Account> pAccounts) {
      if(pAccounts.size()==0)
          return null;
   List<Long> accountCode= new ArrayList<>();
   accountCode = pAccounts.stream().map(a->a.getId()).collect(Collectors.toList());
    String query = "select * from MST_ACCOUNT_BALANCE where FIN_START_DATE=:finStartDate and FIN_END_DATE=:finEndDate and ACCOUNT_CODE in (:accountCodeList)";
    Map parameterMap = new HashMap();
    parameterMap.put("finStartDate", pFinancialStartDate);
    parameterMap.put("finEndDate", pFinancialEndDate);
    parameterMap.put("accountCodeList", accountCode);
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new AccountBalanceRowMapper());
  }

  @Override
  public List<MutableAccountBalance> getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate) {
    String query = "select * from MST_ACCOUNT_BALANCE where FIN_START_DATE=:finStartDate and FIN_END_DATE=:finEndDate";
    Map parameterMap = new HashMap();
    parameterMap.put("finStartDate", pFinancialStartDate);
    parameterMap.put("finEndDate", pFinancialEndDate);
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new AccountBalanceRowMapper());
  }

  @Override
  public Long insertFromAccount(MutableAccountBalance pAccountBalance) {
    String query = INSERT_ONE;
    Map parameterMap = getInsertParameters(pAccountBalance);
    mNamedParameterJdbcTemplate.update(query, parameterMap);
    return pAccountBalance.getId();
  }

  @Override
  public int update(List<MutableAccountBalance> pMutableList) {
    String query = UPDATE_ONE;
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects).length;
  }

  @Override
  public int update(MutableAccountBalance pMutable) {
    String query = UPDATE_ONE;
    Map parameterMap = getInsertParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
    public List<Long> create(List<MutableAccountBalance> pMutableList) {
      String query=INSERT_ONE;
      Map<String, Object>[] parameters = getParameterObjects(pMutableList);
      mNamedParameterJdbcTemplate.batchUpdate(query, parameters);
      return pMutableList
              .stream()
              .map(i->i.getId())
              .collect(Collectors.toList());
    }

  private Map<String, Object>[] getParameterObjects(List<MutableAccountBalance> pMutableAccountBalances) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableAccountBalances.size()];
    for(int i = 0; i < pMutableAccountBalances.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableAccountBalances.get(i));
    }
    return parameterMaps;
  }

  private Map<String, Object>[] getInsertParameterObjects(List<MutableAccountBalance> pMutableAccountBalances) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableAccountBalances.size()];
    for(int i = 0; i < pMutableAccountBalances.size(); i++) {
      parameterMaps[i] = getInsertParameters(pMutableAccountBalances.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableAccountBalance pMutableAccountBalance) {
    Map parameter = new HashMap();
    parameter.put("ID", pMutableAccountBalance.getId());
    parameter.put("FIN_START_DATE", pMutableAccountBalance.getFinStartDate());
    parameter.put("FIN_END_DATE", pMutableAccountBalance.getFinEndDate());
    parameter.put("ACCOUNT_CODE", pMutableAccountBalance.getAccountCode());
    parameter.put("YEAR_OPEN_BALANCE", pMutableAccountBalance.getYearOpenBalance());
    parameter.put("YEAR_OPEN_BALANCE_TYPE", pMutableAccountBalance.getYearOpenBalanceType().getValue());
    parameter.put("TOT_MONTH_DB_BAL_01", pMutableAccountBalance.getTotMonthDbBal01());
    parameter.put("TOT_MONTH_DB_BAL_02", pMutableAccountBalance.getTotMonthDbBal02());
    parameter.put("TOT_MONTH_DB_BAL_03", pMutableAccountBalance.getTotMonthDbBal03());
    parameter.put("TOT_MONTH_DB_BAL_04", pMutableAccountBalance.getTotMonthDbBal04());
    parameter.put("TOT_MONTH_DB_BAL_05", pMutableAccountBalance.getTotMonthDbBal05());
    parameter.put("TOT_MONTH_DB_BAL_06", pMutableAccountBalance.getTotMonthDbBal06());
    parameter.put("TOT_MONTH_DB_BAL_07", pMutableAccountBalance.getTotMonthDbBal07());
    parameter.put("TOT_MONTH_DB_BAL_08", pMutableAccountBalance.getTotMonthDbBal08());
    parameter.put("TOT_MONTH_DB_BAL_09", pMutableAccountBalance.getTotMonthDbBal09());
    parameter.put("TOT_MONTH_DB_BAL_10", pMutableAccountBalance.getTotMonthDbBal10());
    parameter.put("TOT_MONTH_DB_BAL_11", pMutableAccountBalance.getTotMonthDbBal11());
    parameter.put("TOT_MONTH_DB_BAL_12", pMutableAccountBalance.getTotMonthDbBal12());
    parameter.put("TOT_MONTH_CR_BAL_01", pMutableAccountBalance.getTotMonthCrBal01());
    parameter.put("TOT_MONTH_CR_BAL_02", pMutableAccountBalance.getTotMonthCrBal02());
    parameter.put("TOT_MONTH_CR_BAL_03", pMutableAccountBalance.getTotMonthCrBal03());
    parameter.put("TOT_MONTH_CR_BAL_04", pMutableAccountBalance.getTotMonthCrBal04());
    parameter.put("TOT_MONTH_CR_BAL_05", pMutableAccountBalance.getTotMonthCrBal05());
    parameter.put("TOT_MONTH_CR_BAL_06", pMutableAccountBalance.getTotMonthCrBal06());
    parameter.put("TOT_MONTH_CR_BAL_07", pMutableAccountBalance.getTotMonthCrBal07());
    parameter.put("TOT_MONTH_CR_BAL_08", pMutableAccountBalance.getTotMonthCrBal08());
    parameter.put("TOT_MONTH_CR_BAL_09", pMutableAccountBalance.getTotMonthCrBal09());
    parameter.put("TOT_MONTH_CR_BAL_10", pMutableAccountBalance.getTotMonthCrBal10());
    parameter.put("TOT_MONTH_CR_BAL_11", pMutableAccountBalance.getTotMonthCrBal11());
    parameter.put("TOT_MONTH_CR_BAL_12", pMutableAccountBalance.getTotMonthCrBal12());
    parameter.put("TOT_DEBIT_TRANS", pMutableAccountBalance.getTotDebitTrans());
    parameter.put("TOT_CREDIT_TRANS", pMutableAccountBalance.getTotCreditTrans());
    parameter.put("STAT_FLAG", pMutableAccountBalance.getStatFlag());
    parameter.put("STAT_UP_FLAG", pMutableAccountBalance.getStatUpFlag());
    parameter.put("MODIFIED_DATE", pMutableAccountBalance.getModifiedDate());
    parameter.put("MODIFIED_BY", pMutableAccountBalance.getModifiedBy());
    parameter.put("LAST_MODIFIED", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    return parameter;
  }

  private Map getInsertParameters(MutableAccountBalance pMutableAccountBalance) {
    Map parameter = new HashMap();
    parameter.put("ID", pMutableAccountBalance.getId());
    parameter.put("FIN_START_DATE", pMutableAccountBalance.getFinStartDate());
    parameter.put("FIN_END_DATE", pMutableAccountBalance.getFinEndDate());
    parameter.put("ACCOUNT_CODE", pMutableAccountBalance.getAccountCode());
    parameter.put("YEAR_OPEN_BALANCE", pMutableAccountBalance.getYearOpenBalance());
    parameter.put("YEAR_OPEN_BALANCE_TYPE", pMutableAccountBalance.getYearOpenBalanceType().getValue());
    parameter.put("TOT_MONTH_DB_BAL_01", pMutableAccountBalance.getTotMonthDbBal01() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal01());
    parameter.put("TOT_MONTH_DB_BAL_02", pMutableAccountBalance.getTotMonthDbBal02() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal02());
    parameter.put("TOT_MONTH_DB_BAL_03", pMutableAccountBalance.getTotMonthDbBal03() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal03());
    parameter.put("TOT_MONTH_DB_BAL_04", pMutableAccountBalance.getTotMonthDbBal04() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal04());
    parameter.put("TOT_MONTH_DB_BAL_05", pMutableAccountBalance.getTotMonthDbBal05() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal05());
    parameter.put("TOT_MONTH_DB_BAL_06", pMutableAccountBalance.getTotMonthDbBal06() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal06());
    parameter.put("TOT_MONTH_DB_BAL_07", pMutableAccountBalance.getTotMonthDbBal07() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal07());
    parameter.put("TOT_MONTH_DB_BAL_08", pMutableAccountBalance.getTotMonthDbBal08() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal08());
    parameter.put("TOT_MONTH_DB_BAL_09", pMutableAccountBalance.getTotMonthDbBal09() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal09());
    parameter.put("TOT_MONTH_DB_BAL_10", pMutableAccountBalance.getTotMonthDbBal10() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal10());
    parameter.put("TOT_MONTH_DB_BAL_11", pMutableAccountBalance.getTotMonthDbBal11() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal11());
    parameter.put("TOT_MONTH_DB_BAL_12", pMutableAccountBalance.getTotMonthDbBal12() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthDbBal12());
    parameter.put("TOT_MONTH_CR_BAL_01", pMutableAccountBalance.getTotMonthCrBal01() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal01());
    parameter.put("TOT_MONTH_CR_BAL_02", pMutableAccountBalance.getTotMonthCrBal02() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal02());
    parameter.put("TOT_MONTH_CR_BAL_03", pMutableAccountBalance.getTotMonthCrBal03() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal03());
    parameter.put("TOT_MONTH_CR_BAL_04", pMutableAccountBalance.getTotMonthCrBal04() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal04());
    parameter.put("TOT_MONTH_CR_BAL_05", pMutableAccountBalance.getTotMonthCrBal05() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal05());
    parameter.put("TOT_MONTH_CR_BAL_06", pMutableAccountBalance.getTotMonthCrBal06() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal06());
    parameter.put("TOT_MONTH_CR_BAL_07", pMutableAccountBalance.getTotMonthCrBal07() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal07());
    parameter.put("TOT_MONTH_CR_BAL_08", pMutableAccountBalance.getTotMonthCrBal08() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal08());
    parameter.put("TOT_MONTH_CR_BAL_09", pMutableAccountBalance.getTotMonthCrBal09() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal09());
    parameter.put("TOT_MONTH_CR_BAL_10", pMutableAccountBalance.getTotMonthCrBal10() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal10());
    parameter.put("TOT_MONTH_CR_BAL_11", pMutableAccountBalance.getTotMonthCrBal11() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal11());
    parameter.put("TOT_MONTH_CR_BAL_12", pMutableAccountBalance.getTotMonthCrBal12() == null ? new BigDecimal(0)
        : pMutableAccountBalance.getTotMonthCrBal12());
    parameter.put("TOT_DEBIT_TRANS", pMutableAccountBalance.getTotDebitTrans());
    parameter.put("TOT_CREDIT_TRANS", pMutableAccountBalance.getTotCreditTrans());
    parameter.put("STAT_FLAG", pMutableAccountBalance.getStatFlag());
    parameter.put("STAT_UP_FLAG", pMutableAccountBalance.getStatUpFlag());
    parameter.put("MODIFIED_DATE", pMutableAccountBalance.getModifiedDate());
    parameter.put("MODIFIED_BY", pMutableAccountBalance.getModifiedBy());
    parameter.put("LAST_MODIFIED", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    return parameter;
  }

  private class AccountBalanceRowMapper implements RowMapper<MutableAccountBalance> {
    @Override
    public MutableAccountBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableAccountBalance accountBalance = new PersistentAccountBalance();
      accountBalance.setId(rs.getLong("id"));
      accountBalance.setFinStartDate(rs.getDate("fin_start_date"));
      accountBalance.setFinEndDate(rs.getDate("fin_end_date"));
      accountBalance.setAccountCode(rs.getLong("account_code"));
      accountBalance.setYearOpenBalance(rs.getBigDecimal("year_open_balance"));
      accountBalance.setYearOpenBalanceType(BalanceType.get(rs.getString("year_open_balance_type")));
      accountBalance.setTotMonthDbBal01(rs.getBigDecimal("tot_month_db_bal_01"));
      accountBalance.setTotMonthDbBal02(rs.getBigDecimal("tot_month_db_bal_02"));
      accountBalance.setTotMonthDbBal03(rs.getBigDecimal("tot_month_db_bal_03"));
      accountBalance.setTotMonthDbBal04(rs.getBigDecimal("tot_month_db_bal_04"));
      accountBalance.setTotMonthDbBal05(rs.getBigDecimal("tot_month_db_bal_05"));
      accountBalance.setTotMonthDbBal06(rs.getBigDecimal("tot_month_db_bal_06"));
      accountBalance.setTotMonthDbBal07(rs.getBigDecimal("tot_month_db_bal_07"));
      accountBalance.setTotMonthDbBal08(rs.getBigDecimal("tot_month_db_bal_08"));
      accountBalance.setTotMonthDbBal09(rs.getBigDecimal("tot_month_db_bal_09"));
      accountBalance.setTotMonthDbBal10(rs.getBigDecimal("tot_month_db_bal_10"));
      accountBalance.setTotMonthDbBal11(rs.getBigDecimal("tot_month_db_bal_11"));
      accountBalance.setTotMonthDbBal12(rs.getBigDecimal("tot_month_db_bal_12"));
      accountBalance.setTotMonthCrBal01(rs.getBigDecimal("tot_month_cr_bal_01"));
      accountBalance.setTotMonthCrBal02(rs.getBigDecimal("tot_month_cr_bal_02"));
      accountBalance.setTotMonthCrBal03(rs.getBigDecimal("tot_month_cr_bal_03"));
      accountBalance.setTotMonthCrBal04(rs.getBigDecimal("tot_month_cr_bal_04"));
      accountBalance.setTotMonthCrBal05(rs.getBigDecimal("tot_month_cr_bal_05"));
      accountBalance.setTotMonthCrBal06(rs.getBigDecimal("tot_month_cr_bal_06"));
      accountBalance.setTotMonthCrBal07(rs.getBigDecimal("tot_month_cr_bal_07"));
      accountBalance.setTotMonthCrBal08(rs.getBigDecimal("tot_month_cr_bal_08"));
      accountBalance.setTotMonthCrBal09(rs.getBigDecimal("tot_month_cr_bal_09"));
      accountBalance.setTotMonthCrBal10(rs.getBigDecimal("tot_month_cr_bal_10"));
      accountBalance.setTotMonthCrBal11(rs.getBigDecimal("tot_month_cr_bal_11"));
      accountBalance.setTotMonthCrBal12(rs.getBigDecimal("tot_month_cr_bal_12"));
      accountBalance.setTotCreditTrans(rs.getBigDecimal("tot_credit_trans"));
      accountBalance.setTotDebitTrans(rs.getBigDecimal("tot_debit_trans"));
      accountBalance.setStatFlag(rs.getString("stat_flag"));
      accountBalance.setStatUpFlag(rs.getString("stat_up_flag"));
      accountBalance.setModifiedDate(rs.getDate("modified_date"));
      accountBalance.setModifiedBy(rs.getString("modified_by"));
      accountBalance.setLastModified(rs.getString("last_modified"));
      return accountBalance;
    }
  }
}
