package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.TrainingInformation;
import org.ums.domain.model.mutable.registrar.MutableTrainingInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface TrainingInformationManager extends
    ContentManager<TrainingInformation, MutableTrainingInformation, Long> {

  int saveTrainingInformation(final List<MutableTrainingInformation> pMutableTrainingInformation);

  List<TrainingInformation> getEmployeeTrainingInformation(final String pEmployeeId);

  int deleteTrainingInformation(final String pEmployeeId);

  int updateTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation);

  int deleteTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation);

}
