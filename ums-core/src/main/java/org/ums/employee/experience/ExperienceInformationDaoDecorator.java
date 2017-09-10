package org.ums.employee.experience;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.employee.experience.ExperienceInformation;
import org.ums.employee.experience.MutableExperienceInformation;
import org.ums.manager.registrar.ExperienceInformationManager;

import java.util.List;

public class ExperienceInformationDaoDecorator extends
    ContentDaoDecorator<ExperienceInformation, MutableExperienceInformation, Long, ExperienceInformationManager>
    implements ExperienceInformationManager {

  @Override
  public int saveExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation) {
    return getManager().saveExperienceInformation(pMutableExperienceInformation);
  }

  @Override
  public List<ExperienceInformation> getEmployeeExperienceInformation(String pEmployeeId) {
    return getManager().getEmployeeExperienceInformation(pEmployeeId);
  }

  @Override
  public int deleteExperienceInformation(String pEmployeeId) {
    return getManager().deleteExperienceInformation(pEmployeeId);
  }

  @Override
  public int updateExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation) {
    return getManager().updateExperienceInformation(pMutableExperienceInformation);
  }

  @Override
  public int deleteExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation) {
    return getManager().deleteExperienceInformation(pMutableExperienceInformation);
  }
}
