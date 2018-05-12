package org.ums.employee.additional;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class AreaOfInterestInformationDaoDecorator
    extends
    ContentDaoDecorator<AreaOfInterestInformation, MutableAreaOfInterestInformation, String, AreaOfInterestInformationManager>
    implements AreaOfInterestInformationManager {

  @Override
  public List<AreaOfInterestInformation> getAll(String pEmployeeId) {
    return getManager().getAll(pEmployeeId);
  }
}
