package org.ums.employee.publication;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.enums.common.PublicationType;

import java.io.Serializable;
import java.util.Date;

public interface PublicationInformation extends Serializable, EditType<MutablePublicationInformation>,
    Identifier<Long>, LastModifier {

  String getEmployeeId();

  String getTitle();

  String getInterestGenre();

  String getPublisherName();

  Date getDateOfPublication();

  PublicationType getType();

  int getTypeId();

  String getWebLink();

  String getISSN();

  String getIssue();

  String getVolume();

  String getJournalName();

  Country getCountry();

  int getCountryId();

  String getPages();

  String getStatus();

  Date getAppliedOn();

  Date getActionTakenOn();

  int getRowNumber();
}
