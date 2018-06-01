package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.ExpelledInformation;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
public interface MutableExpelledInformation extends ExpelledInformation, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setStudentId(final String pStudentId);

  void setStudentName(final String pStudentName);

  void setCourseId(final String pCourseId);

  void setCourseNo(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setSemesterId(final Integer pSemesterId);

  void setSemesterName(final String pSemesterName);

  void setExamType(final Integer pExamTypeName);

  void setRegType(final Integer pRegType);

  void setExamTypeName(final String pExamName);

  void setDepartment(final Department pDepartment);

  void setProgramName(final String pProgramName);

  void setExpelledReason(final String pExpelledReason);

  void setInsertionDate(final String pInsertionDate);

  void setStatus(final Integer pStatus);

  void setExamDate(final String pExamDate);

  void setDeptId(final String pDeptId);

  void setDeptName(final String pDeptName);
}
