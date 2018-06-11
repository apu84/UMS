package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.CurrencyDaoDecorator;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.enums.accounts.definitions.currency.CurrencyFlag;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentCurrency;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistentCurrencyDao extends CurrencyDaoDecorator {

  String SELECT_ALL =
      "SELECT ID, COMP_CODE, CURRENCY_CODE, CURR_DESCRIPTION, CURRENCY_FLAG, NOTATION, DEFAULT_COMP, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY FROM MST_CURRENCY";

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCurrencyDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Currency> getAll() {
    String query = SELECT_ALL+" ORDER BY CURRENCY_FLAG";
    return mJdbcTemplate.query(query, ((rs, rowNum) -> new PersistentCurrency(
        rs.getLong("id"),
        rs.getString("comp_code"),
        rs.getInt("currency_code"),
        rs.getString("CURR_DESCRIPTION"),
        CurrencyFlag.get(rs.getString("currency_flag")),
        rs.getString("notation"),
        rs.getString("stat_flag"),
        rs.getString("stat_up_flag"),
        rs.getDate("modified_date"),
        rs.getString("modified_by"))));
  }

  @Override
  public Currency get(Long pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, ((rs, rowNum) -> new PersistentCurrency(
        rs.getLong("id"),
        rs.getString("comp_code"),
        rs.getInt("currency_code"),
        rs.getString("CURR_DESCRIPTION"),
        CurrencyFlag.get(rs.getString("currency_flag")),
        rs.getString("notation"),
        rs.getString("stat_flag"),
        rs.getString("stat_up_flag"),
        rs.getDate("modified_date"),
        rs.getString("modified_by")
    )));
  }

  @Override
  public Currency getBaseCurrency() {
    String query = SELECT_ALL + " where CURRENCY_FLAG='B'";
    return mJdbcTemplate.queryForObject(
        query,
        ((rs, rowNum) -> new PersistentCurrency(
            rs.getLong("id"),
            rs.getString("comp_code"),
            rs.getInt("currency_code"),
            rs.getString("CURR_DESCRIPTION"),
            CurrencyFlag.get(rs.getString("currency_flag")),
            rs.getString("notation"),
            rs.getString("stat_flag"),
            rs.getString("stat_up_flag"),
            rs.getDate("modified_date"),
            rs.getString("modified_by")
        ))
    );
  }
}
