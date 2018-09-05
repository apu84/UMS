package org.ums.ems.profilemanagement.training;

import org.ums.manager.ContentManager;

import java.util.List;

public interface TrainingInformationManager extends
    ContentManager<TrainingInformation, MutableTrainingInformation, Long> {

  List<TrainingInformation> get(final String pEmployeeId);

  boolean exists(final String pEmployeeId);
}
