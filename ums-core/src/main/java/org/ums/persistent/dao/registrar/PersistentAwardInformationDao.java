package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.AwardInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.persistent.model.registrar.PersistentAwardInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAwardInformationDao extends AwardInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_AWARD_INFO (EMPLOYEE_ID, AWARD_NAME, INSTITUTION, AWARDED_YEAR, AWARD_SHORT_DESCRIPTION) VALUES (? ,? ,?, ?, ?)";

  static String GET_ONE =
      "Select EMPLOYEE_ID, AWARD_NAME, INSTITUTION, AWARDED_YEAR, AWARD_SHORT_DESCRIPTION From EMP_AWARD_INFO";

  static String DELETE_ALL = "DELETE FROM EMP_AWARD_INFO";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAwardInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveAwardInformation(List<MutableAwardInformation> pMutableAwardInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeAwardInformationParams(pMutableAwardInformation)).length;
  }

  @Override
  public int deleteAwardInformation(String pEmployeeId) {
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  private List<Object[]> getEmployeeAwardInformationParams(List<MutableAwardInformation> pMutableAwardInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AwardInformation awardInformation : pMutableAwardInformation) {
      params.add(new Object[] {awardInformation.getEmployeeId(), awardInformation.getAwardName(),
          awardInformation.getAwardInstitute(), awardInformation.getAwardedYear(),
          awardInformation.getAwardShortDescription()});

    }
    return params;
  }

  @Override
  public List<AwardInformation> getEmployeeAwardInformation(final String employeeId) {
    String query = GET_ONE + " Where employee_id = ?";
    return mJdbcTemplate.query(query, new Object[] {employeeId}, new PersistentAwardInformationDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<AwardInformation> {
    @Override
    public AwardInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableAwardInformation awardInformation = new PersistentAwardInformation();
      awardInformation.setEmployeeId(resultSet.getString("employee_id"));
      awardInformation.setAwardName(resultSet.getString("award_name"));
      awardInformation.setAwardInstitute(resultSet.getString("institution"));
      awardInformation.setAwardedYear(resultSet.getString("awarded_year"));
      awardInformation.setAwardShortDescription(resultSet.getString("award_short_description"));
      return awardInformation;
    }
  }
}
