package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;

import java.io.Serializable;

public interface ExperienceInformation extends Serializable, EditType<MutableExperienceInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  String getExperienceInstitute();

  String getDesignation();

  String getExperienceFromDate();

  String getExperienceToDate();
}
