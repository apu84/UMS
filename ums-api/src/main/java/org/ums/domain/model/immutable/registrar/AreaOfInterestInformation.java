package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.mutable.registrar.MutableAreaOfInterestInformation;

import java.io.Serializable;

public interface AreaOfInterestInformation extends Serializable, EditType<MutableAreaOfInterestInformation>,
    Identifier<String>, LastModifier {

  String getEmployeeId();

  AreaOfInterest getAreaOfInterest();

  Integer getAreaOfInterestId();
}
