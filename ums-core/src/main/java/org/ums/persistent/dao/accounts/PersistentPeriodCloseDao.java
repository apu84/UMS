package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.PeriodCloseDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.enums.accounts.definitions.OpenCloseFlag;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentPeriodClose;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public class PersistentPeriodCloseDao extends PeriodCloseDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentPeriodCloseDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<PeriodClose> getByCurrentYear(Company pCompany) {
    String query =
        "select * from PERIOD_CLOSE where comp_code=? and FIN_ACCOUNT_YEAR_ID in (select id from FIN_ACCOUNT_YEAR where YEAR_CLOSING_FLAG='O')";
    return mJdbcTemplate.query(query, new Object[] {pCompany.getId()}, new PersistentPeriodCloseRowMapper());
  }

  @Override
  public List<PeriodClose> getByPreviousYear(Company pCompany) {
    String query =
        "SELECT * " + "FROM PERIOD_CLOSE " + "WHERE FIN_ACCOUNT_YEAR_ID IN (SELECT id "
            + "                              FROM FIN_ACCOUNT_YEAR "
            + "                              WHERE comp_code=? and  CURRENT_START_DATE IN (SELECT PREVIOUS_START_DATE "
            + "                                                           FROM FIN_ACCOUNT_YEAR "
            + "                                                           WHERE YEAR_CLOSING_FLAG = 'O'))";
    return mJdbcTemplate.query(query, new Object[] {pCompany.getId()}, new PersistentPeriodCloseRowMapper());
  }

  @Override
  public boolean checkWhetherCurrentOpenedYearExists(Company pCompany) {
    return super.checkWhetherCurrentOpenedYearExists(pCompany);
  }

  @Override
  public int update(MutablePeriodClose pMutable) {
    String queyr = "";
    return super.update(pMutable);
  }

  @Override
  public Long create(MutablePeriodClose pMutable) {
    String query =
        "insert into PERIOD_CLOSE(ID, CLOSE_MONTH, CLOSE_YEAR, PERIOD_CLOSING_FLAG, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY, FIN_ACCOUNT_YEAR_ID, COMP_CODE) VALUES ("
            + ":id, :closeMonth, :closeYear, :periodClosingFlag, :statFlag, :statUpFlag, :modifiedDate, :modifiedBy, :finAccountYearId, :compCode)";
    Map namedParameters = new HashMap();
    namedParameters.put("id", pMutable.getId());
    namedParameters.put("closeMonth", pMutable.getMonth().getId());
    namedParameters.put("closeYear", pMutable.getCloseYear());
    namedParameters.put("periodClosingFlag", pMutable.getPeriodClosingFlag().getValue());
    namedParameters.put("statFlag", pMutable.getStatFlag());
    namedParameters.put("statUpFlag", pMutable.getStatUpFlag());
    namedParameters.put("modifiedDate", pMutable.getModifiedDate());
    namedParameters.put("modifiedBy", pMutable.getModifiedBy());
    namedParameters.put("finAccountYearId", pMutable.getFinancialAccountYear().getId());
    namedParameters.put("compCode", pMutable.getCompany().getId());
    mNamedParameterJdbcTemplate.update(query, namedParameters);
    return pMutable.getId();
  }

  @Override
  public int update(List<MutablePeriodClose> pMutableList) {
    String query = "update PERIOD_CLOSE set PERIOD_CLOSING_FLAG=?, MODIFIED_BY=? ,MODIFIED_DATE=? where id=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutableList)).length;
  }

  private List<Object[]> getUpdateParams(List<MutablePeriodClose> periodCloses) {
    List<Object[]> params = new ArrayList<>();

    for(PeriodClose periodClose : periodCloses) {
      params.add(new Object[] {periodClose.getPeriodClosingFlag().getValue(), periodClose.getModifiedBy(),
          periodClose.getModifiedDate(), periodClose.getId()});
    }
    return params;
  }

  @Override
  public List<Long> create(List<MutablePeriodClose> pMutableList) {
    String query =
        "insert into PERIOD_CLOSE(ID, MONTH_ID, CLOSE_YEAR, PERIOD_CLOSING_FLAG, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY, FIN_ACCOUNT_YEAR_ID, COMP_CODE)  VALUES  "
            + "  (?,?,?,?,?,?,?,?,?,?)";
    List<Object[]> params = getCreateParams(pMutableList);
    mJdbcTemplate.batchUpdate(query, getCreateParams(pMutableList));
    return null;
  }

  private List<Object[]> getCreateParams(List<MutablePeriodClose> periodCloses) {
    List<Object[]> params = new ArrayList<>();

    for(PeriodClose periodClose : periodCloses) {
      params.add(new Object[] {periodClose.getId(), periodClose.getMonth().getId(), periodClose.getCloseYear(),
          periodClose.getPeriodClosingFlag().getValue(), periodClose.getStatFlag(), periodClose.getStatUpFlag(),
          periodClose.getModifiedDate(), periodClose.getModifiedBy(), periodClose.getFinancialAccountYear().getId(),
          periodClose.getCompany().getId()});
    }
    return params;
  }

  class PersistentPeriodCloseRowMapper implements RowMapper<PeriodClose> {
    @Override
    public PeriodClose mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutablePeriodClose periodClose = new PersistentPeriodClose();
      periodClose.setId(rs.getLong("id"));
      periodClose.setMonthId(rs.getLong("month_id"));
      periodClose.setCloseYear(rs.getInt("close_year"));
      periodClose.setPeriodClosingFlag(OpenCloseFlag.get(rs.getString("period_closing_flag")));
      periodClose.setStatFlag(rs.getString("stat_flag"));
      periodClose.setStatUpFlag(rs.getString("stat_up_flag"));
      periodClose.setModifiedDate(rs.getDate("modified_date"));
      periodClose.setModifiedBy(rs.getString("modified_by"));
      periodClose.setFinancialAccountYearId(rs.getLong("fin_account_year_id"));
      periodClose.setCompanyId(rs.getString("comp_code"));
      return periodClose;
    }
  }

}
