package org.ums.employee.additional;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.AreaOfInterest;

import java.io.Serializable;

public interface AreaOfInterestInformation extends Serializable, EditType<MutableAreaOfInterestInformation>,
    Identifier<String>, LastModifier {

  AreaOfInterest getAreaOfInterest();

  Integer getAreaOfInterestId();
}
