package org.ums.employee.training;

import org.ums.employee.training.TrainingInformation;
import org.ums.employee.training.MutableTrainingInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface TrainingInformationManager extends
    ContentManager<TrainingInformation, MutableTrainingInformation, Long> {

  List<TrainingInformation> get(final String pEmployeeId);

  boolean exists(final String pEmployeeId);
}
