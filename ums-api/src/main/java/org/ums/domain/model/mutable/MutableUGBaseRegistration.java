package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.*;
import org.ums.enums.ExamType;

public interface MutableUGBaseRegistration extends UGBaseRegistration, Mutable, MutableIdentifier<Integer>, MutableLastModifier {
  void setCourseId(final String pCourseId);

  void setCourse(final Course pCourse);

  void setSemesterId(final Integer pSemesterId);

  void setSemester(final Semester pSemester);

  void setStudentId(final String pStudentId);

  void setStudent(final Student pStudent);

  void setGradeLetter(final String pGradeLetter);

  void setExamType(final ExamType pExamType);

  void setStatus(final UGRegistrationResult.Status pStatus);

  void setType(final String pType);

  void setCourseNo(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setExamDate(final String pExamDate);

  void setMessage(final String pMessage);
}
