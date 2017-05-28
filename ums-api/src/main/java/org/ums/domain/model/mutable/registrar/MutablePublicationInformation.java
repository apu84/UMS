package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutablePublicationInformation extends PublicationInformation, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setPublicationTitle(final String pPublicationTitle);

  void setInterestGenre(final String pInterestGenre);

  void setPublisherName(final String pPublisherName);

  void setDateOfPublication(final String pDateOfPublication);

  void setPublicationType(final String pPublicationType);

  void setPublicationWebLink(final String pPublicationWebLink);

  void setPublicationISSN(final String pPublicationISSN);

  void setPublicationIssue(final String pPublicationIssue);

  void setPublicationVolume(final String pPublicationVolume);

  void setPublicationJournalName(final String pPublicationJournalName);

  void setPublicationCountry(final String pPublicationCountry);

  void setPublicationPages(final String pPublicationPages);

  void setPublicationStatus(final String pPublicationStatus);

  void setAppliedOn(final String pAppliedOn);

  void setActionTakenOn(final String pActionTakenOn);
}
