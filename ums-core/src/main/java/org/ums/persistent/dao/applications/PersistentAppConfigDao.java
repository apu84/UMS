package org.ums.persistent.dao.applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.applications.AppConfigDaoDecorator;
import org.ums.domain.model.immutable.applications.AppConfig;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.applications.PersistentAppConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 20-Sep-17.
 */
public class PersistentAppConfigDao extends AppConfigDaoDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(PersistentAppConfigDao.class);

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAppConfigDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class AppConfigRowMapper implements RowMapper<AppConfig> {
    @Override
    public AppConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentAppConfig appConfig = new PersistentAppConfig();
      appConfig.setId(rs.getLong("id"));
      appConfig.setFeeCategoryId(rs.getString("fee_id"));
      appConfig.setValidityPeriod(rs.getInt("validity_period"));
      appConfig.setDepartmentId(rs.getString("dept_id"));
      appConfig.setHeadsForwarding(rs.getInt("head_forwarding") == 1 ? true : false);
      appConfig.setLastModified(rs.getString("last_modified"));
      return appConfig;
    }
  }

}
