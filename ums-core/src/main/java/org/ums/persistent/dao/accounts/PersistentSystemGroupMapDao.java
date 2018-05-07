package org.ums.persistent.dao.accounts;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.ums.decorator.accounts.SystemGroupMapDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentSystemGroupMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public class PersistentSystemGroupMapDao extends SystemGroupMapDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  String SELECT_ALL = "SELECT * FROM SYSTEM_GROUP_MAP";
  String INSERT_ONE =
      "INSERT INTO SYSTEM_GROUP_MAP(ID, GROUP_TYPE, GROUP_ID, COMP_ID, MODIFIED_BY, MODIFIED_DATE, LAST_MODIFIED) VALUES (:id, :groupType, :groupId, :companyId, :modifiedBy, :modifiedDate, "
          + getLastModifiedSql() + " )";
  String UPDATE_ONE =
      "UPDATE SYSTEM_GROUP_MAP SET GROUP_TYPE= :groupType, GROUP_ID=:groupId,COMP_ID=:companyId, MODIFIED_BY=:modifiedBy, MODIFIED_DATE=:modifiedDate, LAST_MODIFIED="
          + getLastModifiedSql() + " WHERE ID=:id";
  String DELETE_ONE = "DELETE FROM SYSTEM_GROUP_MAP WHERE ID=: id";

  public PersistentSystemGroupMapDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<SystemGroupMap> getAll() {
    String query = SELECT_ALL;
    return mNamedParameterJdbcTemplate.query(query, new PersistentSystemGroupMapRowMapper());
  }

  @Override
  public SystemGroupMap get(String pId) {
    String query = SELECT_ALL + " WHERE ID=:id";
    try {
      Map parameterMap = new HashMap();
      parameterMap.put("id", pId);
      return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentSystemGroupMapRowMapper());
    } catch(EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public SystemGroupMap validate(SystemGroupMap pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableSystemGroupMap pMutable) {
    String query = UPDATE_ONE;
    return mNamedParameterJdbcTemplate.update(query, getInsertOrUpdateParameters(pMutable));
  }

  @Override
  public int update(List<MutableSystemGroupMap> pMutableList) {
    String query = UPDATE_ONE;
    Map<String, Object>[] parameters = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(query, parameters).length;
  }

  @Override
  public int delete(MutableSystemGroupMap pMutable) {
    String query = DELETE_ONE + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pMutable.getId());
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int delete(List<MutableSystemGroupMap> pMutableList) {
    if (pMutableList.size() == 0)
      return 0;
    String query = DELETE_ONE + " where id in(:id)";
    Map parameterMap = new HashMap();
    List<String> idList = pMutableList.stream().map(p -> p.getId()).collect(Collectors.toList());
    parameterMap.put("id", idList);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  @Async
  public int delete(Group pGroup, Company pCompany) {
    String query = "DELETE FROM SYSTEM_GROUP_MAP WHERE GROUP_ID=:groupId AND COMP_ID=:compCode";
    Map parameterMap = new HashMap();
    parameterMap.put("groupId", pGroup.getId());
    parameterMap.put("compCode", pCompany.getId());
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public String create(MutableSystemGroupMap pMutable) {
    String query = INSERT_ONE;
    mNamedParameterJdbcTemplate.update(query, getInsertOrUpdateParameters(pMutable));
    return pMutable.getId();
  }

  @Override
  public List<String> create(List<MutableSystemGroupMap> pMutableList) {
    String query = INSERT_ONE;
    Map<String, Object>[] parameters = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, parameters);
    return pMutableList.stream().map(p -> p.getId()).collect(Collectors.toList());
  }

  @Override
  public boolean exists(String pId) {
    return get(pId) == null ? false : true;
  }

  @Override
  public int count(List<MutableSystemGroupMap> pMutableList) {
    if(pMutableList.size() == 0)
      return 0;
    String query = "select count(*) from system_group_map  where id in (:idList)";
    Map parameterMap = new HashMap();
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class);
  }

  @Override
  public List<SystemGroupMap> getAllByCompany(Company pCompany) {
    String query = SELECT_ALL + " WHERE COMP_ID=:companyId";
    Map parameterMap = new HashMap();
    parameterMap.put("companyId", pCompany.getId());
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentSystemGroupMapRowMapper());
  }

  @Override
  public void invalidateCache(MutableSystemGroupMap pMutable) {
    super.invalidateCache(pMutable);
  }

  private Map<String, Object>[] getParameterObjects(List<MutableSystemGroupMap> pMutableSystemGroupMaps) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableSystemGroupMaps.size()];
    for(int i = 0; i < pMutableSystemGroupMaps.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableSystemGroupMaps.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableSystemGroupMap pMutableSystemGroupMap) {
    Map parameter = new HashMap();
    parameter.put("id", pMutableSystemGroupMap.getGroupType().getValue());
    parameter.put("groupType", pMutableSystemGroupMap.getGroupType().getValue());
    parameter.put("groupId", pMutableSystemGroupMap.getGroup().getId());
    parameter.put("companyId", pMutableSystemGroupMap.getCompany().getId());
    parameter.put("modifiedBy", pMutableSystemGroupMap.getModifiedBy());
    parameter.put("modifiedDate", pMutableSystemGroupMap.getModifiedDate());
    return parameter;
  }

  class PersistentSystemGroupMapRowMapper implements RowMapper<SystemGroupMap> {
    @Override
    public SystemGroupMap mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableSystemGroupMap systemGroupMap = new PersistentSystemGroupMap();
      systemGroupMap.setId(rs.getString("id"));
      systemGroupMap.setGroupType(GroupType.get(rs.getString("group_type")));
      systemGroupMap.setGroupId(rs.getLong("group_id"));
      systemGroupMap.setCompanyId(rs.getString("comp_id"));
      systemGroupMap.setModifiedBy(rs.getString("modified_by"));
      systemGroupMap.setModifiedDate(rs.getDate("modified_date"));
      return systemGroupMap;
    }
  }
}
