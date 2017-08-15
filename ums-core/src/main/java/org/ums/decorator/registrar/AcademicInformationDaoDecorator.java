package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.manager.registrar.AcademicInformationManager;

import java.util.List;

public class AcademicInformationDaoDecorator extends
    ContentDaoDecorator<AcademicInformation, MutableAcademicInformation, Long, AcademicInformationManager> implements
    AcademicInformationManager {
  @Override
  public int saveAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation) {
    return getManager().saveAcademicInformation(pMutableAcademicInformation);
  }

  @Override
  public List<AcademicInformation> getEmployeeAcademicInformation(String pEmployeeId) {
    return getManager().getEmployeeAcademicInformation(pEmployeeId);
  }

  @Override
  public int deleteAcademicInformation(String pEmployeeId) {
    return getManager().deleteAcademicInformation(pEmployeeId);
  }

  @Override
  public int updateAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation) {
    return getManager().updateAcademicInformation(pMutableAcademicInformation);
  }

  @Override
  public int deleteAcademicInformation(List<MutableAcademicInformation> pMutableAcademicInformation) {
    return getManager().deleteAcademicInformation(pMutableAcademicInformation);
  }
}
