package org.ums.employee.training;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.registrar.TrainingCategory;

public interface MutableTrainingInformation extends TrainingInformation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setTrainingName(final String pTrainingName);

  void setTrainingInstitute(final String pTrainingInstitute);

  void setTrainingFromDate(final String pTrainingFromDate);

  void setTrainingToDate(final String pTrainingToDate);

  void setTrainingDuration(final int pTrainingDuration);

  void setTrainingDurationString(final String pTrainingDurationString);

  void setTrainingCategory(final TrainingCategory pTrainingCategory);

  void setTrainingCategoryId(final int pTrainingCategoryId);
}
