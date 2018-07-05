package org.ums.cache.routine;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.manager.CacheManager;
import org.ums.manager.routine.RoutineManager;

import java.util.List;

public class RoutineCache extends ContentCache<Routine, MutableRoutine, Long, RoutineManager> implements RoutineManager {
  private CacheManager<Routine, Long> manager;

  public RoutineCache(CacheManager<Routine, Long> manager) {
    this.manager = manager;
  }

  @Override
  protected CacheManager<Routine, Long> getCacheManager() {
    return manager;
  }

  @Override
  public List<Routine> getTeacherRoutine(String teacherId) {
    return getManager().getTeacherRoutine(teacherId);
  }

  @Override
  public List<Routine> getStudentRoutine(Student student) {
    return getManager().getStudentRoutine(student);
  }

  @Override
  public List<Routine> getRoutine(int semesterId, int programId, int year, int semester, String section) {
    return getManager().getRoutine(semesterId, programId, year, semester, section);
  }

  @Override
  public List<Routine> getRoutine(int pSemesterId, int pProgramId) {
    return getManager().getRoutine(pSemesterId, pProgramId);
  }

  @Override
  public List<Routine> getRoutine(int pSemesterId, String pCourseId) {
    return getManager().getRoutine(pSemesterId, pCourseId);
  }
}
