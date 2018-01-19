package org.ums.services.academic;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.ums.academic.tabulation.model.TabulationCourseModelImpl;
import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.manager.EquivalentCourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.UGRegistrationResultAggregator;

import com.google.common.collect.Maps;

@Service
public class StudentCarryCourseServiceImpl implements StudentCarryCourseService {
  private static final Logger mLogger = LoggerFactory.getLogger(UGRegistrationResultAggregator.class);
  @Autowired
  @Lazy
  private SemesterManager mSemesterManager;
  @Autowired
  @Lazy
  private EquivalentCourseManager mEquivalentCourseManager;
  private Map<String, EquivalentCourse> mEquivalentCourseMap;

  @Override
  public TabulationCourseModelImpl findCoursesForTabulation(List<UGRegistrationResult> pResultList, int pSemesterId) {
    List<EquivalentCourse> equivalentCourses = mEquivalentCourseManager.getAll();
    mEquivalentCourseMap =
        equivalentCourses.stream().collect(Collectors.toMap(EquivalentCourse::getOldCourseId, Function.identity()));
    Collections.sort(pResultList, new ResultComparator());
    return getCarryCourses(pResultList, pSemesterId);
  }

  private TabulationCourseModelImpl getCarryCourses(List<UGRegistrationResult> pResults, Integer pForSemesterId) {
    ListIterator<UGRegistrationResult> resultIterator = pResults.listIterator();
    Map<String, UGRegistrationResult> failedCoursesPreviousSemester = new HashMap<>();
    Map<String, UGRegistrationResult> failedCoursesCurrentSemester = new HashMap<>();
    Map<String, UGRegistrationResult> failedCourses = Maps.newHashMap();
    List<UGRegistrationResult> allCourses = new ArrayList<>();

    Semester pForSemester = mSemesterManager.get(pForSemesterId);

    while(resultIterator.hasNext()) {
      UGRegistrationResult result = resultIterator.next();
      // removing results that are after pForSemester
      if(result.getSemester().getStartDate().after(pForSemester.getStartDate())) {
        resultIterator.remove();
        continue;
      }

      if(result.getSemester().getId().intValue() == pForSemesterId) {
        allCourses.add(result);
        failedCourses = failedCoursesCurrentSemester;
      }
      else {
        failedCourses = failedCoursesPreviousSemester;
      }

      if(!isPassed(result.getGradeLetter())) {
        if(!failedCourses.containsKey(result.getCourseId())) {
          failedCourses.put(result.getCourseId(), result);
        }
      }
      else {
        if(failedCoursesCurrentSemester.containsKey(result.getCourseId())) {
          UGRegistrationResult failedCourse = failedCoursesCurrentSemester.get(result.getCourseId());
          if(isSameDayOrBefore(failedCourse.getSemester().getStartDate(), result.getSemester().getStartDate())) {
            failedCoursesCurrentSemester.remove(failedCourse.getCourseId());
          }
        }
        else if(failedCoursesPreviousSemester.containsKey(result.getCourseId())) {
          UGRegistrationResult failedCourse = failedCoursesPreviousSemester.get(result.getCourseId());
          if(isSameDayOrBefore(failedCourse.getSemester().getStartDate(), result.getSemester().getStartDate())) {
            failedCoursesPreviousSemester.remove(failedCourse.getCourseId());
          }
        }
        else {
          // check equivalence
          EquivalentCourse equivalentCourse = mEquivalentCourseMap.get(result.getCourseId());
          if(equivalentCourse != null) {
            if(failedCourses.containsKey(equivalentCourse.getOldCourseId())
                || failedCourses.containsKey(equivalentCourse.getNewCourseId())) {
              failedCourses.remove(failedCourses.toString());
            }
          }
        }
      }
    }
    Map<String, UGRegistrationResult> currentRegularCourses = new HashMap<>();
    Map<String, UGRegistrationResult> currentClearanceCourse = new HashMap<>();
    Map<String, UGRegistrationResult> currentCarryCourse = new HashMap<>();

    for(UGRegistrationResult ugRegistrationResult : allCourses) {
      if(ugRegistrationResult.getType() == CourseRegType.REGULAR) {
        currentRegularCourses.put(ugRegistrationResult.getCourseId(), ugRegistrationResult);
      }
      else if(ugRegistrationResult.getType() == CourseRegType.CLEARANCE
          || ugRegistrationResult.getType() == CourseRegType.IMPROVEMENT) {
        currentClearanceCourse.put(ugRegistrationResult.getCourseId(), ugRegistrationResult);
      }
      else if(ugRegistrationResult.getType() == CourseRegType.CARRY) {
        currentCarryCourse.put(ugRegistrationResult.getCourseId(), ugRegistrationResult);
      }
    }
    return new TabulationCourseModelImpl(currentRegularCourses, currentClearanceCourse, currentCarryCourse,
        failedCoursesCurrentSemester, failedCoursesPreviousSemester);
  }

  private boolean isSameDayOrBefore(Date compareDate, Date compareWith) {
    return DateUtils.isSameDay(compareDate, compareWith) || compareDate.before(compareWith);
  }

  private boolean isPassed(String gradeLetter) {
    return !gradeLetter.equalsIgnoreCase("F") && !gradeLetter.equalsIgnoreCase("W");
  }

  private class ResultComparator implements Comparator<UGRegistrationResult> {
    @Override
    public int compare(final UGRegistrationResult pResult1, final UGRegistrationResult pResult2) {
      try {
        int dateDiff = pResult1.getSemester().getStartDate().compareTo(pResult2.getSemester().getStartDate());
        if(dateDiff != 0) {
          return dateDiff;
        }
        else {
          if(pResult1.getCourseId().equalsIgnoreCase(pResult2.getCourseId())) {
            return pResult1.getExamType().compareTo(pResult2.getExamType());
          }
          else {
            return pResult1.getCourseId().compareTo(pResult2.getCourseId());
          }
        }
      } catch(Exception e) {
        mLogger.error(e.getMessage(), e);
      }
      throw new NullPointerException("Can not sort result");
    }
  }
}
