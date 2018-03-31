package org.ums.result.legacy;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;

public interface LegacyTabulation extends Serializable, EditType<MutableLegacyTabulation>, LastModifier,
    Identifier<Long> {

  Double getGpa();

  Double getCgpa();

  String getComment();

  Integer getYear();

  Integer getAcademicSemester();

  Semester getSemester();

  Integer getSemesterId();

  Student getStudent();

  String getStudentId();

  Double getCompletedCrHr();
}
