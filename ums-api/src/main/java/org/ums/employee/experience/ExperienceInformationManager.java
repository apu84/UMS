package org.ums.employee.experience;

import org.ums.employee.experience.ExperienceInformation;
import org.ums.employee.experience.MutableExperienceInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ExperienceInformationManager extends
    ContentManager<ExperienceInformation, MutableExperienceInformation, Long> {

  int saveExperienceInformation(final List<MutableExperienceInformation> pMutableExperienceInformation);

  List<ExperienceInformation> getEmployeeExperienceInformation(final String pEmployeeId);

  int deleteExperienceInformation(final String pEmployeeId);

  int updateExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation);

  int deleteExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation);
}
