package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface PublicationInformationManager extends
    ContentManager<PublicationInformation, MutablePublicationInformation, Integer> {

  int savePublicationInformation(final List<MutablePublicationInformation> pMutablePublicationInformation);

  List<PublicationInformation> getEmployeePublicationInformation(final int pEmployeeId);
}
