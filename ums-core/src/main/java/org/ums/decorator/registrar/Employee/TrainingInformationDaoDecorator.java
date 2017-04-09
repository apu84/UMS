package org.ums.decorator.registrar.Employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.TrainingInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableTrainingInformation;
import org.ums.manager.registrar.Employee.TrainingInformationManager;

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
