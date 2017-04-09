package org.ums.domain.model.mutable.registrar.Employee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.Employee.AwardInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableAwardInformation extends AwardInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final int pEmployeeId);

  void setAwardName(final String pAwardName);

  void setAwardInstitute(final String pAwardInstitute);

  void setAwardedYear(final String pAwardedYear);

  void setAwardShortDescription(final String pAwardShortDescription);
}
