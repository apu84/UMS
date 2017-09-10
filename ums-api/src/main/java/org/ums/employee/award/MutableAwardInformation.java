package org.ums.employee.award;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAwardInformation extends AwardInformation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setAwardName(final String pAwardName);

  void setAwardInstitute(final String pAwardInstitute);

  void setAwardedYear(final int pAwardedYear);

  void setAwardShortDescription(final String pAwardShortDescription);
}
