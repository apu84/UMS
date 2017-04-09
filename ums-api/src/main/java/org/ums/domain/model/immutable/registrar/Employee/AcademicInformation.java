package org.ums.domain.model.immutable.registrar.Employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.Employee.MutableAcademicInformation;

import java.io.Serializable;
import java.util.Date;

public interface AcademicInformation extends Serializable, EditType<MutableAcademicInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  String getDegreeName();

  String getDegreeInstitute();

  String getDegreePassingYear();
}
