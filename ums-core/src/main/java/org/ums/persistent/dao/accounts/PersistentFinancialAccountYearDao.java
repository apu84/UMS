package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    return mJdbcTemplate.query(query, new FinancialAccountYearRowMapper());
  }

  @Override
  public FinancialAccountYear getOpenedFinancialAccountYear() {
    String query = "SELECT * FROM FIN_ACCOUNT_YEAR WHERE YEAR_CLOSING_FLAG='O'";
    return mJdbcTemplate.queryForObject(query, new FinancialAccountYearRowMapper());
  }

  @Override
  public FinancialAccountYear get(Long pId) {
    String query = "select * from fin_account_year where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FinancialAccountYearRowMapper());
  }

  @Override
  public FinancialAccountYear validate(FinancialAccountYear pReadonly) {
    return super.validate(pReadonly);
  }

  @Override
  public int update(MutableFinancialAccountYear pMutable) {
    String query =
        "update FIN_ACCOUNT_YEAR set CURRENT_START_DATE=? ,CURRENT_END_DATE=?, "
            + "  PREVIOUS_START_DATE=? , PREVIOUS_END_DATE=? , MODIFIED_BY=? , MODIFIED_DATE=? , BOOK_CLOSING_FLAG=? , YEAR_CLOSING_FLAG=? where id=?";
    return mJdbcTemplate.update(query, pMutable.getCurrentStartDate(), pMutable.getCurrentEndDate(),
        pMutable.getPreviousStartDate(), pMutable.getPreviousEndDate(), pMutable.getModifiedBy(),
        pMutable.getModifiedDate(), pMutable.getBookClosingFlag().getValue(), pMutable.getYearClosingFlag().getValue(),
        pMutable.getId());
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
    String query =
        "insert into FIN_ACCOUNT_YEAR(id, CURRENT_START_DATE, CURRENT_END_DATE, PREVIOUS_START_DATE, PREVIOUS_END_DATE,BOOK_CLOSING_FLAG, YEAR_CLOSING_FLAG, MODIFIED_DATE, MODIFIED_BY) "
            + "  values(:id, :currentStartDate, :currentEndDate, :previousStartDate, :previousEndDate, 'O', 'O', :modifiedDate, :modifiedBy)";
    Long id = mIdGenerator.getNumericId();
    pMutable.setId(id);
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(pMutable);
    return new Long(mNamedParameterJdbcTemplate.update(query, namedParameters));
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
      return financialAccountYear;
    }
  }
}
