package org.ums.manager.registrar.employee;

import org.ums.domain.model.immutable.registrar.employee.TrainingInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableTrainingInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface TrainingInformationManager extends
    ContentManager<TrainingInformation, MutableTrainingInformation, Integer> {

  int saveTrainingInformation(final MutableTrainingInformation pMutableTrainingInformation);

  List<TrainingInformation> getEmployeeTrainingInformation(final int pEmployeeId);
}
