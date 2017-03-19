package org.ums.services.academic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.enums.CourseType;
import org.ums.manager.ResultPublishManager;
import org.ums.manager.StudentRecordManager;
import org.ums.manager.TaskStatusManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.persistent.model.PersistentTaskStatus;
import org.ums.response.type.TaskStatusResponse;
import org.ums.util.UmsUtils;

@Component
public class ProcessResultImpl implements ProcessResult {
  @Autowired
  UGRegistrationResultManager mResultManager;
  @Autowired
  TaskStatusManager mTaskStatusManager;
  @Autowired
  StudentRecordManager mStudentRecordManager;
  @Autowired
  ResultPublishManager mResultPublishManager;

  private final static String PROCESS_GRADES = "process_grades";
  private final static String PROCESS_GPA_CGPA_PROMOTION = "process_gpa_cgpa_promotion";
  private final static String PUBLISH_RESULT = "publish_result";
  private final static Integer UPDATE_NOTIFICATION_AFTER = 20;
  private final static Integer MAX_NO_FAILED_COURSE = 4;
  private final static Integer MAX_NO_FAILED_COURSE_CURRENT_SEMESTER = 2;

  private Map<String, Double> GPA_MAP = null;
  private final List<String> EXCLUDE_GRADES = new ArrayList<>();

  public ProcessResultImpl() {
    GPA_MAP = UmsUtils.getGPAMap();

    EXCLUDE_GRADES.add("E");
    EXCLUDE_GRADES.add("W");
    EXCLUDE_GRADES.add("P");
  }

  @Override
  public void process(int pProgramId, int pSemesterId) {
    processResult(pProgramId, pSemesterId);
  }

  @Async
  private void processResult(int pProgramId, int pSemesterId) {
    List<UGRegistrationResult> resultList = mResultManager.getResults(pProgramId, pSemesterId);

    MutableTaskStatus processResultStatus = new PersistentTaskStatus();
    processResultStatus.setId(mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PROCESS_GPA_CGPA_PROMOTION));
    processResultStatus.setStatus(TaskStatus.Status.INPROGRESS);
    processResultStatus.commit(false);

    processResult(pProgramId, pSemesterId,
        resultList.stream().collect(Collectors.groupingBy(UGRegistrationResult::getStudentId)));
  }

  private void processResult(int pProgramId, int pSemesterId,
      Map<String, List<UGRegistrationResult>> studentCourseGradeMap) {
    String processCGPA = mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PROCESS_GPA_CGPA_PROMOTION);
    int totalStudents = studentCourseGradeMap.keySet().size();
    int i = 0;

    List<StudentRecord> studentRecords =
        mStudentRecordManager.getStudentRecords(pProgramId, pSemesterId);
    Map<String, StudentRecord> studentRecordMap = studentRecords.stream()
        .collect(Collectors.toMap(StudentRecord::getStudentId, Function.identity()));

    List<MutableStudentRecord> updatedStudentRecords = new ArrayList<>();

    for(String studentId : studentCourseGradeMap.keySet()) {
      Double gpa = calculateGPA(studentCourseGradeMap.get(studentId).stream()
          .filter(pResult -> pResult.getSemesterId() == pSemesterId).collect(Collectors.toList()));

      Double cgpa = calculateCGPA(studentCourseGradeMap.get(studentId));
      boolean isPassed = isPassed(pSemesterId, studentCourseGradeMap.get(studentId));

      MutableStudentRecord studentRecord = studentRecordMap.get(studentId).edit();
      studentRecord.setGPA(gpa);
      studentRecord.setCGPA(cgpa);
      studentRecord.setStatus(isPassed ? StudentRecord.Status.PASSED : StudentRecord.Status.FAILED);
      updatedStudentRecords.add(studentRecord);

      i++;

      if((i % UPDATE_NOTIFICATION_AFTER) == 0 || (i == totalStudents)) {
        mStudentRecordManager.update(updatedStudentRecords);
        updatedStudentRecords.clear();
        TaskStatus taskStatus = mTaskStatusManager.get(processCGPA);
        MutableTaskStatus mutableTaskStatus = taskStatus.edit();
        mutableTaskStatus.setProgressDescription(UmsUtils.getPercentageString(i, totalStudents));
        mutableTaskStatus.commit(true);
      }
    }
    TaskStatus taskStatus = mTaskStatusManager.get(processCGPA);
    MutableTaskStatus mutableTaskStatus = taskStatus.edit();
    mutableTaskStatus.setProgressDescription("100");
    mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
    mutableTaskStatus.commit(true);
  }

  private Double calculateCGPA(List<UGRegistrationResult> pResults) {
    return calculateGPA(pResults);
  }

  private Double calculateGPA(List<UGRegistrationResult> pResults) {
    int totalCrHr = 0;
    Double totalGPA = 0D;
    for(UGRegistrationResult result : pResults) {
      if(!EXCLUDE_GRADES.contains(result.getGradeLetter())) {
        totalCrHr += result.getCourse().getCrHr();
        totalGPA += GPA_MAP.get(result.getGradeLetter()) * result.getCourse().getCrHr();
      }
    }
    Double toBeTruncated = totalGPA / totalCrHr;
    return BigDecimal.valueOf(toBeTruncated).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  private Boolean isPassed(final int pSemesterId, List<UGRegistrationResult> pResults) {
    int totalFailedCourse = 0, failedInCurrentSemester = 0;
    for(UGRegistrationResult result : pResults) {
      if(result.getGradeLetter().equalsIgnoreCase("F")) {
        if(result.getCourse().getCourseType() == CourseType.SESSIONAL) {
          return false;
        }
        totalFailedCourse++;
        if(result.getSemesterId() == pSemesterId) {
          failedInCurrentSemester++;
        }
      }
    }
    return (totalFailedCourse <= MAX_NO_FAILED_COURSE)
        && (failedInCurrentSemester <= MAX_NO_FAILED_COURSE_CURRENT_SEMESTER);
  }

  @Override
  public TaskStatusResponse status(int pProgramId, int pSemesterId) {
    String publishResult = mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PUBLISH_RESULT);
    String processCGPA = mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PROCESS_GPA_CGPA_PROMOTION);
    String processGrades = mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PROCESS_GRADES);

    if(mTaskStatusManager.exists(publishResult)) {
      TaskStatus status = mTaskStatusManager.get(publishResult);
      return new TaskStatusResponse(status);
    }

    if(mTaskStatusManager.exists(processGrades)) {
      TaskStatus status = mTaskStatusManager.get(processGrades);
      if(status.getStatus() == TaskStatus.Status.INPROGRESS) {
        return new TaskStatusResponse(status);
      }
      else {
        if(mTaskStatusManager.exists(processCGPA)) {
          return new TaskStatusResponse(mTaskStatusManager.get(processCGPA));
        }
        else {
          return new TaskStatusResponse(status);
        }
      }
    }

    MutableTaskStatus taskStatus = new PersistentTaskStatus();
    taskStatus.setId(processCGPA);
    taskStatus.setStatus(TaskStatus.Status.NONE);

    return new TaskStatusResponse(taskStatus);
  }

  @Transactional
  @Override
  public void publishResult(int pProgramId, int pSemesterId) {
    String publishResult = mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PUBLISH_RESULT);

    MutableTaskStatus taskStatus = new PersistentTaskStatus();
    taskStatus.setId(publishResult);
    taskStatus.setStatus(TaskStatus.Status.INPROGRESS);
    taskStatus.commit(false);

    mResultPublishManager.publishResult(pProgramId, pSemesterId);

    TaskStatus status = mTaskStatusManager.get(publishResult);
    MutableTaskStatus mutableTaskStatus = status.edit();
    mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
    mutableTaskStatus.commit(true);
  }
}
