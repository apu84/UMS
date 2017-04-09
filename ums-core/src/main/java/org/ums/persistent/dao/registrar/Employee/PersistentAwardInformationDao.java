package org.ums.persistent.dao.registrar.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.Employee.AwardInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.AwardInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableAwardInformation;
import org.ums.persistent.model.registrar.Employee.PersistentAcademicInformation;
import org.ums.persistent.model.registrar.Employee.PersistentAwardInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentAwardInformationDao extends AwardInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_AWARD_INFO (EMPLOYEE_ID, AWARD_NAME, INSTITUTION, AWARDED_YEAR, AWARDED_SHORT_DESCRIPTION) VALUES (? ,? ,?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAwardInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveAwardInformation(MutableAwardInformation pMutableAwardInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableAwardInformation.getEmployeeId(),
        pMutableAwardInformation.getAwardName(), pMutableAwardInformation.getAwardInstitute(),
        pMutableAwardInformation.getAwardedYear(), pMutableAwardInformation.getAwardShortDescription());
  }

  class RoleRowMapper implements RowMapper<AwardInformation> {
    @Override
    public AwardInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableAwardInformation awardInformation = new PersistentAwardInformation();
      awardInformation.setEmployeeId(resultSet.getInt("employee_id"));
      awardInformation.setAwardName(resultSet.getString("award_name"));
      awardInformation.setAwardInstitute(resultSet.getString("institution"));
      awardInformation.setAwardedYear(resultSet.getString("awarded_year"));
      awardInformation.setAwardShortDescription(resultSet.getString("award_short_description"));
      return awardInformation;
    }
  }
}
