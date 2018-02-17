package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.CompanyDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentCompany;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class PersistentCompanyDao extends CompanyDaoDecorator {

  String SELECT_ALL = "select ID, NAME, SHORT_NAME from MST_COMP";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCompanyDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Company get(String pId) {
    String query = SELECT_ALL + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, (rs, rowNum) -> new PersistentCompany(rs.getString("id"), rs.getString("name"), rs.getString("short_name")));
  }

  @Override
  public Company getDefaultCompany() {
    return get("01");
  }
}
