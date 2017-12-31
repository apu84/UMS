package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.AccountBalanceDaoDecorator;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentAccountBalance;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public class PersistentAccountBalanceDao extends AccountBalanceDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAccountBalanceDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  private class AccountBalanceRowMapper implements RowMapper<AccountBalance> {
    @Override
    public AccountBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableAccountBalance accountBalance = new PersistentAccountBalance();
      accountBalance.setId(rs.getLong("id"));
      accountBalance.setFinStartDate(rs.getDate("fin_start_date"));
      accountBalance.setFinEndDate(rs.getDate("fin_end_date"));
      accountBalance.setAccountCode(rs.getInt("account_code"));
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
      accountBalance.setStatFlag(rs.getString("stat_flag"));
      accountBalance.setStatUpFlag(rs.getString("stat_up_flag"));
      accountBalance.setModifiedDate(rs.getDate("modified_date"));
      accountBalance.setModifiedBy(rs.getString("modified_by"));
      return accountBalance;
    }
  }
}
