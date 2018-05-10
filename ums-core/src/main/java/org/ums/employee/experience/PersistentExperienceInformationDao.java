package org.ums.employee.experience;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentExperienceInformationDao extends ExperienceInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_EXPERIENCE_INFO (ID, EMPLOYEE_ID, EXPERIENCE_INSTITUTE, EXPERIENCE_DESIGNATION, "
          + "EXPERIENCE_FROM, EXPERIENCE_TO, EXPERIENCE_DURATION, EXPERIENCE_DURATION_STRING, CATEGORY, LAST_MODIFIED) VALUES (?,?,?,?,?,?,?,?,?,"
          + getLastModifiedSql() + ")";

  static String GET_ALL =
      "SELECT ID, EMPLOYEE_ID, EXPERIENCE_INSTITUTE, EXPERIENCE_DESIGNATION, EXPERIENCE_FROM, "
          + "EXPERIENCE_TO, EXPERIENCE_DURATION, EXPERIENCE_DURATION_STRING, CATEGORY, LAST_MODIFIED FROM EMP_EXPERIENCE_INFO";

  static String DELETE_ONE = "DELETE FROM EMP_EXPERIENCE_INFO";

  static String UPDATE_ONE =
      "UPDATE EMP_EXPERIENCE_INFO SET EXPERIENCE_INSTITUTE=?, EXPERIENCE_DESIGNATION=?, EXPERIENCE_FROM=?, "
          + "EXPERIENCE_TO=?, EXPERIENCE_DURATION = ?, EXPERIENCE_DURATION_STRING = ?, CATEGORY = ?, LAST_MODIFIED="
          + getLastModifiedSql() + " ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_EXPERIENCE_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentExperienceInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableExperienceInformation pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getEmployeeId(), pMutable.getExperienceInstitute(),
        pMutable.getDesignation(), pMutable.getExperienceFromDate(), pMutable.getExperienceToDate(),
        pMutable.getExperienceDuration(), pMutable.getExperienceDurationString(), pMutable.getExperienceCategoryId());
    return id;
  }

  @Override
  public ExperienceInformation get(final Long pId) {
    String query = GET_ALL + " WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentExperienceInformationDao.RoleRowMapper());
  }

  @Override
  public List<ExperienceInformation> get(final String pEmployeeId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ? ORDER BY EXPERIENCE_TO DESC ";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId},
        new PersistentExperienceInformationDao.RoleRowMapper());
  }

  @Override
  public int update(MutableExperienceInformation pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getExperienceInstitute(), pMutable.getDesignation(),
        pMutable.getExperienceFromDate(), pMutable.getExperienceToDate(), pMutable.getExperienceDuration(),
        pMutable.getExperienceDurationString(), pMutable.getExperienceCategoryId(), pMutable.getId(),
        pMutable.getEmployeeId());
  }

  @Override
  public int delete(MutableExperienceInformation pMutable) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<ExperienceInformation> {

    @Override
    public ExperienceInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentExperienceInformation experienceInformation = new PersistentExperienceInformation();
      experienceInformation.setId(resultSet.getLong("ID"));
      experienceInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      experienceInformation.setExperienceInstitute(resultSet.getString("EXPERIENCE_INSTITUTE"));
      experienceInformation.setDesignation(resultSet.getString("EXPERIENCE_DESIGNATION"));
      experienceInformation.setExperienceFromDate(resultSet.getString("EXPERIENCE_FROM"));
      experienceInformation.setExperienceToDate(resultSet.getString("EXPERIENCE_TO"));
      experienceInformation.setExperienceDuration(resultSet.getInt("EXPERIENCE_DURATION"));
      experienceInformation.setExperienceDurationString(resultSet.getString("EXPERIENCE_DURATION_STRING"));
      experienceInformation.setExperienceCategoryId(resultSet.getInt("CATEGORY"));
      return experienceInformation;
    }
  }

}
