package org.ums.ems.profilemanagement.award;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class AwardInformationDaoDecorator extends
    ContentDaoDecorator<AwardInformation, MutableAwardInformation, Long, AwardInformationManager> implements
    AwardInformationManager {

  @Override
  public List<AwardInformation> get(String pEmployeeId) {
    return getManager().get(pEmployeeId);
  }

  @Override
  public boolean exists(String pEmployeeId) {
    return getManager().exists(pEmployeeId);
  }
}
