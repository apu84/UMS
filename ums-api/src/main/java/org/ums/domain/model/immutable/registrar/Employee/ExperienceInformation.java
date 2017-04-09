package org.ums.domain.model.immutable.registrar.Employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.Employee.MutableExperienceInformation;

import java.io.Serializable;
import java.util.Date;

public interface ExperienceInformation extends Serializable, EditType<MutableExperienceInformation>,
    Identifier<Integer>, LastModifier {

  int getEmployeeId();

  String getExperienceInstitute();

  String getDesignation();

  String getExperienceFromDate();

  String getExperienceToDate();
}
