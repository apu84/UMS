package org.ums.ems.profilemanagement.academic;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class AcademicInformationDaoDecorator extends
    ContentDaoDecorator<AcademicInformation, MutableAcademicInformation, Long, AcademicInformationManager> implements
    AcademicInformationManager {

  @Override
  public List<AcademicInformation> get(String pEmployeeId) {
    return getManager().get(pEmployeeId);
  }

  @Override
  public boolean exists(String pEmployeeId) {
    return getManager().exists(pEmployeeId);
  }
}
