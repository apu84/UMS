package org.ums.employee.training;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class TrainingInformationDaoDecorator extends
    ContentDaoDecorator<TrainingInformation, MutableTrainingInformation, Long, TrainingInformationManager> implements
    TrainingInformationManager {

  @Override
  public List<TrainingInformation> get(String pEmployeeId) {
    return getManager().get(pEmployeeId);
  }

  @Override
  public boolean exists(String pEmployeeId) {
    return getManager().exists(pEmployeeId);
  }
}
