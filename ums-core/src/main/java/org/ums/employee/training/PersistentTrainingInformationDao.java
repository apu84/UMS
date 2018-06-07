package org.ums.employee.training;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentTrainingInformationDao extends TrainingInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_TRAINING_INFO (ID, EMPLOYEE_ID, TRAINING_NAME, TRAINING_INSTITUTE, TRAINING_FROM, TRAINING_TO, "
          + "TRAINING_DURATION, TRAINING_DURATION_STRING, CATEGORY, LAST_MODIFIED) VALUES (?, ?, ? ,? ,?, ?, ?, ?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ALL =
      "SELECT ID, EMPLOYEE_ID, TRAINING_NAME, TRAINING_INSTITUTE, TRAINING_FROM, TRAINING_TO, TRAINING_DURATION, TRAINING_DURATION_STRING, CATEGORY, LAST_MODIFIED FROM EMP_TRAINING_INFO";

  static String DELETE_ONE = "DELETE FROM EMP_TRAINING_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_TRAINING_INFO SET TRAINING_NAME=?, TRAINING_INSTITUTE=?, TRAINING_FROM=?, TRAINING_TO=?, TRAINING_DURATION = ?, TRAINING_DURATION_STRING = ?, CATEGORY = ?, LAST_MODIFIED="
          + getLastModifiedSql() + " ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_TRAINING_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentTrainingInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableTrainingInformation pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getEmployeeId(), pMutable.getTrainingName(),
        pMutable.getTrainingInstitute(), pMutable.getTrainingFromDate(), pMutable.getTrainingToDate(),
        pMutable.getTrainingDuration(), pMutable.getTrainingDurationString(), pMutable.getTrainingCategoryId());
    return id;
  }

  @Override
  public TrainingInformation get(final Long pId) {
    String query = GET_ALL + " WHERE ID = ? ";
    return mJdbcTemplate
        .queryForObject(query, new Object[] {pId}, new PersistentTrainingInformationDao.RoleRowMapper());
  }

  @Override
  public List<TrainingInformation> get(final String pEmployeeId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ? ORDER BY TRAINING_TO DESC ";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new PersistentTrainingInformationDao.RoleRowMapper());
  }

  @Override
  public int update(MutableTrainingInformation pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getTrainingName(), pMutable.getTrainingInstitute(),
        pMutable.getTrainingFromDate(), pMutable.getTrainingToDate(), pMutable.getTrainingDuration(),
        pMutable.getTrainingDurationString(), pMutable.getTrainingCategoryId(), pMutable.getId(),
        pMutable.getEmployeeId());
  }

  @Override
  public int delete(MutableTrainingInformation pMutable) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<TrainingInformation> {

    @Override
    public TrainingInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentTrainingInformation trainingInformation = new PersistentTrainingInformation();
      trainingInformation.setId(resultSet.getLong("id"));
      trainingInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      trainingInformation.setTrainingName(resultSet.getString("TRAINING_NAME"));
      trainingInformation.setTrainingInstitute(resultSet.getString("TRAINING_INSTITUTE"));
      trainingInformation.setTrainingFromDate(resultSet.getDate("TRAINING_FROM"));
      trainingInformation.setTrainingToDate(resultSet.getDate("TRAINING_TO"));
      trainingInformation.setTrainingDuration(resultSet.getInt("TRAINING_DURATION"));
      trainingInformation.setTrainingDurationString(resultSet.getString("TRAINING_DURATION_STRING"));
      trainingInformation.setTrainingCategoryId(resultSet.getInt("CATEGORY"));
      return trainingInformation;
    }
  }
}
