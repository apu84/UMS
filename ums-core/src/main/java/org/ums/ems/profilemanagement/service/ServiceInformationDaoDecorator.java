package org.ums.ems.profilemanagement.service;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class ServiceInformationDaoDecorator extends
    ContentDaoDecorator<ServiceInformation, MutableServiceInformation, Long, ServiceInformationManager> implements
    ServiceInformationManager {

  @Override
  public List<ServiceInformation> get(String pEmployeeId) {
    return getManager().get(pEmployeeId);
  }

  @Override
  public boolean exists(String pEmployeeId) {
    return getManager().exists(pEmployeeId);
  }
}
