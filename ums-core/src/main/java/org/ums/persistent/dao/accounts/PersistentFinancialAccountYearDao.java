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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    String query = "SELECT * FROM FIN_ACCOUNT_YEAR order by current_start_date desc";
    return mJdbcTemplate.query(query, new FinancialAccountYearRowMapper())
            .stream()
            .map(f->(FinancialAccountYear) f)
            .collect(Collectors.toList());
  }

  @Override
  public MutableFinancialAccountYear getOpenedFinancialAccountYear() {
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
        "update FIN_ACCOUNT_YEAR set CURRENT_START_DATE=:currStartDate ,CURRENT_END_DATE=:currEndDate, "
            + "  PREVIOUS_START_DATE=:previousStartDate , PREVIOUS_END_DATE=:previousEndDate , MODIFIED_BY=:modifiedBy , MODIFIED_DATE=:modifiedDate , BOOK_CLOSING_FLAG=:bookClosingFlag , YEAR_CLOSING_FLAG=:yearClosingFlag where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("currStartDate", pMutable.getCurrentStartDate());
    parameterMap.put("currEndDate", pMutable.getCurrentEndDate());
    parameterMap.put("previousStartDate", pMutable.getPreviousStartDate());
    parameterMap.put("previousEndDate", pMutable.getPreviousEndDate());
    parameterMap.put("modifiedBy", pMutable.getModifiedBy());
    parameterMap.put("modifiedDate", pMutable.getModifiedDate());
    parameterMap.put("bookClosingFlag", pMutable.getBookClosingFlag().getValue());
    parameterMap.put("yearClosingFlag", pMutable.getYearClosingFlag().getValue());
    parameterMap.put("id", pMutable.getId());
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
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
  public void deleteAll() {
    String query = "delete from FIN_ACCOUNT_YEAR";
    mJdbcTemplate.update(query);
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

  @Override
  public boolean exists(Date pStartDate, Date pEndDate) {
    String query =
        "select count(*) from FIN_ACCOUNT_YEAR where (CURRENT_START_DATE>=:startDate and CURRENT_END_DATE<=:startDate) or "
            + "                                     (CURRENT_START_DATE>=:endDate and CURRENT_END_DATE<=:endDate)";
    Map parameterMap = new HashMap();
    parameterMap.put("startDate", pStartDate);
    parameterMap.put("endDate", pEndDate);
    int count = mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class);
    return count == 0 ? false : true;
  }

  class FinancialAccountYearRowMapper implements RowMapper<MutableFinancialAccountYear> {
    @Override
    public MutableFinancialAccountYear mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableFinancialAccountYear financialAccountYear = new PersistentFinancialAccountYear();
      financialAccountYear.setStringId(rs.getLong("id"));
      financialAccountYear.setId(rs.getLong("id"));
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
