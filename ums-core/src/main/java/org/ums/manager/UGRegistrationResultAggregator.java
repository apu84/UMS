package org.ums.manager;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.ums.decorator.UGRegistrationResultDaoDecorator;
import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.persistent.model.PersistentTaskStatus;
import org.ums.services.academic.ProcessResult;
import org.ums.util.UmsUtils;

public class UGRegistrationResultAggregator extends UGRegistrationResultDaoDecorator {
  private static final Logger mLogger = LoggerFactory
      .getLogger(UGRegistrationResultAggregator.class);
  private static final Integer UPDATE_NOTIFICATION_AFTER = 20;

  private EquivalentCourseManager mEquivalentCourseManager;
  private TaskStatusManager mTaskStatusManager;
  private SemesterManager mSemesterManager;

  public UGRegistrationResultAggregator(final EquivalentCourseManager pEquivalentCourseManager,
      final TaskStatusManager pTaskStatusManager, final SemesterManager pSemesterManager) {
    mEquivalentCourseManager = pEquivalentCourseManager;
    mTaskStatusManager = pTaskStatusManager;
    mSemesterManager = pSemesterManager;
  }

  @Override
  public List<UGRegistrationResult> getResults(String pStudentId, Integer pSemesterId) {
    List<UGRegistrationResult> resultList = super.getResults(pStudentId, pSemesterId);
    if(mLogger.isDebugEnabled()) {
      mLogger.debug("Course found: " + resultList.size());
    }
    Collections.sort(resultList, new ResultComparator());
    return resultsWithCarryCourses(resultList, pSemesterId);
  }

  private List<UGRegistrationResult> aggregateResults(List<UGRegistrationResult> pResults) {
    Map<String, UGRegistrationResult> resultMap = new HashMap<>();
    for(UGRegistrationResult result : pResults) {
      // if(!resultMap.containsKey(result.getCourseId())) {
      resultMap.put(result.getCourseId(), result);
      // }
    }
    if(mLogger.isDebugEnabled()) {
      mLogger.debug("Aggregated result: " + resultMap.size());
    }
    return equivalent(resultMap);
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId) {
    String taskId = String.format("%s_%s%s", pProgramId, pSemesterId, ProcessResult.PROCESS_GRADES);
    createTaskStatus(taskId);

    List<UGRegistrationResult> resultList = super.getResults(pProgramId, pSemesterId);

    Map<String, List<UGRegistrationResult>> studentCourseGradeMap =
        resultList.stream().collect(Collectors.groupingBy(UGRegistrationResult::getStudentId));

    int totalStudentFound = studentCourseGradeMap.keySet().size();
    int i = 0;
    if(mLogger.isDebugEnabled()) {
      mLogger.debug("Total student found for result process is: " + totalStudentFound);
    }

    for(String studentId : studentCourseGradeMap.keySet()) {
      if(mLogger.isDebugEnabled()) {
        mLogger.debug("Processing grades for student: " + studentId);
      }
      Collections.sort(studentCourseGradeMap.get(studentId), new ResultComparator());
      List<UGRegistrationResult> results = aggregateResults(studentCourseGradeMap.get(studentId));
      studentCourseGradeMap.put(studentId, results);

      i++;
      if((i % UPDATE_NOTIFICATION_AFTER) == 0 || (i == totalStudentFound)) {
        updateTaskStatus(taskId, TaskStatus.Status.INPROGRESS,
            UmsUtils.getPercentageString(i, totalStudentFound));
      }
    }
    List<UGRegistrationResult> returnList =
        studentCourseGradeMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    updateTaskStatus(taskId, TaskStatus.Status.COMPLETED);
    return returnList;
  }

  private List<UGRegistrationResult> equivalent(Map<String, UGRegistrationResult> pResults) {
    List<EquivalentCourse> equivalentCourses = mEquivalentCourseManager.getAll();
    Map<String, EquivalentCourse> equivalentCourseMap = equivalentCourses.stream()
        .collect(Collectors.toMap(EquivalentCourse::getOldCourseId, Function.identity()));

    for(EquivalentCourse course : equivalentCourseMap.values()) {
      if(pResults.containsKey(course.getOldCourseId())
          && pResults.containsKey(course.getNewCourseId())) {
        pResults.remove(course.getOldCourseId());
      }
    }
    if(mLogger.isDebugEnabled()) {
      mLogger.debug("Equivalence result: " + pResults.values().size());
    }
    return new ArrayList<>(pResults.values());
  }

  private class ResultComparator implements Comparator<UGRegistrationResult> {
    @Override
    public int compare(final UGRegistrationResult pResult1, final UGRegistrationResult pResult2) {
      try {
        int dateDiff =
            pResult1.getSemester().getStartDate().compareTo(pResult2.getSemester().getStartDate());
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

  @Async
  private void createTaskStatus(String taskId) {
    MutableTaskStatus taskStatus = new PersistentTaskStatus();
    taskStatus.setId(taskId);
    taskStatus.setStatus(TaskStatus.Status.INPROGRESS);
    taskStatus.commit(false);
  }

  private void updateTaskStatus(String taskId, TaskStatus.Status pStatus) {
    updateTaskStatus(taskId, pStatus, null);
  }

  @Async
  private void updateTaskStatus(String taskId, TaskStatus.Status pStatus, String pProgress) {
    TaskStatus taskStatus = mTaskStatusManager.get(taskId);
    MutableTaskStatus mutableTaskStatus = taskStatus.edit();
    mutableTaskStatus.setStatus(pStatus);
    if(!StringUtils.isEmpty(pProgress)) {
      mutableTaskStatus.setProgressDescription(pProgress);
    }
    mutableTaskStatus.commit(true);
  }

  private List<UGRegistrationResult> resultsWithCarryCourses(List<UGRegistrationResult> pResults,
      Integer pForSemesterId) {
    ListIterator<UGRegistrationResult> resultIterator = pResults.listIterator();
    while(resultIterator.hasNext()) {
      UGRegistrationResult result = resultIterator.next();
      Semester pForSemester = mSemesterManager.get(pForSemesterId);
      if(result.getSemester().getStartDate().before(pForSemester.getStartDate())
          && result.getGradeLetter() != null) {
        if(result.getGradeLetter().equalsIgnoreCase("F")) {
          if(hasTakenInFollowingSemesters(result, pResults)) {
            resultIterator.remove();
          }
        }
        else {
          resultIterator.remove();
        }
      }
    }
    List<UGRegistrationResult> list = new ArrayList<>();
    while(resultIterator.hasPrevious()) {
      resultIterator.previous();
    }
    resultIterator.forEachRemaining(list::add);
    return list;
  }

  private boolean hasTakenInFollowingSemesters(UGRegistrationResult pResult,
      List<UGRegistrationResult> pResults) {
    for(UGRegistrationResult result : pResults) {
      if(result.getCourseId().equalsIgnoreCase(pResult.getCourseId())
          && pResult.getSemester().getStartDate().before(result.getSemester().getStartDate())) {
        return true;
      }
    }
    return hasTakenEquivalentInFollowingSemesters(pResult, pResults);
  }

  private boolean hasTakenEquivalentInFollowingSemesters(UGRegistrationResult pResult,
                                                         List<UGRegistrationResult> pResults) {
    List<EquivalentCourse> equivalentCourses = mEquivalentCourseManager.getAll();
    Map<String, EquivalentCourse> equivalentCourseMap = equivalentCourses.stream()
        .collect(Collectors.toMap(EquivalentCourse::getOldCourseId, Function.identity()));
    EquivalentCourse equivalentCourse = equivalentCourseMap.get(pResult.getCourseId());
    if(equivalentCourse != null) {
      for(UGRegistrationResult result : pResults) {
        if(result.getCourseId().equalsIgnoreCase(equivalentCourse.getNewCourseId())
            && pResult.getSemester().getStartDate().before(result.getSemester().getStartDate())) {
          return true;
        }
      }
    }
    return false;
  }
}
