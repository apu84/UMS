package org.ums.services.academic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.enums.CourseType;
import org.ums.manager.*;
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
  @Autowired
  UMSConfiguration mUmsConfiguration;
  @Autowired
  RemarksBuilder mRemarksBuilder;
  @Autowired
  SemesterManager mSemesterManager;

  private final static String PROCESS_GRADES = "process_grades";
  private final static String PROCESS_GPA_CGPA_PROMOTION = "process_gpa_cgpa_promotion";
  private final static String PUBLISH_RESULT = "publish_result";
  private final static Integer UPDATE_NOTIFICATION_AFTER = 20;

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

  @Override
  public void process(int pProgramId, int pSemesterId, int pYear, int pSemester) {
    processResult(pProgramId, pSemesterId, pYear, pSemester);
  }

  @Async
  private void processResult(int pProgramId, int pSemesterId, int pYear, int pSemester) {
    List<UGRegistrationResult> resultList = mResultManager.getResults(pProgramId, pSemesterId, pYear, pSemester);

    MutableTaskStatus processResultStatus = new PersistentTaskStatus();
    processResultStatus.setId(mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PROCESS_GPA_CGPA_PROMOTION));
    processResultStatus.setStatus(TaskStatus.Status.INPROGRESS);
    processResultStatus.create();

    List<StudentRecord> studentRecords =
        mStudentRecordManager.getStudentRecords(pProgramId, pSemesterId, pYear, pSemester);

    processResult(pProgramId, pSemesterId,
        resultList.stream().collect(Collectors.groupingBy(UGRegistrationResult::getStudentId)), studentRecords);
  }

  @Async
  private void processResult(int pProgramId, int pSemesterId) {
    List<UGRegistrationResult> resultList = mResultManager.getResults(pProgramId, pSemesterId);

    MutableTaskStatus processResultStatus = new PersistentTaskStatus();
    processResultStatus.setId(mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PROCESS_GPA_CGPA_PROMOTION));
    processResultStatus.setStatus(TaskStatus.Status.INPROGRESS);
    processResultStatus.create();

    List<StudentRecord> studentRecords = mStudentRecordManager.getStudentRecords(pProgramId, pSemesterId);
    processResult(pProgramId, pSemesterId,
        resultList.stream().collect(Collectors.groupingBy(UGRegistrationResult::getStudentId)), studentRecords);
  }

  private void processResult(int pProgramId, int pSemesterId,
      Map<String, List<UGRegistrationResult>> studentCourseGradeMap, List<StudentRecord> studentRecords) {
    String processCGPA = mTaskStatusManager.buildTaskId(pProgramId, pSemesterId, PROCESS_GPA_CGPA_PROMOTION);
    int totalStudents = studentCourseGradeMap.keySet().size();
    int i = 0;

    Map<String, StudentRecord> studentRecordMap =
        studentRecords.stream().collect(Collectors.toMap(StudentRecord::getStudentId, Function.identity()));

    List<MutableStudentRecord> updatedStudentRecords = new ArrayList<>();

    for(String studentId : studentCourseGradeMap.keySet()) {
      MutableStudentRecord studentRecord = studentRecordMap.get(studentId).edit();

      StudentRecordParams studentRecordParams = calculateGPA(studentCourseGradeMap.get(studentId).stream()
          .filter(pResult -> pResult.getSemesterId() == pSemesterId).collect(Collectors.toList()));
      if(studentRecordParams != null) {
        studentRecord.setGPA(studentRecordParams.getGpa());
        studentRecord.setCompletedCrHr(studentRecordParams.getCompletedCrHr());
        studentRecord.setCompletedGradePoints(studentRecordParams.getCompletedGradePoints());
      }
      if(!mUmsConfiguration.isProcessGPAOnly()) {
        List<Semester> previousSemesters =
            mSemesterManager.getPreviousSemesters(pSemesterId, studentRecord.getStudent().getProgram().getProgramTypeId());
        List<Integer> previousSemesterIds =
            previousSemesters.stream().map(Semester::getId).collect(Collectors.toList());
        List<UGRegistrationResult> courseResults = studentCourseGradeMap.get(studentId).stream()
            .filter(pResult -> previousSemesterIds.contains(pResult.getSemesterId())).collect(Collectors.toList());
        StudentRecordParams cgpa = calculateCGPA(courseResults);
        boolean isPassed = isPassed(pSemesterId, courseResults);
        if(cgpa != null) {
          studentRecord.setCGPA(cgpa.getGpa());
          studentRecord.setTotalCompletedCrHr(cgpa.getCompletedCrHr());
          studentRecord.setTotalCompletedGradePoints(cgpa.getCompletedGradePoints());
        }
        studentRecord.setStatus(isPassed ? StudentRecord.Status.PASSED : StudentRecord.Status.FAILED);
        studentRecord.setGradesheetRemarks(mRemarksBuilder.getGradeSheetRemarks(courseResults,
            isPassed ? StudentRecord.Status.PASSED : StudentRecord.Status.PASSED, pSemesterId));
        studentRecord.setTabulationSheetRemarks(mRemarksBuilder
            .getTabulationSheetRemarks(courseResults, studentRecord, pSemesterId));
      }
      updatedStudentRecords.add(studentRecord);

      i++;

      if((i % UPDATE_NOTIFICATION_AFTER) == 0 || (i == totalStudents)) {
        mStudentRecordManager.update(updatedStudentRecords);
        updatedStudentRecords.clear();
        TaskStatus taskStatus = mTaskStatusManager.get(processCGPA);
        MutableTaskStatus mutableTaskStatus = taskStatus.edit();
        mutableTaskStatus.setProgressDescription(UmsUtils.getPercentageString(i, totalStudents));
        mutableTaskStatus.update();
      }
    }
    TaskStatus taskStatus = mTaskStatusManager.get(processCGPA);
    MutableTaskStatus mutableTaskStatus = taskStatus.edit();
    mutableTaskStatus.setProgressDescription("100");
    mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
    mutableTaskStatus.update();
  }

  private StudentRecordParams calculateCGPA(List<UGRegistrationResult> pResults) {
    return calculateGPA(pResults);
  }

  private StudentRecordParams calculateGPA(List<UGRegistrationResult> pResults) {
    Double totalCrHr = 0D;
    Double totalGPA = 0D;
    if(pResults.size() == 0) {
      return null;
    }
    for(UGRegistrationResult result : pResults) {
      if(!EXCLUDE_GRADES.contains(result.getGradeLetter())) {
        totalCrHr += result.getCourse().getCrHr();
        totalGPA += GPA_MAP.get(result.getGradeLetter()) * result.getCourse().getCrHr();
      }
    }
    Double toBeTruncated = totalGPA / totalCrHr;
    return new StudentRecordParams(BigDecimal.valueOf(toBeTruncated).setScale(2, RoundingMode.HALF_UP).doubleValue(),
        totalCrHr, totalGPA);
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
    taskStatus.create();

    mResultPublishManager.publishResult(pProgramId, pSemesterId);

    TaskStatus status = mTaskStatusManager.get(publishResult);
    MutableTaskStatus mutableTaskStatus = status.edit();
    mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
    mutableTaskStatus.update();
  }

  private class StudentRecordParams {
    private double gpa;
    private double completedCrHr;
    private double completedGradePoints;

    public StudentRecordParams(double pGpa, double pCompletedCrHr, double pCompletedGradePoints) {
      gpa = pGpa;
      completedCrHr = pCompletedCrHr;
      completedGradePoints = pCompletedGradePoints;
    }

    public double getGpa() {
      return gpa;
    }

    public double getCompletedCrHr() {
      return completedCrHr;
    }

    public double getCompletedGradePoints() {
      return completedGradePoints;
    }
  }
}
