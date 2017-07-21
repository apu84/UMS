package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.immutable.Semester;

import java.util.Date;

public interface MutableParameterSetting extends ParameterSetting, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setSemesterId(Integer pSemesterId);

  void setSemester(Semester pSemester);

  void setParameter(Parameter pParameter);

  void setParameterId(String pParameterId);

  void setStartDate(Date pStartDate);

  void setEndDate(Date pEndDate);
}
