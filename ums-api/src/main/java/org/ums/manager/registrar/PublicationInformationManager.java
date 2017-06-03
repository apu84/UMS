package org.ums.manager.registrar;

import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.ContentManager;

import java.util.List;

public interface PublicationInformationManager extends
    ContentManager<PublicationInformation, MutablePublicationInformation, Integer> {

  int savePublicationInformation(final List<MutablePublicationInformation> pMutablePublicationInformation);

  List<PublicationInformation> getEmployeePublicationInformation(final String pEmployeeId);

  List<PublicationInformation> getPublicationInformationWithPagination(final String pEmployeeId,
      final String pPublicationStatus, final int pPageNumber, final int pItemPerPage);

  List<PublicationInformation> getEmployeePublicationInformation(final String pEmployeeId, final String pStatus);

  List<PublicationInformation> getPublicationInformation(final String pPublicationStatus);

  int updatePublicationStatus(final MutablePublicationInformation pMutablePublicationInformation);

  int deletePublicationInformation(final String pEmployeeId);
}
