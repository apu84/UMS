package org.ums.manager;

import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSpStudent;

import java.util.List;

/**
 * Created by My Pc on 4/27/2016.
 */
public interface SpStudentManager extends ContentManager<SpStudent, MutableSpStudent, String> {
  List<SpStudent> getStudentByProgramYearSemesterStatus(int pProgramId, int pYear, int pSemester,
      int pStatus);

  List<SpStudent> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId,
      Integer pSemesterId);

  List<SpStudent> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId, String pExamDate);
}
