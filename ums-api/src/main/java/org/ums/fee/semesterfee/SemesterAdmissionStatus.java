package org.ums.fee.semesterfee;

import java.io.Serializable;
import java.util.Date;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;

interface SemesterAdmissionStatus extends Serializable, EditType<MutableSemesterAdmissionStatus>, LastModifier,
    Identifier<Long> {

  Date getReceivedOn();

  Boolean isPaymentCompleted();

  Semester getSemester();

  Integer getSemesterId();

  Student getStudent();

  String getStudentId();
}
