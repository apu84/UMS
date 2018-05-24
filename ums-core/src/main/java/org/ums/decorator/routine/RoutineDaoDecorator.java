package org.ums.decorator.routine;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.manager.routine.RoutineManager;

import java.util.List;

/**
 * Created by My Pc on 3/5/2016.
 */
public class RoutineDaoDecorator extends ContentDaoDecorator<Routine, MutableRoutine, Long, RoutineManager> implements
    RoutineManager {
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

  @Override
  public List<Routine> getRoutine(int pSemesterId, int pProgramId) {
    return getManager().getRoutine(pSemesterId, pProgramId);
  }
}
