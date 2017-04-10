package org.ums.domain.model.immutable.registrar.employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.employee.MutableTrainingInformation;

import java.io.Serializable;

public interface TrainingInformation extends Serializable, EditType<MutableTrainingInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  String getTrainingName();

  String getTrainingInstitute();

  String getTrainingFromDate();

  String getTrainingToDate();
}
