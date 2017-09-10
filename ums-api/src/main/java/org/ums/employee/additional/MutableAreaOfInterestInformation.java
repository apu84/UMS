package org.ums.employee.additional;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAreaOfInterestInformation extends AreaOfInterestInformation, Editable<String>,
    MutableIdentifier<String>, MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setAreaOfInterest(final AreaOfInterest pAreaOfInterest);

  void setAreaOfInterestId(final Integer pAreaOfInterestId);
}
