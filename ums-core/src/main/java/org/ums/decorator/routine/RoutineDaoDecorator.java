package org.ums.decorator.routine;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.manager.routine.RoutineManager;

import java.util.List;

/**
 * Created by My Pc on 3/5/2016.
 */
public class RoutineDaoDecorator extends ContentDaoDecorator<Routine, MutableRoutine, Long, RoutineManager> implements
    RoutineManager {
  @Override
  public List<Routine> getRoutineByTeacher(String teacherId, Integer pSemesterId) {
    return getManager().getRoutineByTeacher(teacherId, pSemesterId);
  }

  @Override
  public List<Routine> getRoutineByStudent(Student student) {
    return getManager().getRoutineByStudent(student);
  }

  @Override
  public List<Routine> getRoutineBySemesterProgramIdYearSemesterAndSection(int semesterId, int programId, int year,
      int semester, String section) {
    return getManager().getRoutineBySemesterProgramIdYearSemesterAndSection(semesterId, programId, year, semester,
        section);
  }

  @Override
  public List<Routine> getRoutineBySemesterAndProgram(int pSemesterId, int pProgramId) {
    return getManager().getRoutineBySemesterAndProgram(pSemesterId, pProgramId);
  }

  @Override
  public List<Routine> getRoutineBySemesterAndCourse(int pSemesterId, String pCourseId) {
    return getManager().getRoutineBySemesterAndCourse(pSemesterId, pCourseId);
  }

  @Override
  public List<Routine> getRoutineBySemesterAndRoom(int pSemesterId, int pRoomId) {
    return getManager().getRoutineBySemesterAndRoom(pSemesterId, pRoomId);
  }
}
