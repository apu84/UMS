package org.ums.persistent.dao.accounts;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.ums.decorator.accounts.GroupDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public class PersistentGroupDao extends GroupDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;
  private SimpleJdbcInsert mSimpleJdbcInsert;

  private String INSERT_ONE =
      "insert into mst_group (id,  group_code, group_name, main_group, reserved_flag,"
          + " flag, tax_limit, tds_percent, comp_code, stat_flag, stat_up_flag, modified_date, modified_by, display_code, last_modified)"
          + " values (:id, :groupCode, :groupName, :mainGroup, :reservedFlag, "
          + ":flag, :taxLimit, :tdsPercent, :companyCode, :statFlag, :statUpFlag, :modifiedDate, :modifiedBy, :displayCode,"
          + getLastModifiedSql() + ")";
  private String UPDATE_ONE =
      "update MST_GROUP set GROUP_CODE=:groupCode, GROUP_NAME=:groupName, MAIN_GROUP=:mainGroup, DISPLAY_CODE=:displayCode, LAST_MODIFIED="
          + getLastModifiedSql() + " where id=:id";

  public PersistentGroupDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Group> getExcludingMainGroupList(List<String> pMainGroupCodeList, Company pCompany) {
    if(pMainGroupCodeList.size() == 0)
      return null;
    String query =
        "select * from MST_GROUP where comp_code=:compCode and ( GROUP_CODE in ( "
            + "select DISTINCT group_code from "
            + "(SELECT "
            + "  GROUP_CODE, "
            + "  MAIN_GROUP, "
            + "  LEVEL "
            + "FROM MST_GROUP "
            + "  START WITH  MAIN_GROUP not in (:groupCodeList) "
            + "CONNECT BY NOCYCLE PRIOR GROUP_CODE = MAIN_GROUP)modified_grouop_code) or GROUP_CODE not in (:groupCodeList) )";
    Map parameterMap = new HashMap();
    parameterMap.put("groupCodeList", pMainGroupCodeList);
    parameterMap.put("compCode", pCompany.getId());
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentGroupRowMapper());
  }

  @Override
  public List<Group> getIncludingMainGroupList(List<String> pMainGroupCodeList, Company pCompany) {
    if(pMainGroupCodeList.size() == 0)
      return null;
    String query =
        "select * from MST_GROUP where comp_code=:compCode and ( GROUP_CODE in ( "
            + "select DISTINCT group_code from "
            + "(SELECT "
            + "  GROUP_CODE, "
            + "  MAIN_GROUP, "
            + "  LEVEL "
            + "FROM MST_GROUP "
            + "  START WITH  MAIN_GROUP in (:groupCodeList) "
            + "CONNECT BY NOCYCLE PRIOR GROUP_CODE = MAIN_GROUP)modified_grouop_code) or GROUP_CODE in (:groupCodeList) )";
    Map parameterMap = new HashMap();
    parameterMap.put("groupCodeList", pMainGroupCodeList);
    parameterMap.put("compCode", pCompany.getId());
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentGroupRowMapper());
  }

  @Override
  public List<Group> getAll() {
    String query = "select * from mst_group order by modified_date DESC ";
    return mJdbcTemplate.query(query, new PersistentGroupRowMapper());
  }

  @Override
  public List<Group> getAll(Company pCompany) {
    String query = "select * from mst_group where comp_code=? order by modified_date DESC ";
    return mJdbcTemplate.query(query, new Object[] {pCompany.getId()}, new PersistentGroupRowMapper());
  }

  @Override
  public Group get(Long pId) {
    String sql = "select * from mst_group where id= :id";
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource("id", pId);
      return this.mNamedParameterJdbcTemplate.queryForObject(sql, namedParameters, new PersistentGroupRowMapper());
    } catch(EmptyResultDataAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<Group> getByMainGroup(Group pGroup, Company pCompany) {
    String query =
        "select * from mst_group where main_group=:mainGroup and comp_code=:compCode order by to_number(group_code)";
    Map parameterMap = new HashMap();
    parameterMap.put("mainGroup", pGroup.getMainGroup());
    parameterMap.put("compCode", pCompany.getId());
    return this.mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentGroupRowMapper());
  }

  @Override
  public Group validate(Group pReadonly) {
    return super.validate(pReadonly);
  }

  @Override
  public int update(MutableGroup pMutable) {
    String query = UPDATE_ONE;
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(pMutable);
    return this.mNamedParameterJdbcTemplate.update(query, namedParameters);
  }

  @Override
  public int update(List<MutableGroup> pMutableList) {

    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableGroup pMutable) {
    String query = "delete from mst_group where id=:id";
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(pMutable);
    return mNamedParameterJdbcTemplate.update(query, namedParameters);
  }

  @Override
  public int delete(List<MutableGroup> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableGroup pMutable) {
    String query = INSERT_ONE;
    // Map<String, Object> namedParameters = convertObjectToParamMap(pMutable);
    Map parameterMap = createOrUpdateParameter(pMutable);
    return new Long(mNamedParameterJdbcTemplate.update(query, parameterMap));
  }

  @Override
  public List<Long> create(List<MutableGroup> pMutableList) {
    Map<String, Object>[] parameterMaps = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(INSERT_ONE, parameterMaps);
    return pMutableList.stream()
        .map(p -> p.getId())
        .collect(Collectors.toList());
  }

  private Map<String, Object>[] getParameterObjects(List<MutableGroup> pMutableGroups) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableGroups.size()];
    for(int i = 0; i < pMutableGroups.size(); i++) {
      parameterMaps[i] = createOrUpdateParameter(pMutableGroups.get(i));
    }
    return parameterMaps;
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  Map createOrUpdateParameter(MutableGroup pMutableGroup) {
    Map parameterMap = new HashMap();
    parameterMap.put("id", pMutableGroup.getId() == null ? mIdGenerator.getNumericId() : pMutableGroup.getId());
    parameterMap.put("groupCode", pMutableGroup.getGroupCode());
    parameterMap.put("groupName", pMutableGroup.getGroupName());
    parameterMap.put("mainGroup", pMutableGroup.getMainGroup());
    parameterMap.put("reservedFlag", pMutableGroup.getReservedFlag());
    parameterMap.put("flag", pMutableGroup.getFlag());
    parameterMap.put("taxLimit", pMutableGroup.getTaxLimit());
    parameterMap.put("tdsPercent", pMutableGroup.getTdsPercent());
    parameterMap.put("companyCode", pMutableGroup.getCompCode());
    parameterMap.put("statFlag", pMutableGroup.getStatFlag());
    parameterMap.put("statUpFlag", pMutableGroup.getStatUpFlag());
    parameterMap.put("modifiedDate", pMutableGroup.getModifiedDate());
    parameterMap.put("modifiedBy", pMutableGroup.getModifiedBy());
    parameterMap.put("displayCode", pMutableGroup.getDisplayCode());
    return parameterMap;
  }

  class PersistentGroupRowMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableGroup group = new PersistentGroup();
      group.setId(rs.getLong("id"));
      group.setCompCode(rs.getString("comp_code"));
      group.setGroupCode(rs.getString("group_code"));
      group.setGroupName(rs.getString("group_name"));
      group.setMainGroup(rs.getString("main_group"));
      group.setReservedFlag(rs.getString("reserved_flag"));
      group.setFlag(rs.getString("flag"));
      group.setTaxLimit(rs.getBigDecimal("tax_limit"));
      group.setTdsPercent(rs.getBigDecimal("tds_percent"));
      group.setDefaultComp(rs.getString("default_comp"));
      group.setStatFlag(rs.getString("stat_flag"));
      group.setStatUpFlag(rs.getString("stat_up_flag"));
      group.setModifiedDate(rs.getDate("modified_date"));
      group.setModifiedBy(rs.getString("modified_by"));
      group.setDisplayCode(rs.getString("display_code"));
      group.setLastModified(rs.getString("last_modified"));
      return group;
    }
  }
}
