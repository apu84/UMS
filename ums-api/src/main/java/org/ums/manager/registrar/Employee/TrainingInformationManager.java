package org.ums.manager.registrar.Employee;

import org.ums.domain.model.immutable.registrar.Employee.TrainingInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableTrainingInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface TrainingInformationManager extends
    ContentManager<TrainingInformation, MutableTrainingInformation, Integer> {

  int saveTrainingInformation(final MutableTrainingInformation pMutableTrainingInformation);

  List<TrainingInformation> getEmployeeTrainingInformation(final int pEmployeeId);
}
