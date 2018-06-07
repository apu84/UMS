package org.ums.employee.publication;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class PublicationInformationDaoDecorator extends
    ContentDaoDecorator<PublicationInformation, MutablePublicationInformation, Long, PublicationInformationManager>
    implements PublicationInformationManager {

  @Override
  public List<PublicationInformation> get(String pEmployeeId) {
    return getManager().get(pEmployeeId);
  }

  @Override
  public boolean exists(String pEmployeeId) {
    return getManager().exists(pEmployeeId);
  }
}
