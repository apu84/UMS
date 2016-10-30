package org.ums.services.academic;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.enums.CourseType;
import org.ums.manager.StudentRecordManager;
import org.ums.manager.TaskStatusManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.persistent.model.PersistentTaskStatus;
import org.ums.response.type.TaskStatusResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProcessResultImpl implements ProcessResult {
  @Autowired
  UGRegistrationResultManager mResultManager;
  @Autowired
  TaskStatusManager mTaskStatusManager;
  @Autowired
  StudentRecordManager mStudentRecordManager;

  private final ExecutorService pool = Executors.newFixedThreadPool(10);

  private final static String PROCESS_GRADES = "_process_grades";
  private final static String PROCESS_GRADES_TASK_NAME = "Processing student grades";
  private final static String PROCESS_GPA_CGPA_PROMOTION = "_process_gpa_cgpa_promotion";
  private final static String PROCESS_GPA_CGPA_PROMOTION_TASK_NAME =
      "Processing student GPA, CGPA and PASS/FAIL status";

  private static final Integer UPDATE_NOTIFICATION_AFTER = 20;
  private static final Integer MAX_NO_FAILED_COURSE = 4;

  private final Map<String, Double> GPA_MAP = new HashMap<>();
  private final List<String> EXCLUDE_GRADES = new ArrayList<>();

  public ProcessResultImpl() {
    GPA_MAP.put("A+", 4.00);
    GPA_MAP.put("A", 3.75);
    GPA_MAP.put("A-", 3.50);
    GPA_MAP.put("B+", 3.25);
    GPA_MAP.put("B", 3.00);
    GPA_MAP.put("B-", 2.75);
    GPA_MAP.put("C+", 2.50);
    GPA_MAP.put("C", 2.25);
    GPA_MAP.put("D", 2.00);
    GPA_MAP.put("F", 0.00);

    EXCLUDE_GRADES.add("E");
    EXCLUDE_GRADES.add("W");
    EXCLUDE_GRADES.add("P");
  }

  @Override
  public void process(int pProgramId, int pSemesterId) throws Exception {
    MutableTaskStatus taskStatus = new PersistentTaskStatus();
    taskStatus.setId(pProgramId + "_" + pSemesterId + PROCESS_GRADES);
    taskStatus.setTaskName(PROCESS_GRADES_TASK_NAME);
    taskStatus.setStatus(TaskStatus.Status.INPROGRESS);
    taskStatus.commit(false);

    getResults(pProgramId, pSemesterId);
  }

  private Future<List<UGRegistrationResult>> getResults(int pProgramId, int pSemesterId)
      throws Exception {
    return pool.submit(() -> {
      List<UGRegistrationResult> resultList = mResultManager.getResults(pProgramId, pSemesterId);

      TaskStatus taskStatus =
          mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GRADES);
      MutableTaskStatus mutableTaskStatus = taskStatus.edit();
      mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
      mutableTaskStatus.commit(true);

      MutableTaskStatus processResultStatus = new PersistentTaskStatus();
      processResultStatus.setId(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION);
      processResultStatus.setTaskName(PROCESS_GPA_CGPA_PROMOTION_TASK_NAME);
      processResultStatus.setStatus(TaskStatus.Status.INPROGRESS);
      processResultStatus.commit(false);

      processResult(pProgramId, pSemesterId,
          resultList.stream().collect(Collectors.groupingBy(UGRegistrationResult::getStudentId)));

      return resultList;
    });
  }

  private void processResult(int pProgramId, int pSemesterId,
      Map<String, List<UGRegistrationResult>> studentCourseGradeMap) throws Exception {

    int totalStudents = studentCourseGradeMap.keySet().size();
    int percentageCompleted, i = 0;

    List<StudentRecord> studentRecords =
        mStudentRecordManager.getStudentRecords(pProgramId, pSemesterId);
    Map<String, StudentRecord> studentRecordMap = studentRecords.stream()
        .collect(Collectors.toMap(StudentRecord::getStudentId, Function.identity()));

    List<MutableStudentRecord> updatedStudentRecords = new ArrayList<>();

    for(String studentId : studentCourseGradeMap.keySet()) {
      Double gpa = calculateGPA(studentCourseGradeMap.get(studentId).stream()
          .filter(pResult -> pResult.getSemesterId() == pSemesterId).collect(Collectors.toList()));

      Double cgpa = calculateCGPA(studentCourseGradeMap.get(studentId));
      boolean isPassed = isPassed(studentCourseGradeMap.get(studentId));

      MutableStudentRecord studentRecord = studentRecordMap.get(studentId).edit();
      studentRecord.setGPA(gpa);
      studentRecord.setCGPA(cgpa);
      studentRecord.setStatus(isPassed ? StudentRecord.Status.PASSED : StudentRecord.Status.FAILED);
      updatedStudentRecords.add(studentRecord);

      i++;

      if((i % UPDATE_NOTIFICATION_AFTER) == 0 || (i == studentCourseGradeMap.keySet().size())) {
        mStudentRecordManager.update(updatedStudentRecords);
        updatedStudentRecords.clear();

        percentageCompleted = (i / totalStudents) * 100;

        TaskStatus taskStatus =
            mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION);
        MutableTaskStatus mutableTaskStatus = taskStatus.edit();
        mutableTaskStatus.setProgressDescription(percentageCompleted + "");
        mutableTaskStatus.commit(true);
      }
    }

    TaskStatus taskStatus =
        mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION);
    MutableTaskStatus mutableTaskStatus = taskStatus.edit();
    mutableTaskStatus.setProgressDescription("100");
    mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
    mutableTaskStatus.commit(false);
  }

  private Double calculateCGPA(List<UGRegistrationResult> pResults) throws Exception {
    return calculateGPA(pResults);
  }

  private Double calculateGPA(List<UGRegistrationResult> pResults) throws Exception {
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

  private Boolean isPassed(List<UGRegistrationResult> pResults) throws Exception {
    int failedCourse = 0;
    for(UGRegistrationResult result : pResults) {
      if(result.getGradeLetter().equalsIgnoreCase("F")) {
        if(result.getCourse().getCourseType() == CourseType.SESSIONAL) {
          return false;
        }
        failedCourse++;
      }
    }
    return failedCourse > MAX_NO_FAILED_COURSE;
  }

  @Override
  public TaskStatusResponse status(int pProgramId, int pSemesterId) throws Exception {
    if(mTaskStatusManager.exists(pProgramId + "_" + pSemesterId + PROCESS_GRADES)) {
      TaskStatus status = mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GRADES);
      if(status.getStatus() == TaskStatus.Status.INPROGRESS) {
        return new TaskStatusResponse(status);
      }
      else {
        if(mTaskStatusManager.exists(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION)) {
          return new TaskStatusResponse(mTaskStatusManager.get(pProgramId + "_" + pSemesterId
              + PROCESS_GPA_CGPA_PROMOTION));
        }
        else {
          return new TaskStatusResponse(status);
        }
      }
    }

    MutableTaskStatus taskStatus = new PersistentTaskStatus();
    taskStatus.setId(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION);
    taskStatus.setTaskName(PROCESS_GPA_CGPA_PROMOTION);
    taskStatus.setStatus(TaskStatus.Status.NONE);

    return new TaskStatusResponse(taskStatus);
  }
}
