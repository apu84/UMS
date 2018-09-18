package org.ums.ems.profilemanagement.experience;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class ExperienceInformationDaoDecorator extends
    ContentDaoDecorator<ExperienceInformation, MutableExperienceInformation, Long, ExperienceInformationManager>
    implements ExperienceInformationManager {

  @Override
  public List<ExperienceInformation> get(String pEmployeeId) {
    return getManager().get(pEmployeeId);
  }

  @Override
  public boolean exists(String pEmployeeId) {
    return getManager().exists(pEmployeeId);
  }
}
