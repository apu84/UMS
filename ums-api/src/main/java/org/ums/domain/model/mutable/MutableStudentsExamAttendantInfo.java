package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public interface MutableStudentsExamAttendantInfo extends StudentsExamAttendantInfo, Editable<Long>,
    MutableLastModifier, MutableIdentifier<Long> {
  void setProgramId(final Integer pProgramId);

  void setSemesterId(final Integer pSemesterId);

  void setYear(final Integer pYear);

  void setSemester(final Integer pSemester);

  void setExamType(final Integer pExamType);

  void setPresentStudents(final Integer pPresentStudents);

  void setAbsentStudents(final Integer pAbsentStudents);

  void setRegisteredStudents(final Integer pRegisteredStudents);

  void setCourseId(final String pCourseId);

  void setCourseNo(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setProgramName(final String pProgramName);

  void setDeptId(final String pDeptId);

  void setDeptName(final String pDeptName);

  void setExamDate(final String pExamDate);

}
