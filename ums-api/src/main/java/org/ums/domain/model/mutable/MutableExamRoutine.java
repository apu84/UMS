package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.ExamRoutine;
import java.util.List;

public interface MutableExamRoutine extends ExamRoutine, Mutable {
  void setRoutine(final List<ExamRoutineDto> pRoutineList);
  void setSemesterId(final Integer pSemesterId);
  void setSemesterName(final String pSemesterName);
  void setExamTypeId(final Integer pExamType);
  void setExamTypeName(final String pExamType);
  void setInsertType(final String pInsertType);
  void setTotalStudent(final Integer pTotalStudent);
}
