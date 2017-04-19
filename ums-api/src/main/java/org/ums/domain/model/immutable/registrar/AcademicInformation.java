package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;

import java.io.Serializable;

public interface AcademicInformation extends Serializable, EditType<MutableAcademicInformation>, Identifier<Integer>,
    LastModifier {

  String getEmployeeId();

  String getDegreeName();

  String getDegreeInstitute();

  String getDegreePassingYear();
}
