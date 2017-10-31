package org.ums.employee.academic;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.enums.common.AcademicDegreeType;

import java.io.Serializable;

public interface AcademicInformation extends Serializable, EditType<MutableAcademicInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  AcademicDegreeType getDegree();

  int getDegreeId();

  String getInstitute();

  String getPassingYear();

  String getResult();
}
