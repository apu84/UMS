package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.FinancialAccountYearDaoDecorator;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.enums.accounts.definitions.financial.account.year.BookClosingFlagType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentFinancialAccountYear;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class PersistentFinancialAccountYearDao extends FinancialAccountYearDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentFinancialAccountYearDao(JdbcTemplate pJdbcTemplate,
                                           NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<FinancialAccountYear> getAll() {
    String query = "SELECT * FROM FIN_ACCOUNT_YEAR";
    return mNamedParameterJdbcTemplate.query(query, new FinancialAccountYearRowMapper());
  }

  @Override
  public FinancialAccountYear get(Long pId) {
    String query = "";
    return super.get(pId);
  }

  @Override
  public FinancialAccountYear validate(FinancialAccountYear pReadonly) {
    return super.validate(pReadonly);
  }

  @Override
  public int update(MutableFinancialAccountYear pMutable) {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableFinancialAccountYear> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableFinancialAccountYear pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableFinancialAccountYear> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableFinancialAccountYear pMutable) {
    return super.create(pMutable);
  }

  @Override
  public List<Long> create(List<MutableFinancialAccountYear> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class FinancialAccountYearRowMapper implements RowMapper<FinancialAccountYear> {
    @Override
    public FinancialAccountYear mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableFinancialAccountYear financialAccountYear = new PersistentFinancialAccountYear();
      financialAccountYear.setStringId(rs.getLong("id"));
      financialAccountYear.setCurrentStartDate(rs.getDate("current_start_date"));
      financialAccountYear.setCurrentEndDate(rs.getDate("current_end_date"));
      financialAccountYear.setPreviousStartDate(rs.getDate("previous_start_date"));
      financialAccountYear.setPreviousEndDate(rs.getDate("previous_end_date"));
      financialAccountYear.setBookClosingFlag(BookClosingFlagType.get(rs.getString("book_closing_flag")));
      financialAccountYear.setItLimit(rs.getBigDecimal("it_limit"));
      financialAccountYear.setYearClosingFlag(YearClosingFlagType.get(rs.getString("year_closing_flag")));
      financialAccountYear.setStatFlag(rs.getString("stat_flag"));
      financialAccountYear.setStatUpFlag(rs.getString("stat_up_flag"));
      financialAccountYear.setModifiedDate(rs.getDate("modified_date"));
      financialAccountYear.setModifiedBy(rs.getString("modified_by"));
      return null;
    }
  }
}
