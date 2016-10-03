package org.ums.services.academic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.decorator.UGRegistrationResultDaoDecorator;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

import java.util.*;

public class UGRegistrationResultAggregator extends UGRegistrationResultDaoDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(UGRegistrationResultAggregator.class);
  private SemesterManager mSemesterManager;
  private StudentManager mStudentManager;

  public UGRegistrationResultAggregator(final SemesterManager pSemesterManager,
                                        final StudentManager pStudentManager) {
    mSemesterManager = pSemesterManager;
    mStudentManager = pStudentManager;
  }

  @Override
  public List<UGRegistrationResult> getRegisteredCourses(String pStudentId) throws Exception {
    List<UGRegistrationResult> resultList = super.getRegisteredCourses(pStudentId);
    Collections.sort(resultList, new ResultComparator());
    return resultList;
  }

  private List<UGRegistrationResult> aggregateResults(List<UGRegistrationResult> pResults) {
    Map<String, UGRegistrationResult> resultMap = new HashMap<>();
    for (UGRegistrationResult result : pResults) {
      if (!resultMap.containsKey(result.getCourseId())) {
        resultMap.put(result.getCourseId(), result);
      }
    }

    return equivalent(new ArrayList<>(resultMap.values()));
  }

  private List<UGRegistrationResult> equivalent(List<UGRegistrationResult> pResults) {
    return pResults;
  }

  private class ResultComparator implements Comparator<UGRegistrationResult> {
    @Override
    public int compare(final UGRegistrationResult pResult1, final UGRegistrationResult pResult2) {
      try {
        int dateDiff = pResult1.getSemester().getStartDate().compareTo(pResult2.getSemester().getStartDate());
        if (dateDiff != 0) {
          return dateDiff;
        } else {
          if (pResult1.getCourseId().equalsIgnoreCase(pResult2.getCourseId())) {
            return pResult1.getType().compareTo(pResult2.getType());
          } else {
            return pResult1.getCourseId().compareTo(pResult2.getCourseId());
          }
        }
      } catch (Exception e) {
        mLogger.error(e.getMessage());
      }
      throw new NullPointerException("Can not sort result");
    }
  }
}
