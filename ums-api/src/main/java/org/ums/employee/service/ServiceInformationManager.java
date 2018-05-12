package org.ums.employee.service;

import org.ums.manager.ContentManager;

import java.util.List;

public interface ServiceInformationManager extends ContentManager<ServiceInformation, MutableServiceInformation, Long> {

  List<ServiceInformation> get(final String pEmployeeId);

  boolean exists(final String pEmployeeId);
}
