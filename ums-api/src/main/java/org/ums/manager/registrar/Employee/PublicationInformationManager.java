package org.ums.manager.registrar.Employee;

import org.ums.domain.model.immutable.registrar.Employee.PublicationInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutablePublicationInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface PublicationInformationManager extends
    ContentManager<PublicationInformation, MutablePublicationInformation, Integer> {

  int savePublicationInformation(final MutablePublicationInformation pMutablePublicationInformation);

  List<PublicationInformation> getEmployeePublicationInformation(final int pEmployeeId);
}
