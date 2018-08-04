package org.ums.manager.routine;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.manager.ContentManager;

import java.util.List;

public interface RoutineManager extends ContentManager<Routine, MutableRoutine, Long> {
  List<Routine> getTeacherRoutine(String teacherId);

  List<Routine> getStudentRoutine(Student student);

  List<Routine> getRoutine(int pSemesterId, String pCourseId);

  List<Routine> getRoutine(int semesterId, int programId, int year, int semester, String section);

  List<Routine> getRoutine(int pSemesterId, int pProgramId);
}
