package org.ums.decorator;

import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSpStudent;
import org.ums.manager.SpStudentManager;

import java.util.List;

/**
 * Created by My Pc on 4/27/2016.
 */
public class SpStudentDaoDecorator extends ContentDaoDecorator<SpStudent,MutableSpStudent,String,SpStudentManager> implements SpStudentManager {
  @Override
  public List<SpStudent> getStudentByProgramYearSemesterStatus(int pProgramId, int pYear, int pSemester, int pStatus) {
    return getManager().getStudentByProgramYearSemesterStatus(pProgramId,pYear,pSemester,pStatus);
  }

  @Override
  public List<SpStudent> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId, Integer pSemesterId) {
    return getManager().getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(pCourseId,pSemesterId);
  }
}
