package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.ums.decorator.accounts.GroupDaoDecorator;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public class PersistentGroupDao extends GroupDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;
  private SimpleJdbcInsert mSimpleJdbcInsert;

  private String INSERT_ONE =
      "insert into mst_group (id, comp_code, group_code, group_name, main_group, reserved_flag,"
          + " flag, tax_limit, tds_percent, default_comp, stat_flag, stat_up_flag, modified_date, modified_by)"
          + " values (:id, :compCode, :groupCode, :groupName, :mainGroup, :reservedFlag, "
          + ":flag, :taxLimit, :tdsPercent, :defaultComp, :statFlag, :statUpFlag, :modifiedDate, :modifiedBy)";

  public PersistentGroupDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
                            IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Group> getAll() {
    String query = "select * from mst_group";
    return mJdbcTemplate.query(query, new PersistentGroupRowMapper());
  }

  @Override
  public Group get(Long pId) {
    String sql = "select * from mst_group where id= :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", pId);
    return this.mNamedParameterJdbcTemplate.queryForObject(sql, namedParameters, new PersistentGroupRowMapper());
  }

  @Override
  public List<Group> getGroups(Group pGroup) {
    String query = "select * from mst_group where main_group=:mainGroup";
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(pGroup);
    return this.mNamedParameterJdbcTemplate.query(query, namedParameters, new PersistentGroupRowMapper());
  }

  @Override
  public Group validate(Group pReadonly) {
    return super.validate(pReadonly);
  }

  @Override
  public int update(MutableGroup pMutable) {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableGroup> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableGroup pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableGroup> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableGroup pMutable) {
    String query = INSERT_ONE;
    // Map<String, Object> namedParameters = convertObjectToParamMap(pMutable);
    Long id = mIdGenerator.getNumericId();
    pMutable.setStringId(id);
    SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(pMutable);
    return new Long(mNamedParameterJdbcTemplate.update(query, namedParameters));
  }

  @Override
  public List<Long> create(List<MutableGroup> pMutableList) {
    SqlParameterSource[] parameterSources = SqlParameterSourceUtils.createBatch(pMutableList.toArray());
    mNamedParameterJdbcTemplate.batchUpdate(INSERT_ONE, parameterSources);
    return null;
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class PersistentGroupRowMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableGroup group = new PersistentGroup();
      group.setStringId(rs.getLong("id"));
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
      return group;
    }
  }
}
