package org.ums.manager;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.immutable.Routine;

import java.util.List;

public interface RoutineManager extends ContentManager<Routine, MutableRoutine, String> {
  public List<Routine> getTeacherRoutine(String teacherId);

  public List<Routine> getStudentRoutine(Student student);

  public List<Routine> getEmployeeRoutine(int semesterId, int programId, int year, int semester);
}
