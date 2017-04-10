package org.ums.decorator.registrar.employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.employee.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableExperienceInformation;
import org.ums.manager.registrar.employee.ExperienceInformationManager;

import java.util.List;

public class ExperienceInformationDaoDecorator extends
    ContentDaoDecorator<ExperienceInformation, MutableExperienceInformation, Integer, ExperienceInformationManager>
    implements ExperienceInformationManager {

  @Override
  public int saveExperienceInformation(MutableExperienceInformation pMutableExperienceInformation) {
    return getManager().saveExperienceInformation(pMutableExperienceInformation);
  }

  @Override
  public List<ExperienceInformation> getEmployeeExperienceInformation(int pEmployeeId) {
    return getManager().getEmployeeExperienceInformation(pEmployeeId);
  }
}
