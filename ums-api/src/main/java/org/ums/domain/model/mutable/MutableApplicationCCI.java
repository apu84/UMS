package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.ApplicationType;

/**
 * Created by My Pc on 7/11/2016.
 */
public interface MutableApplicationCCI extends ApplicationCCI, Mutable, MutableLastModifier, MutableIdentifier<Long> {
  void setSemester(final Semester pSemester);

  void setSemesterId(final Integer pSemesterId);

  void setStudent(final Student pStudent);

  void setStudentId(final String pStudentId);

  void setCourse(final Course pCourse);

  void setCourseId(final String pCourseId);

  void setCourseNo(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setApplicationType(final ApplicationType pApplicationType);

  void setApplicationDate(final String pApplicationDate);

  void setExamDate(final String pExamDate);

  void setExamDateOriginal(final String pExamDateOriginal);

  void setMessage(final String pMessage);

  void setTotalStudent(final Integer pTotalStudent);

  void setCourseYear(final Integer pCourseYear);

  void setCourseSemester(final Integer pCourseSemester);

  void setRoomNo(final String pRoomNo);

  void setRoomId(final Integer pRoomId);
}
