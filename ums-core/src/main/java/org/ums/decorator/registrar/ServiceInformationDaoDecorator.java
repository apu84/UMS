package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.manager.registrar.ServiceInformationManager;

import java.util.List;

public class ServiceInformationDaoDecorator extends
    ContentDaoDecorator<ServiceInformation, MutableServiceInformation, Long, ServiceInformationManager> implements
    ServiceInformationManager {

  @Override
  public Long saveServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    return getManager().saveServiceInformation(pMutableServiceInformation);
  }

  @Override
  public List<ServiceInformation> getServiceInformation(String pEmployeeId) {
    return getManager().getServiceInformation(pEmployeeId);
  }

  @Override
  public int updateServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    return getManager().updateServiceInformation(pMutableServiceInformation);
  }

  @Override
  public int deleteServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    return getManager().deleteServiceInformation(pMutableServiceInformation);
  }
}
