package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface ServiceInformationManager extends
    ContentManager<ServiceInformation, MutableServiceInformation, Integer> {

  int saveServiceInformation(final List<MutableServiceInformation> pMutableEmployeeInformation);

  List<ServiceInformation> getServiceInformation(final int pEmployeeId);
}
