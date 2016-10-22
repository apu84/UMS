package org.ums.services.academic;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.manager.TaskStatusManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.persistent.model.PersistentTaskStatus;
import org.ums.response.type.TaskStatusResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class ProcessResultImpl implements ProcessResult {
  @Autowired
  UGRegistrationResultManager mResultManager;
  @Autowired
  TaskStatusManager mTaskStatusManager;

  private final ExecutorService pool = Executors.newFixedThreadPool(20);

  private final static String PROCESS_GRADES = "_process_grades";
  private final static String PROCESS_GRADES_TASK_NAME = "Processing student grades";
  private final static String PROCESS_GPA_CGPA_PROMOTION = "_process_gpa_cgpa_promotion";
  private final static String PROCESS_GPA_CGPA_PROMOTION_TASK_NAME = "Processing student GPA, CGPA and PASS/FAIL status";

  private final Integer UPDATE_NOTIFICATION_AFTER = 20;

  @Override
  public void process(int pProgramId, int pSemesterId) throws Exception {
    MutableTaskStatus taskStatus = new PersistentTaskStatus();
    taskStatus.setId(pProgramId + "_" + pSemesterId + PROCESS_GRADES);
    taskStatus.setTaskName(PROCESS_GRADES_TASK_NAME);
    taskStatus.setStatus(TaskStatus.Status.INPROGRESS);
    taskStatus.commit(false);

    getResults(pProgramId, pSemesterId);
  }

  private Future<List<UGRegistrationResult>> getResults(int pProgramId,
                                                        int pSemesterId) throws Exception {
    return pool.submit(() -> {
          List<UGRegistrationResult> resultList = mResultManager.getResults(pProgramId, pSemesterId);

          TaskStatus taskStatus = mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GRADES);
          MutableTaskStatus mutableTaskStatus = taskStatus.edit();
          mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
          mutableTaskStatus.commit(true);

          MutableTaskStatus processResultStatus = new PersistentTaskStatus();
          processResultStatus.setId(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION);
          processResultStatus.setTaskName(PROCESS_GPA_CGPA_PROMOTION_TASK_NAME);
          processResultStatus.setStatus(TaskStatus.Status.INPROGRESS);
          processResultStatus.commit(false);

          processResult(pProgramId,
              pSemesterId,
              resultList.stream().collect(Collectors.groupingBy(UGRegistrationResult::getStudentId)));

          return resultList;
        }
    );
  }

  private Future<Boolean> processResult(int pProgramId,
                                        int pSemesterId,
                                        Map<String, List<UGRegistrationResult>> studentCourseGradeMap) {
    return pool.submit(() -> {
          int totalStudents = studentCourseGradeMap.keySet().size();
          int percentageCompleted = 0, i = 0;
          for (String studentId : studentCourseGradeMap.keySet()) {
            calculateGPA(studentCourseGradeMap.get(studentId).stream()
                .filter(pResult -> pResult.getSemesterId() == pSemesterId)
                .collect(Collectors.toList()));

            calculateCGPA(studentCourseGradeMap.get(studentId));
            isPassed(studentCourseGradeMap.get(studentId));
            i++;
            if ((i % UPDATE_NOTIFICATION_AFTER) == 0) {
              percentageCompleted = (i / totalStudents) * 100;

              TaskStatus taskStatus = mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION);
              MutableTaskStatus mutableTaskStatus = taskStatus.edit();
              mutableTaskStatus.setProgressDescription(percentageCompleted + "");
              mutableTaskStatus.commit(true);
            }
          }

          TaskStatus taskStatus = mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION);
          MutableTaskStatus mutableTaskStatus = taskStatus.edit();
          mutableTaskStatus.setProgressDescription("100");
          mutableTaskStatus.setStatus(TaskStatus.Status.COMPLETED);
          mutableTaskStatus.commit(false);

          return true;
        }
    );
  }

  private Double calculateCGPA(List<UGRegistrationResult> pResults) {
    return 0D;
  }

  private Double calculateGPA(List<UGRegistrationResult> pResults) {
    return 0D;
  }

  private Boolean isPassed(List<UGRegistrationResult> pResults) {
    return false;
  }

  @Override
  public TaskStatusResponse status(int pProgramId, int pSemesterId) throws Exception {
    TaskStatus status = mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GRADES);
    if (status.getStatus() == TaskStatus.Status.INPROGRESS) {
      return new TaskStatusResponse(status);
    } else {
      return new TaskStatusResponse(mTaskStatusManager.get(pProgramId + "_" + pSemesterId + PROCESS_GPA_CGPA_PROMOTION));
    }
  }
}
