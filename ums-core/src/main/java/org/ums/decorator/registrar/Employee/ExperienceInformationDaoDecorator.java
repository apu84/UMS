package org.ums.decorator.registrar.Employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableExperienceInformation;
import org.ums.manager.registrar.Employee.ExperienceInformationManager;

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
