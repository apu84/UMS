package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;
import org.ums.manager.registrar.ExperienceInformationManager;

import java.util.List;

public class ExperienceInformationDaoDecorator extends
    ContentDaoDecorator<ExperienceInformation, MutableExperienceInformation, Integer, ExperienceInformationManager>
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
}
