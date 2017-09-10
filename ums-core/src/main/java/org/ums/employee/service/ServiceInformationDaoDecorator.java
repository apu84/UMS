package org.ums.employee.service;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.employee.service.ServiceInformation;
import org.ums.employee.service.MutableServiceInformation;
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
