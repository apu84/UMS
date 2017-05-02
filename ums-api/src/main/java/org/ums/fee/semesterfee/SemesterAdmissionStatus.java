package org.ums.fee.semesterfee;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;

public interface SemesterAdmissionStatus extends Serializable, EditType<MutableSemesterAdmissionStatus>, LastModifier,
    Identifier<Long> {

  Boolean isAdmitted();

  Semester getSemester();

  Integer getSemesterId();

  Student getStudent();

  String getStudentId();
}
