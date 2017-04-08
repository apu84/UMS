package org.ums.fee.semesterfee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableInstallmentSettings extends InstallmentSettings, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setEnabled(Boolean pEnabled);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);
}
