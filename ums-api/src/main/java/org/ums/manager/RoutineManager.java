package org.ums.manager;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.immutable.Routine;

import java.util.List;

public interface RoutineManager extends ContentManager<Routine, MutableRoutine, Long> {
  List<Routine> getTeacherRoutine(String teacherId);

  List<Routine> getStudentRoutine(Student student);

  List<Routine> getEmployeeRoutine(int semesterId, int programId, int year, int semester);

  List<Routine> getRoutine(int pSemesterId, int pProgramId);
}
