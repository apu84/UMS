package org.ums.manager.registrar.employee;

import org.ums.domain.model.immutable.registrar.employee.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableExperienceInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ExperienceInformationManager extends
    ContentManager<ExperienceInformation, MutableExperienceInformation, Integer> {

  int saveExperienceInformation(final MutableExperienceInformation pMutableExperienceInformation);

  List<ExperienceInformation> getEmployeeExperienceInformation(final int pEmployeeId);
}
