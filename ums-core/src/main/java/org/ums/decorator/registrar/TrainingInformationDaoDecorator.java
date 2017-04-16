package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.TrainingInformation;
import org.ums.domain.model.mutable.registrar.MutableTrainingInformation;
import org.ums.manager.registrar.TrainingInformationManager;

import java.util.List;

public class TrainingInformationDaoDecorator extends
    ContentDaoDecorator<TrainingInformation, MutableTrainingInformation, Integer, TrainingInformationManager> implements
    TrainingInformationManager {

  @Override
  public int saveTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    return getManager().saveTrainingInformation(pMutableTrainingInformation);
  }

  @Override
  public List<TrainingInformation> getEmployeeTrainingInformation(int pEmployeeId) {
    return getManager().getEmployeeTrainingInformation(pEmployeeId);
  }
}
