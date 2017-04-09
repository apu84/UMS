package org.ums.domain.model.mutable.registrar.Employee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.Employee.TrainingInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableTrainingInformation extends TrainingInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final int pEmployeeId);

  void setTrainingName(final String pTrainingName);

  void setTrainingInstitute(final String pTrainingInstitute);

  void setTrainingFromDate(final String pTrainingFromDate);

  void setTrainingToDate(final String pTrainingToDate);
}
