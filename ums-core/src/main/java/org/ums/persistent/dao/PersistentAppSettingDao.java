package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AppSettingDaoDecorator;
import org.ums.domain.model.immutable.AppSetting;
import org.ums.persistent.model.PersistentAppSetting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by My Pc on 30-Aug-16.
 */
public class PersistentAppSettingDao extends AppSettingDaoDecorator {

  static String SELECT_ALL =
      "SELECT ID,PARAMETER_NAME,PARAMETER_VALUE,DESCRIPTION,DATA_TYPE,LAST_MODIFIED FROM APP_SETTING ";
  static String INSERT_ONE =
      "INSERT INTO APP_SETTING (PARAMETER_NAME,PARAMETER_VALUE,DESCRIPTION,DATA_TYPE,LAST_MODIFIED) "
          + " VALUES (?,?,?,?," + getLastModifiedSql() + ")";
  static String DELETE_ALL = "DELETE FROM APP_SETTING ";
  static String UPDATE_ONE =
      "UPDATE APP_SETTING SET PARAMETER_NAME=?,PARAMETER_VALUE=?,DESCRIPTION=?,DATA_TYPE=?, LAST_MODIFIED= "
          + getLastModifiedSql() + " ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAppSettingDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<AppSetting> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new AppSettingRowMapper());
  }

  @Override
  public AppSetting get(Integer pId) throws Exception {
    String query = SELECT_ALL + " where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new AppSettingRowMapper());
  }

  class AppSettingRowMapper implements RowMapper<AppSetting> {
    @Override
    public AppSetting mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentAppSetting appSetting = new PersistentAppSetting();
      appSetting.setId(pResultSet.getInt("ID"));
      appSetting.setParameterName(pResultSet.getString("PARAMETER_NAME"));
      appSetting.setParameterValue(pResultSet.getString("PARAMETER_VALUE"));
      appSetting.setParameterDescription(pResultSet.getString("DESCRIPTION"));
      appSetting.setDataType(pResultSet.getString("DATA_TYPE"));
      appSetting.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return appSetting;
    }
  }

}
