package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.TrainingInformation;
import org.ums.domain.model.mutable.registrar.MutableTrainingInformation;
import org.ums.manager.registrar.TrainingInformationManager;

import java.util.List;

public class TrainingInformationDaoDecorator extends
    ContentDaoDecorator<TrainingInformation, MutableTrainingInformation, Long, TrainingInformationManager> implements
    TrainingInformationManager {

  @Override
  public int saveTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    return getManager().saveTrainingInformation(pMutableTrainingInformation);
  }

  @Override
  public List<TrainingInformation> getEmployeeTrainingInformation(String pEmployeeId) {
    return getManager().getEmployeeTrainingInformation(pEmployeeId);
  }

  @Override
  public int deleteTrainingInformation(String pEmployeeId) {
    return getManager().deleteTrainingInformation(pEmployeeId);
  }

  @Override
  public int updateTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    return getManager().updateTrainingInformation(pMutableTrainingInformation);
  }

  @Override
  public int deleteTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    return getManager().deleteTrainingInformation(pMutableTrainingInformation);
  }
}
