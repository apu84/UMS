package org.ums.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.decorator.UGRegistrationResultDaoDecorator;
import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.immutable.UGRegistrationResult;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UGRegistrationResultAggregator extends UGRegistrationResultDaoDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(UGRegistrationResultAggregator.class);
  private EquivalentCourseManager mEquivalentCourseManager;

  public UGRegistrationResultAggregator(final EquivalentCourseManager pEquivalentCourseManager) {
    mEquivalentCourseManager = pEquivalentCourseManager;
  }

  @Override
  public List<UGRegistrationResult> getRegisteredCoursesWithResult(String pStudentId) throws Exception {
    List<UGRegistrationResult> resultList = super.getRegisteredCoursesWithResult(pStudentId);

    return aggregateResults(resultList);
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId) throws Exception {
    List<UGRegistrationResult> resultList = super.getResults(pProgramId, pSemesterId);

    Map<String, List<UGRegistrationResult>> studentCourseGradeMap
        = resultList.stream().collect(Collectors.groupingBy(UGRegistrationResult::getStudentId));

    for (String studentId : studentCourseGradeMap.keySet()) {
      Collections.sort(studentCourseGradeMap.get(studentId), new ResultComparator());
      aggregateResults(studentCourseGradeMap.get(studentId));
    }

    return studentCourseGradeMap.values().stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private List<UGRegistrationResult> aggregateResults(List<UGRegistrationResult> pResults) throws Exception {
    Map<String, UGRegistrationResult> resultMap = new HashMap<>();
    for (UGRegistrationResult result : pResults) {
      if (!resultMap.containsKey(result.getCourseId())) {
        resultMap.put(result.getCourseId(), result);
      }
    }

    return equivalent(resultMap);
  }

  private List<UGRegistrationResult> equivalent(Map<String, UGRegistrationResult> pResults) throws Exception {
    List<EquivalentCourse> equivalentCourses = mEquivalentCourseManager.getAll();
    Map<String, EquivalentCourse> equivalentCourseMap = equivalentCourses.stream().collect(
        Collectors.toMap(EquivalentCourse::getOldCourseId, Function.identity()));

    for (EquivalentCourse course : equivalentCourseMap.values()) {
      if (pResults.containsKey(course.getOldCourseId())
          && pResults.containsKey(course.getNewCourseId())) {
        pResults.remove(course.getOldCourseId());
      }
    }

    return new ArrayList<>(pResults.values());
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
        mLogger.error(e.getMessage(), e);
      }
      throw new NullPointerException("Can not sort result for processing grades");
    }
  }
}
