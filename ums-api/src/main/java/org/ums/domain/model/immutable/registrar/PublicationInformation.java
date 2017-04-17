package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;

import java.io.Serializable;

public interface PublicationInformation extends Serializable, EditType<MutablePublicationInformation>,
    Identifier<Integer>, LastModifier {

  int getEmployeeId();

  String getPublicationTitle();

  String getInterestGenre();

  String getPublisherName();

  String getDateOfPublication();

  String getPublicationType();

  String getPublicationWebLink();
}
