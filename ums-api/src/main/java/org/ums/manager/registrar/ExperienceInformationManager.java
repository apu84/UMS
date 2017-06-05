package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ExperienceInformationManager extends
    ContentManager<ExperienceInformation, MutableExperienceInformation, Integer> {

  int saveExperienceInformation(final List<MutableExperienceInformation> pMutableExperienceInformation);

  List<ExperienceInformation> getEmployeeExperienceInformation(final String pEmployeeId);

  int deleteExperienceInformation(final String pEmployeeId);

  int updateExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation);

  int deleteExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation);
}
