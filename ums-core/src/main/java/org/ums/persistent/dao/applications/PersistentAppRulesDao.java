package org.ums.persistent.dao.applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.applications.AppRulesDaoDecorator;
import org.ums.domain.model.immutable.applications.AppRules;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.applications.PersistentAppRules;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 24-Sep-17.
 */
public class PersistentAppRulesDao extends AppRulesDaoDecorator {

  private static final Logger mLogger = LoggerFactory.getLogger(PersistentAppRulesDao.class);

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAppRulesDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class AppRulesRowMapper implements RowMapper<AppRules> {
    @Override
    public AppRules mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentAppRules appRules = new PersistentAppRules();
      appRules.setId(rs.getLong("id"));
      appRules.setFeeCategoryId(rs.getString("fee_id"));
      appRules.setDependentFeeCategoryId(rs.getString("dependent_fee_id"));
      appRules.setLastModified(rs.getString("last_modified"));
      return appRules;
    }
  }
}
