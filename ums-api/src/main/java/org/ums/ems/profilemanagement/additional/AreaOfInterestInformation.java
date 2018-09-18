package org.ums.ems.profilemanagement.additional;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface AreaOfInterestInformation extends Serializable, EditType<MutableAreaOfInterestInformation>,
    Identifier<String>, LastModifier {

  String getAreaOfInterest();
}
