package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.ExperienceInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;
import org.ums.persistent.model.registrar.PersistentExperienceInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentExperienceInformationDao extends ExperienceInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_EXPERIENCE_INFO (EMPLOYEE_ID, EXPERIENCE_INSTITUTE, EXPERIENCE_DESIGNATION, EXPERIENCE_FROM, EXPERIENCE_TO) VALUES (?,?,?,?,?)";

  static String GET_ONE =
      "Select EMPLOYEE_ID, EXPERIENCE_INSTITUTE, EXPERIENCE_DESIGNATION, EXPERIENCE_FROM, EXPERIENCE_TO From EMP_EXPERIENCE_INFO";

  static String DELETE_ALL = "DELETE FROM EMP_EXPERIENCE_INFO";

  private JdbcTemplate mJdbcTemplate;

  public PersistentExperienceInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeExperienceInformationParams(pMutableExperienceInformation)).length;
  }

  @Override
  public int deleteExperienceInformation(String pEmployeeId) {
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  private List<Object[]> getEmployeeExperienceInformationParams(
      List<MutableExperienceInformation> pMutableExperienceInformation) {
    List<Object[]> params = new ArrayList<>();
    for(ExperienceInformation experienceInformation : pMutableExperienceInformation) {
      params.add(new Object[] {experienceInformation.getEmployeeId(), experienceInformation.getExperienceInstitute(),
          experienceInformation.getDesignation(), experienceInformation.getExperienceFromDate(),
          experienceInformation.getExperienceToDate()});

    }
    return params;
  }

  @Override
  public List<ExperienceInformation> getEmployeeExperienceInformation(final String employeeId) {
    String query = GET_ONE + " Where employee_id = ?";
    return mJdbcTemplate
        .query(query, new Object[] {employeeId}, new PersistentExperienceInformationDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<ExperienceInformation> {

    @Override
    public ExperienceInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableExperienceInformation experienceInformation = new PersistentExperienceInformation();
      experienceInformation.setEmployeeId(resultSet.getString("employee_id"));
      experienceInformation.setExperienceInstitute(resultSet.getString("experience_institute"));
      experienceInformation.setDesignation(resultSet.getString("experience_designation"));
      experienceInformation.setExperienceFromDate(resultSet.getString("experience_from"));
      experienceInformation.setExperienceToDate(resultSet.getString("experience_to"));
      return experienceInformation;
    }
  }

}
