package org.ums.employee.academic;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.enums.common.DegreeLevel;

import java.io.Serializable;

public interface AcademicInformation extends Serializable, EditType<MutableAcademicInformation>, Identifier<Long>,
    LastModifier {

  String getEmployeeId();

  DegreeLevel getDegreeLevel();

  Integer getDegreeLevelId();

  DegreeTitle getDegreeTitle();

  Integer getDegreeTitleId();

  String getInstitute();

  Integer getPassingYear();

  String getResult();

  String getMajor();

  Integer getDuration();
}
