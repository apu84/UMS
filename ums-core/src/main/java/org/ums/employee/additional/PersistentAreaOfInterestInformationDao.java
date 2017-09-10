package org.ums.employee.additional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAreaOfInterestInformationDao extends AreaOfInterestInformationDaoDecorator {

  static String INSERT_ONE = "INSERT INTO EMP_AOI_INFO (EMPLOYEE_ID, AOI_ID, LAST_MODIFIED) VALUES (?, ?, "
      + getLastModifiedSql() + ")";

  static String GET_ONE = "SELECT AOI_ID FROM EMP_AOI_INFO ";

  static String DELETE_ONE = "DELETE FROM EMP_AOI_INFO ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAreaOfInterestInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveAreaOfInterestInformation(
      final List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getAreaOfInterestInformationParams(pMutableAreaOfInterestInformation)).length;
  }

  private List<Object[]> getAreaOfInterestInformationParams(
      final List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AreaOfInterestInformation areaOfInterestInformation : pMutableAreaOfInterestInformation) {
      params.add(new Object[] {areaOfInterestInformation.getEmployeeId(),
          areaOfInterestInformation.getAreaOfInterest().getId()});
    }
    return params;
  }

  @Override
  public List<AreaOfInterestInformation> getAreaOfInterestInformation(final String pEmployeeId) {
    String query = GET_ONE + " WHERE EMPLOYEE_ID=?";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId},
        new PersistentAreaOfInterestInformationDao.RoleRowMapper());
  }

  @Override
  public int deleteAreaOfInterestInformation(final String pEmployeeId) {
    String query = DELETE_ONE + " WHERE EMPLOYEE_ID=?";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  class RoleRowMapper implements RowMapper<AreaOfInterestInformation> {

    @Override
    public AreaOfInterestInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAreaOfInterestInformation persistentAreaOfInterestInformation =
          new PersistentAreaOfInterestInformation();
      persistentAreaOfInterestInformation.setAreaOfInterestId(resultSet.getInt("AOI_ID"));
      return persistentAreaOfInterestInformation;
    }
  }
}
