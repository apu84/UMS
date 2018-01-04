package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.PeriodCloseDaoDecorator;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.OpenCloseFlag;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentPeriodClose;

import java.sql.ResultSet;
import java.sql.SQLException;
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
  public List<PeriodClose> getByCurrentYear() {
    return super.getByCurrentYear();
  }

  @Override
  public List<PeriodClose> getByPreviousYear() {
    return super.getByPreviousYear();
  }

  @Override
  public boolean checkWhetherCurrentOpenedYearExists() {
    return super.checkWhetherCurrentOpenedYearExists();
  }

  @Override
  public int update(MutablePeriodClose pMutable) {
    String queyr = "";
    return super.update(pMutable);
  }

  @Override
  public Long create(MutablePeriodClose pMutable) {
    String query =
        "insert into PERIOD_CLOSE(ID, CLOSE_MONTH, CLOSE_YEAR, PERIOD_CLOSING_FLAG, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY, FIN_ACCOUNT_YEAR_ID) VALUES ("
            + ":id, :closeMonth, :closeYear, :periodClosingFlag, :statFlag, :statUpFlag, :modifiedDate, :modifiedBy)";
    Map namedParameters = new HashMap();
    namedParameters.put("id", pMutable.getId());
    namedParameters.put("closeMonth", pMutable.getCloseMonth().getValue());
    namedParameters.put("closeYear", pMutable.getCloseYear());
    namedParameters.put("periodClosingFlag", pMutable.getPeriodClosingFlag().getValue());
    namedParameters.put("statFlag", pMutable.getStatFlag());
    namedParameters.put("statUpFlag", pMutable.getStatUpFlag());
    namedParameters.put("modifiedDate", pMutable.getModifiedDate());
    namedParameters.put("modifiedBy", pMutable.getModifiedBy());
    mNamedParameterJdbcTemplate.update(query, namedParameters);
    return pMutable.getId();
  }

  class PersistentPeriodCloseRowMapper implements RowMapper<PeriodClose> {
    @Override
    public PeriodClose mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutablePeriodClose periodClose = new PersistentPeriodClose();
      periodClose.setId(rs.getLong("id"));
      periodClose.setFinAccountYearId(rs.getLong("fin_account_year_id"));
      periodClose.setCloseMonth(MonthType.get(rs.getInt("close_month")));
      periodClose.setCloseYear(rs.getInt("close_year"));
      periodClose.setPeriodClosingFlag(OpenCloseFlag.get(rs.getString("period_closing_flag")));
      periodClose.setStatFlag(rs.getString("stat_flag"));
      periodClose.setStatUpFlag(rs.getString("stat_up_flag"));
      periodClose.setModifiedDate(rs.getDate("modified_date"));
      periodClose.setModifiedBy(rs.getString("modified_by"));
      return periodClose;
    }
  }

}
