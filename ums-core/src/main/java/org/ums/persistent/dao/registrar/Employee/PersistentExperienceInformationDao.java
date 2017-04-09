package org.ums.persistent.dao.registrar.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.Employee.ExperienceInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableEmployeeInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableExperienceInformation;
import org.ums.persistent.model.registrar.Employee.PersistentExperienceInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentExperienceInformationDao extends ExperienceInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_EXPERIENCE_INFO (EMPLOYEE_ID, EMPLOYEE_INSTITUTE, EXPERIENCE_DESIGNATION, EXPERIENCE_FROM, EXPERIENCE_TO) VALUES (? ,? ,?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentExperienceInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveExperienceInformation(MutableExperienceInformation pMutableExperienceInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableExperienceInformation.getEmployeeId(),
        pMutableExperienceInformation.getExperienceInstitute(), pMutableExperienceInformation.getDesignation(),
        pMutableExperienceInformation.getExperienceFromDate(), pMutableExperienceInformation.getExperienceToDate());
  }

  class RoleRowMapper implements RowMapper<ExperienceInformation> {

    @Override
    public ExperienceInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableExperienceInformation experienceInformation = new PersistentExperienceInformation();
      experienceInformation.setEmployeeId(resultSet.getInt("employee_id"));
      experienceInformation.setExperienceInstitute(resultSet.getString("experience_institute"));
      experienceInformation.setDesignation(resultSet.getString("experience_designation"));
      experienceInformation.setExperienceFromDate(resultSet.getString("experience_from"));
      experienceInformation.setExperienceToDate(resultSet.getString("experience_to"));
      return experienceInformation;
    }
  }

}
