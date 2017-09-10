package org.ums.employee.experience;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableExperienceInformation extends ExperienceInformation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setExperienceInstitute(final String pExperienceInstitute);

  void setDesignation(final String pDesignation);

  void setExperienceFromDate(final String pExperienceFromDate);

  void setExperienceToDate(final String pExperienceToDate);
}
