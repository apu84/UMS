package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.enums.common.AcademicDegreeType;

import java.io.Serializable;

public interface AcademicInformation extends Serializable, EditType<MutableAcademicInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  AcademicDegreeType getDegree();

  int getDegreeId();

  String getInstitute();

  int getPassingYear();
}
