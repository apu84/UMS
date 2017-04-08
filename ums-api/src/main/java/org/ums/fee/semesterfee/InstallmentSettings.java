package org.ums.fee.semesterfee;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;

public interface InstallmentSettings extends Serializable, EditType<MutableInstallmentSettings>, LastModifier,
    Identifier<Long> {

  Boolean isEnabled();

  Semester getSemester();

  Integer getSemesterId();
}
