package org.ums.manager;

import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.immutable.ExamRoutine;

import java.util.List;

public interface ExamRoutineManager extends ContentManager<ExamRoutine, MutableExamRoutine, Object> {
  List<ExamRoutineDto> getExamRoutine(int semesterId, int examType);

  List<ExamRoutineDto> getExamRoutineForApplicationCCI(int semesterId, int examType);

  ExamRoutineDto getExamRoutineForCivilExamBySemester(Integer pSemesterId);

  List<ExamRoutineDto> getCCIExamRoutinesBySemeste(Integer pSemesterId);

  List<ExamRoutineDto> getExamRoutineBySemesterAndExamType(Integer pSemesterId, Integer pExamType);

  List<ExamRoutineDto> getExamRoutine(int pSemesterId, int pExamType, String pOfferedBy);

  List<ExamRoutineDto> getExamDatesBySemesterAndType(Integer pSemesterId, Integer pExamType);

  List<ExamRoutineDto> getExamRoutineBySemesterAndExamTypeOrderByExamDateAndProgramIdAndCourseId(Integer pSemesterId,
      Integer pExamType);
}
