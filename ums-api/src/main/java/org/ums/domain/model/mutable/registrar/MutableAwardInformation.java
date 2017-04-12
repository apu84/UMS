package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAwardInformation extends AwardInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final int pEmployeeId);

  void setAwardName(final String pAwardName);

  void setAwardInstitute(final String pAwardInstitute);

  void setAwardedYear(final String pAwardedYear);

  void setAwardShortDescription(final String pAwardShortDescription);
}
