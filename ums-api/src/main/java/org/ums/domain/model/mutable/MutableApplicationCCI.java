package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.enums.ApplicationStatus;
import org.ums.enums.ApplicationType;

/**
 * Created by My Pc on 7/11/2016.
 */
public interface MutableApplicationCCI extends ApplicationCCI, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
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

  void setApplicationStatus(final ApplicationStatus applicationStatus);

  void setCCIStatus(final Integer cciStatus);

  void setGradeLetter(final String gradeLetter);

  void setCarryYear(final Integer carryyear);

  void setCarrySemester(final Integer carrySemester);

  void setFullName(final String fullName);

  void setCurrentEnrolledSemester(final Integer currentEnrolledSemester);

  void setTotalcarry(final Integer totalcarry);

  void setTotalApplied(final Integer totalApplied);

  void setTotalApproved(final Integer totalApproved);

  void setTotalRejected(final Integer totalrejected);

}
