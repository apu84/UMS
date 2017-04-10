package org.ums.domain.model.immutable.registrar.employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.employee.MutableExperienceInformation;

import java.io.Serializable;

public interface ExperienceInformation extends Serializable, EditType<MutableExperienceInformation>,
    Identifier<Integer>, LastModifier {

  int getEmployeeId();

  String getExperienceInstitute();

  String getDesignation();

  String getExperienceFromDate();

  String getExperienceToDate();
}
