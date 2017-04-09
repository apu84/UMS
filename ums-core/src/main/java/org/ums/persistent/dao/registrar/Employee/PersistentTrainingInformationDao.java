package org.ums.persistent.dao.registrar.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.Employee.TrainingInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.ExperienceInformation;
import org.ums.domain.model.immutable.registrar.Employee.TrainingInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableTrainingInformation;
import org.ums.persistent.model.registrar.Employee.PersistentTrainingInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentTrainingInformationDao extends TrainingInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_TRAINING_INFO (EMPLOYEE_ID, TRAINING_NAME, TRAINING_INSTITUTE, TRAINING_FROM, TRAINING_TO) VALUES (? ,? ,?, ?, ?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentTrainingInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveTrainingInformation(MutableTrainingInformation pMutableTrainingInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableTrainingInformation.getEmployeeId(),
        pMutableTrainingInformation.getTrainingName(), pMutableTrainingInformation.getTrainingInstitute(),
        pMutableTrainingInformation.getTrainingFromDate(), pMutableTrainingInformation.getTrainingToDate());
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
