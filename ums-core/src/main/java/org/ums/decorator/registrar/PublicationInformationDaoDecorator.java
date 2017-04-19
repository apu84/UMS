package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.registrar.PublicationInformationManager;

import java.util.List;

public class PublicationInformationDaoDecorator extends
    ContentDaoDecorator<PublicationInformation, MutablePublicationInformation, Integer, PublicationInformationManager>
    implements PublicationInformationManager {

  @Override
  public int savePublicationInformation(List<MutablePublicationInformation> pMutablePublicationInformation) {
    return getManager().savePublicationInformation(pMutablePublicationInformation);
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(String pEmployeeId) {
    return getManager().getEmployeePublicationInformation(pEmployeeId);
  }
}
