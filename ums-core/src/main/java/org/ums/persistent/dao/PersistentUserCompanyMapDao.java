package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.CompanyDaoDecorator;
import org.ums.decorator.UserCompanyMapDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.immutable.accounts.UserCompanyMap;
import org.ums.domain.model.mutable.MutableUserCompanyMap;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.enums.accounts.definitions.account.balance.AccountType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.dao.accounts.PersistentSystemAccountMapDao;
import org.ums.persistent.model.PersistentCompany;
import org.ums.persistent.model.PersistentUserCompanyMap;
import org.ums.persistent.model.accounts.PersistentSystemAccountMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentUserCompanyMapDao extends UserCompanyMapDaoDecorator {

  String SELECT_ALL = "select ID, USER_ID, COMPANY_ID,LAST_MODIFIED FROM USER_COMPANY_MAP";

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentUserCompanyMapDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public UserCompanyMap get(Long pId) {
    String query = SELECT_ALL + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, (rs, rowNum) -> new PersistentUserCompanyMap(rs.getLong("id"), rs.getString("user_id"), rs.getString("company_id")));
  }

  @Override
  public List<UserCompanyMap> getCompanyList(String pUserId) {
    SELECT_ALL = "select ID, USER_ID, COMPANY_ID,LAST_MODIFIED FROM USER_COMPANY_MAP";
    String query = SELECT_ALL + " where user_id=?";
    return mJdbcTemplate
        .query(query, new Object[] {pUserId}, new PersistentUserCompanyMapDao.UserCompanyMapRowMapper());

  }

  class UserCompanyMapRowMapper implements RowMapper<UserCompanyMap> {
    @Override
    public UserCompanyMap mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUserCompanyMap userCompanyMap = new PersistentUserCompanyMap();
      userCompanyMap.setId(rs.getLong("id"));
      userCompanyMap.setUserId(rs.getString("user_id"));
      userCompanyMap.setCompanyId(rs.getString("company_id"));
      userCompanyMap.setLastModified(rs.getString("last_modified"));
      return userCompanyMap;
    }
  }

}
