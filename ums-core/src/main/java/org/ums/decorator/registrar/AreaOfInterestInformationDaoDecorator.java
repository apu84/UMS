package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.AreaOfInterestInformation;
import org.ums.domain.model.mutable.registrar.MutableAreaOfInterestInformation;
import org.ums.manager.registrar.AreaOfInterestInformationManager;

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
