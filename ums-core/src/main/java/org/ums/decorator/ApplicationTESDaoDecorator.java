package org.ums.decorator;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.manager.ApplicationTESManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
public class ApplicationTESDaoDecorator extends
    ContentDaoDecorator<ApplicationTES, MutableApplicationTES, Long, ApplicationTESManager> implements
    ApplicationTESManager {
  @Override
  public List<ApplicationTES> getAlreadyReviewdCourses(String pStudentId, Integer pSemesterId) {
    return getManager().getAlreadyReviewdCourses(pStudentId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getTeachersInfo(String pCourseId, Integer pSemesterId, String pSection) {
    return getManager().getTeachersInfo(pCourseId, pSemesterId, pSection);
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    return getManager().getAllQuestions(pSemesterId);
  }

  @Override
  public List<ApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId, String pCourseType) {
    return getManager().getReviewEligibleCourses(pStudentId, pSemesterId, pCourseType);
  }

  @Override
  public String getSemesterName(Integer pCurrentSemester) {
    return getManager().getSemesterName(pCurrentSemester);
  }
}
