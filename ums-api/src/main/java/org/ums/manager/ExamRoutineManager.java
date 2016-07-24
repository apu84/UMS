package org.ums.manager;

import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.immutable.ExamRoutine;

import java.util.List;

public interface ExamRoutineManager extends ContentManager<ExamRoutine, MutableExamRoutine, Object> {
  public List<ExamRoutineDto> getExamRoutine(int semesterId,int examType) throws Exception;
  public List<ExamRoutineDto> getExamRoutineForApplicationCCI(int semesterId,int examType);
}

