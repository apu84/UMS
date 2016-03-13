package org.ums.manager;

import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.immutable.Routine;

import java.util.List;

/**
 * Created by My Pc on 3/5/2016.
 */
public interface RoutineManager extends ContentManager<Routine,MutableRoutine,String> {
  public List<Routine> getTeacherRoutine(String teacherId);
  public List<Routine> getStudentRoutine(int semesterId,int programId);
  public List<Routine> getEmployeeRoutine(int semesterId,int programId,int year,int semester);
}
