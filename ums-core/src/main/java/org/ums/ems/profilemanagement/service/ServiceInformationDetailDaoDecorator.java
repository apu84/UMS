package org.ums.ems.profilemanagement.service;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class ServiceInformationDetailDaoDecorator
    extends
    ContentDaoDecorator<ServiceInformationDetail, MutableServiceInformationDetail, Long, ServiceInformationDetailManager>
    implements ServiceInformationDetailManager {
  @Override
  public List<ServiceInformationDetail> getServiceDetail(Long pId) {
    return getManager().getServiceDetail(pId);
  }

  @Override
  public boolean exists(String pEmployeeId) {
    return getManager().exists(pEmployeeId);
  }
}
