package org.ums.manager.registrar.employee;

import org.ums.domain.model.immutable.registrar.employee.PublicationInformation;
import org.ums.domain.model.mutable.registrar.employee.MutablePublicationInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface PublicationInformationManager extends
    ContentManager<PublicationInformation, MutablePublicationInformation, Integer> {

  int savePublicationInformation(final MutablePublicationInformation pMutablePublicationInformation);

  List<PublicationInformation> getEmployeePublicationInformation(final int pEmployeeId);
}
