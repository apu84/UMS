package org.ums.persistent.dao.accounts;

import io.reactivex.Observable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;
import org.ums.decorator.accounts.AccountBalanceDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentAccountBalance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public class PersistentAccountBalanceDao extends AccountBalanceDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  String INSERT_ONE =
      "INSERT INTO MST_ACCOUNT_BALANCE(ID, FIN_START_DATE, FIN_END_DATE, YEAR_OPEN_BALANCE, YEAR_OPEN_BALANCE_TYPE, "
          + "                                TOT_MONTH_DB_BAL_04, TOT_MONTH_CR_BAL_04, TOT_MONTH_DB_BAL_05, "
          + "                                TOT_MONTH_CR_BAL_05, TOT_MONTH_DB_BAL_06, TOT_MONTH_CR_BAL_06, TOT_MONTH_DB_BAL_07, "
          + "                                TOT_MONTH_CR_BAL_07, TOT_MONTH_DB_BAL_08, TOT_MONTH_CR_BAL_08, TOT_MONTH_DB_BAL_09, "
          + "                                TOT_MONTH_CR_BAL_09, TOT_MONTH_DB_BAL_10, TOT_MONTH_CR_BAL_10, TOT_MONTH_DB_BAL_11, "
          + "                                TOT_MONTH_CR_BAL_11, TOT_MONTH_DB_BAL_12, TOT_MONTH_CR_BAL_12, TOT_MONTH_DB_BAL_01, "
          + "                                TOT_MONTH_CR_BAL_01, TOT_MONTH_DB_BAL_02, TOT_MONTH_CR_BAL_02, TOT_MONTH_DB_BAL_03, "
          + "                                TOT_MONTH_CR_BAL_03, TOT_DEBIT_TRANS, TOT_CREDIT_TRANS, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE) "
          + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  public PersistentAccountBalanceDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public AccountBalance getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate, Account pAccount) {
    return super.getAccountBalance(pFinancialStartDate, pFinancialEndDate, pAccount);
  }

  @Override
  public List<MutableAccountBalance> getAccountBalance(Date pFinancialStartDate, Date pFinancialEndDate, List<Account> pAccounts) {
   List<Long> accountCode= new ArrayList<>();
   accountCode = pAccounts.stream().map(a->a.getAccountCode()).collect(Collectors.toList());
    String query="select * from MST_ACCOUNT_BALANCE where FIN_START_DATE=? and FIN_END_DATE=? and ACCOUNT_CODE in (" + org.apache.commons.lang.StringUtils.join(accountCode,",") +
        ")";
    return mJdbcTemplate.query(query, new Object[]{pFinancialStartDate, pFinancialEndDate}, new AccountBalanceRowMapper());
  }

  @Override
  public Long insertFromAccount(MutableAccountBalance pAccountBalance) {
    String query =
        "INSERT INTO MST_ACCOUNT_BALANCE (ID,FIN_START_DATE, FIN_END_DATE, ACCOUNT_CODE,YEAR_OPEN_BALANCE, YEAR_OPEN_BALANCE_TYPE, TOT_DEBIT_TRANS, TOT_CREDIT_TRANS, MODIFIED_DATE, MODIFIED_BY) "
            + " VALUES(?,?,?,?,?,?,?,?,?,?)";
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(query, id, pAccountBalance.getFinStartDate(), pAccountBalance.getFinEndDate(), pAccountBalance
        .getAccountCode(), pAccountBalance.getYearOpenBalance(), pAccountBalance.getYearOpenBalanceType().getValue(),
        pAccountBalance.getTotDebitTrans(), pAccountBalance.getTotCreditTrans(), pAccountBalance.getModifiedDate(),
        pAccountBalance.getModifiedBy());
    return id;
  }

  @Override
  public int update(List<MutableAccountBalance> pMutableList) {
    String query =
        "UPDATE MST_ACCOUNT_BALANCE "
            + "SET TOT_MONTH_CR_BAL_01 = ?, TOT_MONTH_CR_BAL_02 = ?, TOT_MONTH_CR_BAL_03 = ?, TOT_MONTH_CR_BAL_04 = ?, "
            + "  TOT_MONTH_CR_BAL_05   = ?, TOT_MONTH_CR_BAL_06 = ?, TOT_MONTH_CR_BAL_07 = ?, TOT_MONTH_CR_BAL_08 = ?, "
            + "  TOT_MONTH_CR_BAL_09   = ?, TOT_MONTH_CR_BAL_10 = ?, "
            + "  TOT_MONTH_CR_BAL_11   = ?, TOT_MONTH_CR_BAL_12 = ?, TOT_DEBIT_TRANS = ?, TOT_CREDIT_TRANS = ?, STAT_FLAG = ?, "
            + "  STAT_UP_FLAG          = ?, MODIFIED_DATE = ? "
            + "WHERE FIN_START_DATE = ? AND FIN_END_DATE = ? AND ACCOUNT_CODE = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList)).length;
  }

  private List<Object[]> getUpdateParamList(List<MutableAccountBalance> pAccountBalanceList) {
    List<Object[]> params = new ArrayList<>();

    for(AccountBalance accountBalance : pAccountBalanceList) {
      params.add(new Object[] {accountBalance.getTotMonthDbBal01(), accountBalance.getTotMonthDbBal02(),
          accountBalance.getTotMonthDbBal03(), accountBalance.getTotMonthDbBal04(),
          accountBalance.getTotMonthDbBal05(), accountBalance.getTotMonthDbBal06(),
          accountBalance.getTotMonthDbBal07(), accountBalance.getTotMonthDbBal08(),
          accountBalance.getTotMonthDbBal09(), accountBalance.getTotMonthDbBal10(),
          accountBalance.getTotMonthDbBal11(), accountBalance.getTotMonthDbBal12(), accountBalance.getTotDebitTrans(),
          accountBalance.getTotCreditTrans(), accountBalance.getStatFlag(), accountBalance.getFinStartDate(),
          accountBalance.getFinEndDate(), accountBalance.getAccountCode()});
    }

    return params;
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
      accountBalance.setTotCreditTrans(rs.getBigDecimal("tot_credit_trans"));
      accountBalance.setTotDebitTrans(rs.getBigDecimal("tot_debit_trans"));
      accountBalance.setStatFlag(rs.getString("stat_flag"));
      accountBalance.setStatUpFlag(rs.getString("stat_up_flag"));
      accountBalance.setModifiedDate(rs.getDate("modified_date"));
      accountBalance.setModifiedBy(rs.getString("modified_by"));
      return accountBalance;
    }
  }
}
