package org.ums.decorator.registrar;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.registrar.ServiceInformationDetail;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.manager.registrar.ServiceInformationDetailManager;

import java.util.List;

public class ServiceInformationDetailDaoDecorator
    extends
    ContentDaoDecorator<ServiceInformationDetail, MutableServiceInformationDetail, Integer, ServiceInformationDetailManager>
    implements ServiceInformationDetailManager {

  @Override
  public int saveServiceInformationDetail(List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
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
