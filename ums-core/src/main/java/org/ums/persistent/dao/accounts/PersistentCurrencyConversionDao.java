package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.CurrencyConversionDaoDecorator;
import org.ums.domain.model.immutable.accounts.CurrencyConversion;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.domain.model.mutable.accounts.MutableCurrencyConversion;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentCurrencyConversion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistentCurrencyConversionDao extends CurrencyConversionDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  String SELECT_ALL = "SELECT " + "  ID, " + "  COMP_CODE, " + "  CURRENCY_ID, " + "  CONV_FACTOR, "
      + "  RCONV_FACTOR, " + "  BCONV_FACTOR, " + "  RBCONV_FACTOR, " + "  DEFAULT_COMP, " + "  STAT_FLAG, "
      + "  STAT_UP_FLAG, " + "  MODIFIED_DATE, " + "  MODIFIED_BY " + "FROM MST_CURRENCY";

  public PersistentCurrencyConversionDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<CurrencyConversion> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentCurrencyConversionRowMapper());
  }

  class PersistentCurrencyConversionRowMapper implements RowMapper<CurrencyConversion> {
    @Override
    public CurrencyConversion mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableCurrencyConversion currencyConversion = new PersistentCurrencyConversion();
      currencyConversion.setId(pResultSet.getLong("id"));
      currencyConversion.setCompanyId(pResultSet.getString("comp_code"));
      currencyConversion.setCurrencyId(pResultSet.getLong("currency_id"));
      currencyConversion.setConversionFactor(pResultSet.getBigDecimal("conv_factor"));
      currencyConversion.setReverseConversionFactor(pResultSet.getBigDecimal("rconv_factor"));
      currencyConversion.setBaseConversionFactor(pResultSet.getBigDecimal("bconv_factor"));
      currencyConversion.setReverseBaseConversionFactor(pResultSet.getBigDecimal("rbconv_factor"));
      currencyConversion.setModifiedDate(pResultSet.getDate("modified_date"));
      currencyConversion.setModifiedBy(pResultSet.getString("modified_by"));
      return currencyConversion;
    }
  }
}
