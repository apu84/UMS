package org.ums.domain.model.mutable.registrar.employee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.employee.PublicationInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutablePublicationInformation extends PublicationInformation, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setEmployeeId(final int pEmployeeId);

  void setPublicationTitle(final String pPublicationTitle);

  void setInterestGenre(final String pInterestGenre);

  void setAuthor(final String pAuthor);

  void setPublisherName(final String pPublisherName);

  void setDateOfPublication(final String pDateOfPublication);

  void setPublicationType(final String pPublicationType);

  void setPublicationWebLink(final String pPublicationWebLink);
}
