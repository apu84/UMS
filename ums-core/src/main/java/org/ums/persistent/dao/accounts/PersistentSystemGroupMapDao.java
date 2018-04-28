package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.SystemGroupMapDaoDecorator;
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
      "INSERT INTO SYSTEM_GROUP_MAP(ID, GROUP_TYPE, GROUP_ID, LAST_MODIFIED) VALUES (:id, :groupType, :groupId, "
          + getLastModifiedSql() + " )";
  String UPDATE_ONE = "UPDATE SYSTEM_GROUP_MAP SET GROUP_TYPE= :groupType, GROUP_ID=:groupId, LAST_MODIFIED="
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
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentSystemGroupMapRowMapper());
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
  public String create(MutableSystemGroupMap pMutable) {
    return super.create(pMutable);
  }

  @Override
  public List<String> create(List<MutableSystemGroupMap> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(String pId) {
    return super.exists(pId);
  }

  @Override
  public int count(List<MutableSystemGroupMap> pMutableList) {
    return super.count(pMutableList);
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
    return parameter;
  }

  class PersistentSystemGroupMapRowMapper implements RowMapper<SystemGroupMap> {
    @Override
    public SystemGroupMap mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableSystemGroupMap systemGroupMap = new PersistentSystemGroupMap();
      systemGroupMap.setId(rs.getString("id"));
      systemGroupMap.setGroupType(GroupType.get(rs.getString("group_type")));
      systemGroupMap.setGroupId(rs.getLong("group_id"));
      return systemGroupMap;
    }
  }
}
