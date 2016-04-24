package org.ums.decorator;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.immutable.Routine;
import org.ums.manager.RoutineManager;

import java.util.List;

/**
 * Created by My Pc on 3/5/2016.
 */
public class RoutineDaoDecorator extends ContentDaoDecorator<Routine, MutableRoutine, String, RoutineManager> implements RoutineManager {
  @Override
  public List<Routine> getTeacherRoutine(String teacherId) {
    return getManager().getTeacherRoutine(teacherId);
  }

  @Override
  public List<Routine> getStudentRoutine(Student student) {
    return getManager().getStudentRoutine(student);
  }

  @Override
  public List<Routine> getEmployeeRoutine(int semesterId, int programId, int year, int semester) {
    return getManager().getEmployeeRoutine(semesterId, programId, year, semester);
  }
}
