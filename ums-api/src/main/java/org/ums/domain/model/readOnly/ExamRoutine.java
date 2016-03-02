package org.ums.domain.model.readOnly;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import java.io.Serializable;
import java.util.List;

public interface ExamRoutine extends Serializable, EditType<MutableExamRoutine> {
  List<ExamRoutineDto> getRoutine();
  int getSemesterId();
  String getSemesterName();
  int getExamTypeId();
  String getExamTypeName();
  String getInsertType();
}
