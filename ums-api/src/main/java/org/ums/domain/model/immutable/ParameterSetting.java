package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableParameterSetting;

import java.io.Serializable;
import java.util.Date;

public interface ParameterSetting extends Serializable, LastModifier, EditType<MutableParameterSetting>,
    Identifier<Long> {
  Integer getSemesterId();

  Semester getSemester();

  Parameter getParameter();

  String getParameterId();

  Date getStartDate();

  Date getEndDate();
}
