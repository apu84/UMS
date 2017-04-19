package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableExperienceInformation extends ExperienceInformation, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setExperienceInstitute(final String pExperienceInstitute);

  void setDesignation(final String pDesignation);

  void setExperienceFromDate(final String pExperienceFromDate);

  void setExperienceToDate(final String pExperienceToDate);
}
