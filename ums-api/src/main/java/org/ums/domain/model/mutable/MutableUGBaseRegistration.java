package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGBaseRegistration;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;

public interface MutableUGBaseRegistration extends UGBaseRegistration, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {
  void setCourseId(final String pCourseId);

  void setCourse(final Course pCourse);

  void setSemesterId(final Integer pSemesterId);

  void setSemester(final Semester pSemester);

  void setStudentId(final String pStudentId);

  void setStudent(final Student pStudent);

  void setGradeLetter(final String pGradeLetter);

  void setExamType(final ExamType pExamType);

  void setType(final CourseRegType pType);

  void setCourseNo(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setExamDate(final String pExamDate);

  void setMessage(final String pMessage);

}
