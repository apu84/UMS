package org.ums.ems.profilemanagement.experience;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.enums.registrar.ExperienceCategory;

import java.io.Serializable;
import java.util.Date;

public interface ExperienceInformation extends Serializable, EditType<MutableExperienceInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  String getExperienceInstitute();

  String getDesignation();

  Date getExperienceFromDate();

  Date getExperienceToDate();

  int getExperienceDuration();

  String getExperienceDurationString();

  ExperienceCategory getExperienceCategory();

  int getExperienceCategoryId();
}
