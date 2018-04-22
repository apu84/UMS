package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.LmsTypeDaoDecorator;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsType;
import org.ums.enums.common.DurationType;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.Gender;
import org.ums.enums.common.SalaryType;
import org.ums.persistent.model.common.PersistentLmsType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-May-17.
 */
public class PersistentLmsTypeDao extends LmsTypeDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  String SELECT_ALL = "SELECT * FROM LMS_TYPE ";
  String UPDATE_ONE = "UPDATE LMS_TYPE SET NAME=?, TYPE=?, DURATION=?, LAST_MODIFIED=" + getLastModifiedSql()
      + ", max_duration=?, max_simultaneous_duration=?, duration_type=?, salary_type=? ";

  public PersistentLmsTypeDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<LmsType> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new LmsTypeRowMapper());
  }

  @Override
  public List<LmsType> getLmsTypes(EmployeeLeaveType pType, Gender pGender) {
    String query = "";
    if(pGender == Gender.MALE) {
      query = SELECT_ALL + " where type=1 or type=? order by id";
      return mJdbcTemplate.query(query, new Object[] {pType.getId()}, new LmsTypeRowMapper());
    }
    else {
      query = SELECT_ALL + " where type=1 or type=? or type=? order by id";
      return mJdbcTemplate.query(query, new Object[] {EmployeeLeaveType.FEMALE_LEAVE.getId(), pType.getId()},
          new LmsTypeRowMapper());
    }
  }

  @Override
  public LmsType get(Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new LmsTypeRowMapper());
  }

  @Override
  public LmsType validate(LmsType pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableLmsType pMutable) {
    String update = UPDATE_ONE + " where id=?";
    return mJdbcTemplate.update(update, pMutable.getName(), pMutable.getEmployeeLeaveType().getId(), pMutable
        .getDuration(), pMutable.getMaxDuration(), pMutable.getMaxSimultaneousDuration(), pMutable.getDurationType()
        .getId(), pMutable.getSalaryType().getId(), pMutable.getId());
  }

  @Override
  public int update(List<MutableLmsType> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableLmsType pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableLmsType> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Integer create(MutableLmsType pMutable) {
    return super.create(pMutable);
  }

  @Override
  public List<Integer> create(List<MutableLmsType> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(Integer pId) {
    return super.exists(pId);
  }

  class LmsTypeRowMapper implements RowMapper<LmsType> {
    @Override
    public LmsType mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentLmsType lmsType = new PersistentLmsType();
      lmsType.setId(rs.getInt("id"));
      lmsType.setName(rs.getString("name"));
      lmsType.setType(EmployeeLeaveType.get(rs.getInt("type")));
      lmsType.setDuration(rs.getInt("duration"));
      lmsType.setMaxDuration(rs.getInt("max_duration"));
      lmsType.setMaxSimultaneousDuration(rs.getInt("max_simultaneous_duration"));
      if(rs.getInt("duration_type") != 0)
        lmsType.setDurationType(DurationType.get(rs.getInt("duration_type")));
      if(rs.getInt("salary_type") != 0)
        lmsType.setSalaryType(SalaryType.get(rs.getInt("salary_type")));
      lmsType.setLastModified(rs.getString("last_modified"));
      lmsType.setAuthorizeRoleId(rs.getInt("authorize_role"));
      lmsType.setSpecialAuthorizeRoleId(rs.getInt("special_authorize_role"));
      return lmsType;
    }
  }

}
