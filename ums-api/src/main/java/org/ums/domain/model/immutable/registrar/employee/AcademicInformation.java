package org.ums.domain.model.immutable.registrar.employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.employee.MutableAcademicInformation;

import java.io.Serializable;

public interface AcademicInformation extends Serializable, EditType<MutableAcademicInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  String getDegreeName();

  String getDegreeInstitute();

  String getDegreePassingYear();
}
