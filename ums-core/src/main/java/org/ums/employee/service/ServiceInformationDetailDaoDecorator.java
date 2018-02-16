package org.ums.employee.service;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class ServiceInformationDetailDaoDecorator
    extends
    ContentDaoDecorator<ServiceInformationDetail, MutableServiceInformationDetail, Long, ServiceInformationDetailManager>
    implements ServiceInformationDetailManager {

  @Override
  public int saveServiceInformationDetail(List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    return getManager().saveServiceInformationDetail(pMutableServiceInformationDetail);
  }

  @Override
  public int saveServiceInformationDetail(MutableServiceInformationDetail pMutableServiceInformationDetail) {
    return getManager().saveServiceInformationDetail(pMutableServiceInformationDetail);
  }

  @Override
  public List<ServiceInformationDetail> getServiceInformationDetail(Long pServiceId) {
    return getManager().getServiceInformationDetail(pServiceId);
  }

  @Override
  public int updateServiceInformationDetail(List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    return getManager().updateServiceInformationDetail(pMutableServiceInformationDetail);
  }

  @Override
  public int deleteServiceInformationDetail(List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    return getManager().deleteServiceInformationDetail(pMutableServiceInformationDetail);
  }
}
