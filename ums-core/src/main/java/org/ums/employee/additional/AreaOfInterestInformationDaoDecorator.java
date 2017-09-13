package org.ums.employee.additional;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class AreaOfInterestInformationDaoDecorator
    extends
    ContentDaoDecorator<AreaOfInterestInformation, MutableAreaOfInterestInformation, String, AreaOfInterestInformationManager>
    implements AreaOfInterestInformationManager {
  @Override
  public List<AreaOfInterestInformation> getAreaOfInterestInformation(String pEmployeeId) {
    return getManager().getAreaOfInterestInformation(pEmployeeId);
  }

  @Override
  public int saveAreaOfInterestInformation(List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation) {
    return getManager().saveAreaOfInterestInformation(pMutableAreaOfInterestInformation);
  }

  @Override
  public int updateAreaOfInformation(List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation) {
    return getManager().updateAreaOfInformation(pMutableAreaOfInterestInformation);
  }

  @Override
  public int deleteAreaOfInterestInformation(String pEmployeeId) {
    return getManager().deleteAreaOfInterestInformation(pEmployeeId);
  }
}
