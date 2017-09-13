package org.ums.employee.service;

import org.ums.employee.service.ServiceInformationDetail;
import org.ums.employee.service.MutableServiceInformationDetail;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ServiceInformationDetailManager extends
    ContentManager<ServiceInformationDetail, MutableServiceInformationDetail, Long> {

  int saveServiceInformationDetail(final List<MutableServiceInformationDetail> pMutableServiceInformationDetail);

  List<ServiceInformationDetail> getServiceInformationDetail(final Long pServiceId);

  int updateServiceInformationDetail(final List<MutableServiceInformationDetail> pMutableServiceInformationDetail);

  int deleteServiceInformationDetail(final List<MutableServiceInformationDetail> pMutableServiceInformationDetail);
}
