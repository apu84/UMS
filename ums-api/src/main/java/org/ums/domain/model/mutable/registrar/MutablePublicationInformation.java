package org.ums.domain.model.mutable.registrar;

import org.apache.poi.ss.formula.functions.Count;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.PublicationType;

import java.util.Date;

public interface MutablePublicationInformation extends PublicationInformation, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setTitle(final String pTitle);

  void setInterestGenre(final String pInterestGenre);

  void setPublisherName(final String pPublisherName);

  void setDateOfPublication(final Date pDateOfPublication);

  void setType(final PublicationType pType);

  void setTypeId(final int pTypeId);

  void setWebLink(final String pWebLink);

  void setISSN(final String pISSN);

  void setIssue(final String pIssue);

  void setVolume(final String pVolume);

  void setJournalName(final String pJournalName);

  void setCountry(final Country pCountry);

  void setCountryId(final int pCountryId);

  void setPages(final String pPages);

  void setStatus(final String pStatus);

  void setAppliedOn(final Date pAppliedOn);

  void setActionTakenOn(final Date pActionTakenOn);

  void setRowNumber(final int pRowNumber);
}
