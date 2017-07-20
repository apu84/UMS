package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.ServiceInformationDetail;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ServiceInformationDetailManager extends
    ContentManager<ServiceInformationDetail, MutableServiceInformationDetail, Integer> {

  int saveServiceInformationDetail(final List<MutableServiceInformationDetail> pMutableServiceInformationDetail);

  List<ServiceInformationDetail> getServiceInformationDetail(final Long pServiceId);

  int updateServiceInformationDetail(final List<MutableServiceInformationDetail> pMutableServiceInformationDetail);

  int deleteServiceInformationDetail(final List<MutableServiceInformationDetail> pMutableServiceInformationDetail);
}
