package org.ums.employee.training;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.enums.registrar.TrainingCategory;

import java.io.Serializable;
import java.util.Date;

public interface TrainingInformation extends Serializable, EditType<MutableTrainingInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  String getTrainingName();

  String getTrainingInstitute();

  Date getTrainingFromDate();

  Date getTrainingToDate();

  int getTrainingDuration();

  String getTrainingDurationString();

  TrainingCategory getTrainingCategory();

  int getTrainingCategoryId();
}
