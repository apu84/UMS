package org.ums.manager.routine;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.manager.ContentManager;

import java.util.List;

public interface RoutineManager extends ContentManager<Routine, MutableRoutine, Long> {
  List<Routine> getRoutineByTeacher(String teacherId, Integer pSemesterId);

  List<Routine> getRoutineByStudent(Student student);

  List<Routine> getRoutineBySemesterAndCourse(int pSemesterId, String pCourseId);

  List<Routine> getRoutineBySemesterProgramIdYearSemesterAndSection(int semesterId, int programId, int year,
      int semester, String section);

  List<Routine> getRoutineBySemesterAndProgram(int pSemesterId, int pProgramId);

  List<Routine> getRoutineBySemesterAndRoom(int pSemesterId, int pRoomId);
}
