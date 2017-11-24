package org.ums.employee.experience;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentExperienceInformationDao extends ExperienceInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_EXPERIENCE_INFO (ID, EMPLOYEE_ID, EXPERIENCE_INSTITUTE, EXPERIENCE_DESIGNATION, "
          + "EXPERIENCE_FROM, EXPERIENCE_TO, EXPERIENCE_DURATION, EXPERIENCE_DURATION_STRING, CATEGORY, LAST_MODIFIED) VALUES (?,?,?,?,?,?,?,?,?,"
          + getLastModifiedSql() + ")";

  static String GET_ONE =
      "Select ID, EMPLOYEE_ID, EXPERIENCE_INSTITUTE, EXPERIENCE_DESIGNATION, EXPERIENCE_FROM, "
          + "EXPERIENCE_TO, EXPERIENCE_DURATION, EXPERIENCE_DURATION_STRING, CATEGORY, LAST_MODIFIED From EMP_EXPERIENCE_INFO";

  static String DELETE_ALL = "DELETE FROM EMP_EXPERIENCE_INFO";

  static String UPDATE_ALL =
      "UPDATE EMP_EXPERIENCE_INFO SET EXPERIENCE_INSTITUTE=?, EXPERIENCE_DESIGNATION=?, EXPERIENCE_FROM=?, "
          + "EXPERIENCE_TO=?, EXPERIENCE_DURATION = ?, EXPERIENCE_DURATION_STRING = ?, CATEGORY = ?, LAST_MODIFIED="
          + getLastModifiedSql() + " ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentExperienceInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
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
      params.add(new Object[] {mIdGenerator.getNumericId(), experienceInformation.getEmployeeId(),
          experienceInformation.getExperienceInstitute(), experienceInformation.getDesignation(),
          experienceInformation.getExperienceFromDate(), experienceInformation.getExperienceToDate(),
          experienceInformation.getExperienceDuration(), experienceInformation.getExperienceDurationString(),
          experienceInformation.getExperienceCategoryId()});

    }
    return params;
  }

  @Override
  public List<ExperienceInformation> getEmployeeExperienceInformation(final String employeeId) {
    String query = GET_ONE + " Where employee_id = ?";
    return mJdbcTemplate
        .query(query, new Object[] {employeeId}, new PersistentExperienceInformationDao.RoleRowMapper());
  }

  @Override
  public int updateExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation) {
    String query = UPDATE_ALL + "WHERE EMPLOYEE_ID = ? and ID = ? ";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutableExperienceInformation)).length;
  }

  private List<Object[]> getUpdateParams(List<MutableExperienceInformation> pMutableExperienceInformation) {
    List<Object[]> params = new ArrayList<>();
    for(ExperienceInformation pExperienceInformation : pMutableExperienceInformation) {
      params.add(new Object[] {pExperienceInformation.getExperienceInstitute(),
          pExperienceInformation.getDesignation(), pExperienceInformation.getExperienceFromDate(),
          pExperienceInformation.getExperienceToDate(), pExperienceInformation.getExperienceDuration(),
          pExperienceInformation.getExperienceDurationString(), pExperienceInformation.getExperienceCategoryId(),
          pExperienceInformation.getEmployeeId(), pExperienceInformation.getId()});
    }
    return params;
  }

  @Override
  public int deleteExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation) {
    String query = DELETE_ALL + " WHERE ID=? AND EMPLOYEE_ID=?";
    return mJdbcTemplate.batchUpdate(query, getDeleteParams(pMutableExperienceInformation)).length;
  }

  private List<Object[]> getDeleteParams(List<MutableExperienceInformation> pMutableExperienceInformation) {
    List<Object[]> params = new ArrayList<>();
    for(ExperienceInformation pExperienceInformation : pMutableExperienceInformation) {
      params.add(new Object[] {pExperienceInformation.getId(), pExperienceInformation.getEmployeeId()});
    }
    return params;
  }

  class RoleRowMapper implements RowMapper<ExperienceInformation> {

    @Override
    public ExperienceInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentExperienceInformation experienceInformation = new PersistentExperienceInformation();
      experienceInformation.setId(resultSet.getLong("id"));
      experienceInformation.setEmployeeId(resultSet.getString("employee_id"));
      experienceInformation.setExperienceInstitute(resultSet.getString("experience_institute"));
      experienceInformation.setDesignation(resultSet.getString("experience_designation"));
      experienceInformation.setExperienceFromDate(resultSet.getString("experience_from"));
      experienceInformation.setExperienceToDate(resultSet.getString("experience_to"));
      experienceInformation.setExperienceDuration(resultSet.getInt("experience_duration"));
      experienceInformation.setExperienceDurationString(resultSet.getString("experience_duration_string"));
      experienceInformation.setExperienceCategoryId(resultSet.getInt("category"));
      return experienceInformation;
    }
  }

}
