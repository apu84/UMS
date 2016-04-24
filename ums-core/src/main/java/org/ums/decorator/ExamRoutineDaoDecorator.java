package org.ums.decorator;

import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.manager.ExamRoutineManager;

import java.util.List;

public class ExamRoutineDaoDecorator extends ContentDaoDecorator<ExamRoutine, MutableExamRoutine, Object, ExamRoutineManager> implements ExamRoutineManager {
  @Override
  public List<ExamRoutineDto> getExamRoutine(int semesterId,int examTypeId) throws Exception {
    return getManager().getExamRoutine(semesterId,examTypeId);
  }

}