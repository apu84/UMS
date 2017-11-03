package org.ums.employee.experience;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface ExperienceInformation extends Serializable, EditType<MutableExperienceInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  String getExperienceInstitute();

  String getDesignation();

  String getExperienceFromDate();

  String getExperienceToDate();

  int getExperienceDuration();

  String getExperienceDurationString();
}
