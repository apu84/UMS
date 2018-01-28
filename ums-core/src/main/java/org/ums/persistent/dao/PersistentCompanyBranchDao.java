package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.CompanyBranchDaoDecorator;
import org.ums.domain.model.immutable.CompanyBranch;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentCompanyBranch;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class PersistentCompanyBranchDao extends CompanyBranchDaoDecorator {

  String SELECT_ALL = "SELECT ID, COMP_ID, NAME FROM COMP_BRANCH";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCompanyBranchDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public CompanyBranch get(Long pId) {
    String query = SELECT_ALL + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, ((rs, rowNum) -> new PersistentCompanyBranch(rs.getLong("id"), rs.getLong("comp_id"), rs.getString("name"))));
  }
}
