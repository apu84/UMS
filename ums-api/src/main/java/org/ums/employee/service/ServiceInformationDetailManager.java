package org.ums.employee.service;

import org.ums.manager.ContentManager;

import java.util.List;

public interface ServiceInformationDetailManager extends
    ContentManager<ServiceInformationDetail, MutableServiceInformationDetail, Long> {

  List<ServiceInformationDetail> getServiceDetail(final Long pId);

  boolean exists(final String pEmployeeId);
}
