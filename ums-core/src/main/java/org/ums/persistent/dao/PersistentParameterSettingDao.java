package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ParameterSettingDaoDecorator;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentParameterSetting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by My Pc on 3/14/2016.
 */
public class PersistentParameterSettingDao extends ParameterSettingDaoDecorator {

  static String SELECT_ALL =
      "SELECT PS_ID,SEMESTER_ID,PARAMETER_ID, START_DATE, END_DATE,LAST_MODIFIED FROM MST_PARAMETER_SETTING";
  static String INSERT_ONE =
      "INSERT INTO MST_PARAMETER_SETTING(PS_ID, SEMESTER_ID,PARAMETER_ID,START_DATE,END_DATE,LAST_MODIFIED)"
          + "VALUES(?, ?,?,?,?," + getLastModifiedSql() + ")";
  static String UPDATE_ONE =
      "UPDATE MST_PARAMETER_SETTING SET SEMESTER_ID=?, PARAMETER_ID=?, START_DATE = ?, END_DATE = ?, LAST_MODIFIED="
          + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM MST_PARAMETER_SETTING ";
  static String ORDER_BY = " ORDER BY PS_ID";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentParameterSettingDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<ParameterSetting> getAll() {
    String query = SELECT_ALL + ORDER_BY;
    return mJdbcTemplate.query(query, new ParameterSettingRowMapper());
  }

  @Override
  public ParameterSetting get(Long pId) {
    String query = SELECT_ALL + " WHERE PS_ID=? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ParameterSettingRowMapper());
  }

  @Override
  public ParameterSetting getBySemesterAndParameterId(String parameter, int semesterId) {
    String query =
        "SELECT PS_ID,SEMESTER_ID,PARAMETER_ID, START_DATE, END_DATE,LAST_MODIFIED FROM MST_PARAMETER_SETTING where SEMESTER_ID=? and  parameter_id in (select PARAMETER_ID from MST_PARAMETER where PARAMETER=?)";
    List<ParameterSetting> settings =
        mJdbcTemplate.query(query, new Object[] {semesterId, parameter}, new ParameterSettingRowMapper());
    return !settings.isEmpty() ? settings.get(0) : null;
  }

  @Override
  public int update(MutableParameterSetting pMutable) {
    String query = UPDATE_ONE + " WHERE PS_ID=? ";
    return mJdbcTemplate.update(query, pMutable.getSemester().getId(), pMutable.getParameter().getId(),
        pMutable.getStartDate(), pMutable.getEndDate(), pMutable.getId());
  }

  @Override
  public int delete(MutableParameterSetting pMutable) {
    String query = DELETE_ONE + " WHERE PS_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableParameterSetting pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getSemester().getId(), pMutable.getParameter().getId(),
        pMutable.getStartDate(), pMutable.getEndDate());
    return id;
  }

  @Override
  public List<ParameterSetting> getBySemester(int semesterId) {
    String query = SELECT_ALL + " WHERE SEMESTER_ID=? ";
    return mJdbcTemplate.query(query, new Object[] {semesterId}, new ParameterSettingRowMapper());
  }

  class ParameterSettingRowMapper implements RowMapper<ParameterSetting> {
    @Override
    public ParameterSetting mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentParameterSetting parameterSetting = new PersistentParameterSetting();
      parameterSetting.setId(pResultSet.getLong("PS_ID"));
      parameterSetting.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      parameterSetting.setParameterId(pResultSet.getString("PARAMETER_ID"));
      parameterSetting.setStartDate(pResultSet.getTimestamp("START_DATE"));
      parameterSetting.setEndDate(pResultSet.getTimestamp("END_DATE"));
      parameterSetting.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return parameterSetting;
    }
  }
}
