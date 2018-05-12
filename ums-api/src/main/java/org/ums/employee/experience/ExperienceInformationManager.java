package org.ums.employee.experience;

import org.ums.manager.ContentManager;

import java.util.List;

public interface ExperienceInformationManager extends
    ContentManager<ExperienceInformation, MutableExperienceInformation, Long> {

  List<ExperienceInformation> get(final String pEmployeeId);

  boolean exists(final String pEmployeeId);
}
