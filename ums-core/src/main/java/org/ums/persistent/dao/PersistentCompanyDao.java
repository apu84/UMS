package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.CompanyDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentCompany;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class PersistentCompanyDao extends CompanyDaoDecorator {

  String SELECT_ALL = "select ID, NAME, SHORT_NAME, ADDRESS from MST_COMP";
  String INSERT_ONE = "INSERT INTO MST_COMP (ID, NAME, SHORT_NAME, ADDRESS) values(:id, :name, :shortName, :address)";
  String UPDATE_ONE = "UPDATE MST_COMP SET NAME=:name, SHORT_NAME=:shortName, ADDRESS=:address where ID=:id";

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentCompanyDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
  }

  @Override
  public Company get(String pId) {
    String query = SELECT_ALL + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, (rs, rowNum) -> new PersistentCompany(rs.getString("id"), rs.getString("name"), rs.getString("short_name"), rs.getString("address")));
  }

  @Override
  public List<Company> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query,  (rs, rowNum) -> new PersistentCompany(rs.getString("id"), rs.getString("name"), rs.getString("short_name"), rs.getString("address")));
  }

  @Override
  public String create(MutableCompany pMutable) {
    String query=INSERT_ONE;
    Map parameterMap = new HashMap();
    getParameterMap(pMutable, parameterMap);
    mNamedParameterJdbcTemplate.update(query, parameterMap);
    return pMutable.getId();
  }

  private void getParameterMap(MutableCompany pMutable, Map pParameterMap) {
    pParameterMap.put("id", pMutable.getId());
    pParameterMap.put("name", pMutable.getName());
    pParameterMap.put("shortName", pMutable.getShortName());
    pParameterMap.put("address", pMutable.getAddress());
  }

  @Override
  public int update(MutableCompany pMutable) {
    Map parameterMap = new HashMap();
    getParameterMap(pMutable, parameterMap);
    return mNamedParameterJdbcTemplate.update(UPDATE_ONE, parameterMap);
  }

  @Override
  public Company getDefaultCompany() {
    return get("01");
  }


}
