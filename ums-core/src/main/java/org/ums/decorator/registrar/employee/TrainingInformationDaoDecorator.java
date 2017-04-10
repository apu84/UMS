package org.ums.decorator.registrar.employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.employee.TrainingInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableTrainingInformation;
import org.ums.manager.registrar.employee.TrainingInformationManager;

import java.util.List;

public class TrainingInformationDaoDecorator extends
    ContentDaoDecorator<TrainingInformation, MutableTrainingInformation, Integer, TrainingInformationManager> implements
    TrainingInformationManager {

  @Override
  public int saveTrainingInformation(MutableTrainingInformation pMutableTrainingInformation) {
    return getManager().saveTrainingInformation(pMutableTrainingInformation);
  }

  @Override
  public List<TrainingInformation> getEmployeeTrainingInformation(int pEmployeeId) {
    return getManager().getEmployeeTrainingInformation(pEmployeeId);
  }
}
