package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.TrainingInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.immutable.registrar.TrainingInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.domain.model.mutable.registrar.MutableTrainingInformation;
import org.ums.persistent.model.registrar.PersistentTrainingInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentTrainingInformationDao extends TrainingInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_TRAINING_INFO (EMPLOYEE_ID, TRAINING_NAME, TRAINING_INSTITUTE, TRAINING_FROM, TRAINING_TO, LAST_MODIFIED) VALUES (? ,? ,?, ?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ONE =
      "Select ID, EMPLOYEE_ID, TRAINING_NAME, TRAINING_INSTITUTE, TRAINING_FROM, TRAINING_TO, LAST_MODIFIED From EMP_TRAINING_INFO";

  static String DELETE_ALL = "DELETE FROM EMP_TRAINING_INFO";

  private JdbcTemplate mJdbcTemplate;

  public PersistentTrainingInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeTrainingInformationParams(pMutableTrainingInformation)).length;
  }

  @Override
  public int deleteTrainingInformation(String pEmployeeId) {
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  private List<Object[]> getEmployeeTrainingInformationParams(
      List<MutableTrainingInformation> pMutableTrainingInformation) {
    List<Object[]> params = new ArrayList<>();
    for(TrainingInformation trainingInformation : pMutableTrainingInformation) {
      params.add(new Object[] {trainingInformation.getEmployeeId(), trainingInformation.getTrainingName(),
          trainingInformation.getTrainingInstitute(), trainingInformation.getTrainingFromDate(),
          trainingInformation.getTrainingToDate()});

    }
    return params;
  }

  @Override
  public List<TrainingInformation> getEmployeeTrainingInformation(final String employeeId) {
    String query = GET_ONE + " Where employee_id = ?";
    return mJdbcTemplate.query(query, new Object[] {employeeId}, new PersistentTrainingInformationDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<TrainingInformation> {

    @Override
    public TrainingInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableTrainingInformation trainingInformation = new PersistentTrainingInformation();
      trainingInformation.setId(resultSet.getInt("id"));
      trainingInformation.setEmployeeId(resultSet.getString("employee_id"));
      trainingInformation.setTrainingName(resultSet.getString("training_name"));
      trainingInformation.setTrainingInstitute(resultSet.getString("training_institute"));
      trainingInformation.setTrainingFromDate(resultSet.getString("training_from"));
      trainingInformation.setTrainingToDate(resultSet.getString("training_to"));
      return trainingInformation;
    }
  }
}
