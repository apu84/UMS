package org.ums.employee.experience;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.registrar.ExperienceCategory;

import java.util.Date;

public interface MutableExperienceInformation extends ExperienceInformation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setExperienceInstitute(final String pExperienceInstitute);

  void setDesignation(final String pDesignation);

  void setExperienceFromDate(final Date pExperienceFromDate);

  void setExperienceToDate(final Date pExperienceToDate);

  void setExperienceDuration(final int pExperienceDuration);

  void setExperienceDurationString(final String pExperienceDurationString);

  void setExperienceCategory(final ExperienceCategory pExperienceCategory);

  void setExperienceCategoryId(final int pExperienceCategoryId);
}
