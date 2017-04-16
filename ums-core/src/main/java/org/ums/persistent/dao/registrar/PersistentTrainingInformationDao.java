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
      "INSERT INTO EMP_TRAINING_INFO (EMPLOYEE_ID, TRAINING_NAME, TRAINING_INSTITUTE, TRAINING_FROM, TRAINING_TO) VALUES (? ,? ,?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentTrainingInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeTrainingInformationParams(pMutableTrainingInformation)).length;
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

  class RoleRowMapper implements RowMapper<TrainingInformation> {

    @Override
    public TrainingInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableTrainingInformation trainingInformation = new PersistentTrainingInformation();
      trainingInformation.setEmployeeId(resultSet.getInt("employee_id"));
      trainingInformation.setTrainingName(resultSet.getString("training_name"));
      trainingInformation.setTrainingInstitute(resultSet.getString("training_institute"));
      trainingInformation.setTrainingFromDate(resultSet.getString("training_from"));
      trainingInformation.setTrainingToDate(resultSet.getString("training_to"));
      return trainingInformation;
    }
  }
}
