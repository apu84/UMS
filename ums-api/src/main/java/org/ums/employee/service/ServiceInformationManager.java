package org.ums.employee.service;

import org.ums.employee.service.ServiceInformation;
import org.ums.employee.service.MutableServiceInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ServiceInformationManager extends ContentManager<ServiceInformation, MutableServiceInformation, Long> {

  Long saveServiceInformation(final MutableServiceInformation pMutableServiceInformation);

  List<ServiceInformation> getServiceInformation(final String pEmployeeId);

  int updateServiceInformation(final MutableServiceInformation pMutableServiceInformation);

  int deleteServiceInformation(final MutableServiceInformation pMutableServiceInformation);
}
