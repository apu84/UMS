package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.manager.registrar.ServiceInformationManager;

import java.util.List;

public class ServiceInformationDaoDecorator extends
    ContentDaoDecorator<ServiceInformation, MutableServiceInformation, Integer, ServiceInformationManager> implements
    ServiceInformationManager {

  @Override
  public int saveServiceInformation(List<MutableServiceInformation> pMutableEmployeeInformation) {
    return getManager().saveServiceInformation(pMutableEmployeeInformation);
  }

  @Override
  public List<ServiceInformation> getServiceInformation(int pEmployeeId) {
    return getManager().getServiceInformation(pEmployeeId);
  }
}
