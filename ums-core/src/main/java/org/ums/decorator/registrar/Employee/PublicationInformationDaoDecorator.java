package org.ums.decorator.registrar.Employee;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.Employee.PublicationInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutablePublicationInformation;
import org.ums.manager.registrar.Employee.PublicationInformationManager;

import java.util.List;

public class PublicationInformationDaoDecorator extends
    ContentDaoDecorator<PublicationInformation, MutablePublicationInformation, Integer, PublicationInformationManager>
    implements PublicationInformationManager {

  @Override
  public int savePublicationInformation(MutablePublicationInformation pMutablePublicationInformation) {
    return getManager().savePublicationInformation(pMutablePublicationInformation);
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(int pEmployeeId) {
    return getManager().getEmployeePublicationInformation(pEmployeeId);
  }
}
