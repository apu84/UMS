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

  @Override
  public List<ExamRoutineDto> getExamRoutineForApplicationCCI(int semesterId, int examType) {
    return getManager().getExamRoutineForApplicationCCI(semesterId,examType);
  }

  @Override
  public ExamRoutineDto getExamRoutineForCivilExamBySemester(Integer pSemesterId) {
    return getManager().getExamRoutineForCivilExamBySemester(pSemesterId);
  }

  @Override
  public List<ExamRoutineDto> getCCIExamRoutinesBySemeste(Integer pSemesterId) {
    return getManager().getCCIExamRoutinesBySemeste(pSemesterId);
  }

  @Override
  public List<ExamRoutineDto> getExamRoutineBySemesterAndExamType(Integer pSemesterId, Integer pExamType) {
    return getManager().getExamRoutineBySemesterAndExamType(pSemesterId,pExamType);
  }

  @Override
  public List<ExamRoutineDto> getExamDatesBySemesterAndType(Integer pSemesterId, Integer pExamType) {
    return getManager().getExamRoutineBySemesterAndExamType(pSemesterId,pExamType);
  }

  @Override
  public List<ExamRoutineDto> getExamRoutineBySemesterAndExamTypeOrderByExamDateAndProgramIdAndCourseId(Integer pSemesterId, Integer pExamType) {
    return getManager().getExamRoutineBySemesterAndExamTypeOrderByExamDateAndProgramIdAndCourseId(pSemesterId,pExamType);
  }
}