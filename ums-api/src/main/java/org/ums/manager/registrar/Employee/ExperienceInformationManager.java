package org.ums.manager.registrar.Employee;

import org.ums.domain.model.immutable.registrar.Employee.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableExperienceInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ExperienceInformationManager extends
    ContentManager<ExperienceInformation, MutableExperienceInformation, Integer> {

  int saveExperienceInformation(final MutableExperienceInformation pMutableExperienceInformation);

  List<ExperienceInformation> getEmployeeExperienceInformation(final int pEmployeeId);
}
