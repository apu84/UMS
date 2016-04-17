package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ParameterSettingDaoDecorator;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.persistent.model.PersistentParameterSetting;

import javax.ws.rs.DELETE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by My Pc on 3/14/2016.
 */
public class PersistentParameterSettingDao extends ParameterSettingDaoDecorator {

  static String SELECT_ALL ="SELECT PS_ID,SEMESTER_ID,PARAMETER_ID,TO_CHAR(START_DATE,'DD/MM/YYYY') START_DATE,TO_CHAR(END_DATE,'DD/MM/YYYY') END_DATE,LAST_MODIFIED FROM MST_PARAMETER_SETTING" ;
  static String INSERT_ONE = "INSERT INTO MST_PARAMETER_SETTING(SEMESTER_ID,PARAMETER_ID,START_DATE,END_DATE,LAST_MODIFIED)"+
      "VALUES(?,?,to_date(?,'DD/MM/YYYY'),to_date(?,'DD/MM/YYYY'),"+ getLastModifiedSql()+")";
  static String UPDATE_ONE = "UPDATE MST_PARAMETER_SETTING SET SEMESTER_ID=?, PARAMETER_ID=?, START_DATE = to_date(?,'DD/MM/YYYY'), END_DATE = to_date(?,'DD/MM/YYYY'), LAST_MODIFIED="+getLastModifiedSql()+" ";
  static String DELETE_ONE = "DELETE FROM MST_PARAMETER_SETTING ";
  static String ORDER_BY = " ORDER BY PS_ID";

  private JdbcTemplate mJdbcTemplate;

  public PersistentParameterSettingDao(final JdbcTemplate pJdbcTemplate){
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<ParameterSetting> getAll() throws Exception {
    String query = SELECT_ALL + ORDER_BY;
    List<ParameterSetting> test = mJdbcTemplate.query(query,new ParameterSettingRowMapper());
    return mJdbcTemplate.query(query,
        new ParameterSettingRowMapper()
    );
  }

  @Override
  public ParameterSetting get(String pId) throws Exception {
    String query = SELECT_ALL+" WHERE PS_ID=? ";
    return mJdbcTemplate.queryForObject(query,new Object[]{pId}, new ParameterSettingRowMapper());
  }

  @Override
  public ParameterSetting getBySemesterAndParameterId(int parameterId, int semesterId) {
    String query = SELECT_ALL+" WHERE PARAMETER_ID=? AND SEMESTER_ID=?";
    return mJdbcTemplate.queryForObject(query,new Object[]{parameterId,semesterId},new ParameterSettingRowMapper());
  }

  @Override
  public int update(MutableParameterSetting pMutable) throws Exception {
    String query = UPDATE_ONE+" WHERE PS_ID=? ";
    return mJdbcTemplate.update(query,
          pMutable.getSemester().getId(),
          pMutable.getParameter().getId(),
          pMutable.getStartDate(),
          pMutable.getEndDate(),
          pMutable.getId()
        );
  }

  @Override
  public int delete(MutableParameterSetting pMutable) throws Exception {
    String query = DELETE_ONE+" WHERE PS_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableParameterSetting pMutable) throws Exception {

    String query = INSERT_ONE;
    return mJdbcTemplate.update(query,
          pMutable.getSemester().getId(),
          pMutable.getParameter().getId(),
          pMutable.getStartDate(),
          pMutable.getEndDate()
        );
  }

  @Override
  public List<ParameterSetting> getBySemester(int semesterId) {
    String query = SELECT_ALL+" WHERE SEMESTER_ID=? ";
    return mJdbcTemplate.query(query,new Object[]{semesterId}, new ParameterSettingRowMapper());
  }

  class ParameterSettingRowMapper implements RowMapper<ParameterSetting>{
    @Override
    public ParameterSetting mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentParameterSetting parameterSetting = new PersistentParameterSetting();
      parameterSetting.setId(pResultSet.getString("PS_ID"));
      parameterSetting.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      parameterSetting.setParameterId(pResultSet.getString("PARAMETER_ID"));
      parameterSetting.setStartDate(pResultSet.getString("START_DATE"));
      parameterSetting.setEndDate(pResultSet.getString("END_DATE"));
      parameterSetting.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return parameterSetting;
    }
  }
}
