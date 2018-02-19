package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.MonthBalanceDaoDecorator;
import org.ums.domain.model.immutable.accounts.MonthBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableMonthBalance;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentMonthBalance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 09-Feb-18.
 */
public class PersistentMonthBalanceDao extends MonthBalanceDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentMonthBalanceDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<MutableMonthBalance> getExistingMonthBalanceBasedOnAccountBalance(List<MutableAccountBalance> pAccountBalances, Long pMonthId) {
    List<Long> accountBalanceIdList = pAccountBalances.stream().map(a->a.getId()).collect(Collectors.toList());
    String query="select * from DT_MONTH_BALANCE where MONTH_ID=:monthId AND ACCOUNT_BALANCE_ID IN ( " +
        "  :accountBalanceId" +
        ")";
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("monthId", pMonthId);
    parameters.addValue("accountBalanceId", accountBalanceIdList);
    return mNamedParameterJdbcTemplate.query(query, parameters, new PersistentMonthBalanceRowMapper());
  }

  @Override
  public List<Long> create(List<MutableMonthBalance> pMutableList) {
    String query="insert into DT_MONTH_BALANCE(ID, ACCOUNT_BALANCE_ID, MONTH_ID, TOT_MONTH_DB_BALANCE, TOT_MONTH_CR_BALANCE)   " +
        "    VALUES (?,?,?,?,?)";
    List<Object[]> params = getCreateParamList(pMutableList);
    mJdbcTemplate.batchUpdate(query, params);
    return params.stream().map(param-> (Long) param[0])
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public int update(List<MutableMonthBalance> pMutableList) {
    String query =
        "UPDATE DT_MONTH_BALANCE SET TOT_MONTH_CR_BALANCE=?, TOT_MONTH_DB_BALANCE=? WHERE  "
            + "ACCOUNT_BALANCE_ID=? AND MONTH_ID=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList)).length;
  }

  private List<Object[]> getCreateParamList(List<MutableMonthBalance> pMutableMonthBalances) {
    List<Object[]> params = new ArrayList<>();
    for(MutableMonthBalance monthBalance : pMutableMonthBalances) {
      params.add(new Object[] {monthBalance.getId(), monthBalance.getAccountBalanceId(),
          monthBalance.getMonth().getId(), monthBalance.getTotalMonthDebitBalance(),
          monthBalance.getTotalMonthCreditBalance()});
    }
    return params;
  }

  private List<Object[]> getUpdateParamList(List<MutableMonthBalance> pMutableMonthBalances) {
    List<Object[]> params = new ArrayList<>();
    for(MutableMonthBalance monthBalance : pMutableMonthBalances) {
      params.add(new Object[] {monthBalance.getTotalMonthCreditBalance(), monthBalance.getTotalMonthDebitBalance(),
          monthBalance.getAccountBalanceId(), monthBalance.getMonth().getId()});
    }
    return params;
  }

  class PersistentMonthBalanceRowMapper implements RowMapper<MutableMonthBalance> {
    @Override
    public MutableMonthBalance mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableMonthBalance monthBalance = new PersistentMonthBalance();
      monthBalance.setId(pResultSet.getLong("id"));
      monthBalance.setAccountBalanceId(pResultSet.getLong("account_balance_id"));
      monthBalance.setMonthId(pResultSet.getLong("month_id"));
      monthBalance.setTotalMonthDebitBalance(pResultSet.getBigDecimal("tot_month_db_balance"));
      monthBalance.setTotalMonthCreditBalance(pResultSet.getBigDecimal("tot_month_cr_balance"));
      return monthBalance;
    }
  }
}
