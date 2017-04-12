package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.manager.registrar.AcademicInformationManager;

import java.util.List;

public class AcademicInformationDaoDecorator extends
    ContentDaoDecorator<AcademicInformation, MutableAcademicInformation, Integer, AcademicInformationManager> implements
    AcademicInformationManager {
  @Override
  public int saveAcademicInformation(MutableAcademicInformation pMutableAcademicInformation) {
    return getManager().saveAcademicInformation(pMutableAcademicInformation);
  }

  @Override
  public List<AcademicInformation> getEmployeeAcademicInformation(int pEmployeeId) {
    return getManager().getEmployeeAcademicInformation(pEmployeeId);
  }
}
