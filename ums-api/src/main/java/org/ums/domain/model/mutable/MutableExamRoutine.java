package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.ExamRoutine;

import java.util.List;

public interface MutableExamRoutine extends ExamRoutine, Editable<Integer>, MutableIdentifier<Long> {
  void setRoutine(final List<ExamRoutineDto> pRoutineList);

  void setSemesterId(final Integer pSemesterId);

  void setSemesterName(final String pSemesterName);

  void setExamTypeId(final Integer pExamType);

  void setExamTypeName(final String pExamType);

  void setInsertType(final String pInsertType);

  void setTotalStudent(final Integer pTotalStudent);

  void setProgramName(final String pProgramShortName);

  void setCourseNumber(final String pCourseNo);

  void setCourseTitle(final String pCourseTitle);

  void setCourseYear(final Integer pCourseYear);

  void setCourseSemester(final Integer pCourseSemester);

  void setExamGroup(final Integer pExamGroup);

  void setAppDeadLineStr(final String pApplicationDeadLine);
}
