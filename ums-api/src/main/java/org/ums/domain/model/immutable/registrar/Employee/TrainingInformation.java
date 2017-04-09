package org.ums.domain.model.immutable.registrar.Employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.Employee.MutableTrainingInformation;

import java.io.Serializable;
import java.util.Date;

public interface TrainingInformation extends Serializable, EditType<MutableTrainingInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  String getTrainingName();

  String getTrainingInstitute();

  String getTrainingFromDate();

  String getTrainingToDate();
}
