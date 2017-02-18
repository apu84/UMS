package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableParameterSetting;

import java.io.Serializable;

/**
 * Created by My Pc on 3/14/2016.
 */
public interface ParameterSetting extends Serializable, LastModifier,
    EditType<MutableParameterSetting>, Identifier<Long> {
  Semester getSemester();

  Parameter getParameter();

  String getStartDate();

  String getEndDate();

  String getLastModified();
}
