package org.ums.employee.academic;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.employee.academic.AcademicInformation;
import org.ums.employee.academic.MutableAcademicInformation;
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
