package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.AccountDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

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

  class PersistentAccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableAccount account = new PersistentAccount();
      account.setStringId(rs.getLong("id"));
      account.setAccountCode(rs.getString("account_code"));
      account.setAccountName(rs.getString("account_name"));
      account.setAccGroupCode(rs.getString("acc_group_code"));
      account.setReserved(rs.getBoolean("reserved"));
      account.setTaxLimit(rs.getBigDecimal("tax_limit"));
      account.setTaxCode(rs.getString("tax_code"));
      account.setDefaultComp(rs.getString("default_code"));
      account.setStatFlag(rs.getString("stat_flag"));
      account.setStatUpFlag(rs.getString("stat_up_flag"));
      account.setModifiedDate(rs.getDate("modified_date"));
      account.setModifiedBy("modified_by");
      return account;
    }
  }
}
