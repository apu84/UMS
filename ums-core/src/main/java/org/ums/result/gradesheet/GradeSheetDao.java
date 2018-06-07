package org.ums.result.gradesheet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

import org.apache.commons.lang.NotImplementedException;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseRegType;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.services.academic.ProcessResult;

public class GradeSheetDao extends GradeSheetDaoDecorator {
  private StudentManager mStudentManager;
  private StudentRecordManager mStudentRecordManager;
  private UGRegistrationResultManager mUGRegistrationResultManager;
  private IdGenerator mIdGenerator;
  private TaskStatusManager mTaskStatusManager;
  private MarksSubmissionStatusManager mMarksSubmissionStatusManager;

  public GradeSheetDao(StudentManager pStudentManager, StudentRecordManager pStudentRecordManager,
      UGRegistrationResultManager pUGRegistrationResultManager, IdGenerator pIdGenerator,
      TaskStatusManager pTaskStatusManager, MarksSubmissionStatusManager pMarksSubmissionStatusManager) {
    mStudentManager = pStudentManager;
    mStudentRecordManager = pStudentRecordManager;
    mUGRegistrationResultManager = pUGRegistrationResultManager;
    mIdGenerator = pIdGenerator;
    mTaskStatusManager = pTaskStatusManager;
    mMarksSubmissionStatusManager = pMarksSubmissionStatusManager;
  }

  @Override
  public GradesheetModel get(String pStudentId, Integer pSemesterId) {
    Student student = mStudentManager.get(pStudentId);
    StudentRecord studentRecord = mStudentRecordManager.getStudentRecord(pStudentId, pSemesterId);
    List<UGRegistrationResult> semesterResults =
        mUGRegistrationResultManager.getSemesterResult(pStudentId, pSemesterId);
    List<UGRegistrationResult> allResults = mUGRegistrationResultManager.getResultUpToSemester(pStudentId, pSemesterId,
        student.getProgram().getProgramTypeId());
    final Map<String, Boolean> resultProcessed = new HashMap<>();

    String taskSemesterId =
        mTaskStatusManager.buildTaskId(student.getProgramId(), pSemesterId, ProcessResult.PROCESS_GPA_CGPA_PROMOTION);
    String taskYearSemesterId = mTaskStatusManager.buildTaskId(student.getProgramId(), pSemesterId,
        studentRecord.getYear(), studentRecord.getAcademicSemester(), ProcessResult.PROCESS_GPA_CGPA_PROMOTION);

    TaskStatus taskStatus = null;
    if(mTaskStatusManager.exists(taskSemesterId)) {
      taskStatus = mTaskStatusManager.get(taskSemesterId);
    }
    else if(mTaskStatusManager.exists(taskYearSemesterId)) {
      taskStatus = mTaskStatusManager.get(taskYearSemesterId);
    }

    if(taskStatus != null && taskStatus.getStatus() == TaskStatus.Status.COMPLETED) {
      resultProcessed.put("resultProcessed", Boolean.TRUE);
    }

    return new GradesheetModel() {
      @Override
      public String getStudentId() {
        return student.getId();
      }

      @Override
      public String getName() {
        return student.getFullName();
      }

      @Override
      public String getProgramName() {
        return student.getProgram().getLongName();
      }

      @Override
      public String getDepartmentName() {
        return student.getDepartment().getLongName();
      }

      @Override
      public String getEnrollmentSemesterName() {
        return student.getSemester().getName();
      }

      @Override
      public String getSemesterName() {
        return studentRecord.getSemester().getName();
      }

      @Override
      public String getYear() {
        return studentRecord.getYear().toString();
      }

      @Override
      public String getAcademicSemester() {
        return studentRecord.getAcademicSemester().toString();
      }

      @Override
      public String getSemesterCrHr() {
        return resultProcessed() ? studentRecord.getCompletedCrHr().toString() : "N/A";
      }

      @Override
      public String getCumulativeCrHr() {
        return resultProcessed() ? studentRecord.getTotalCompletedCrHr().toString() : "N/A";
      }

      @Override
      public String getGpa() {
        return resultProcessed() ? (studentRecord.getGPA().toString().length() > 5
            ? studentRecord.getGPA().toString().substring(0, 5) : studentRecord.getGPA().toString()) : "N/A";
      }

      @Override
      public String getCGpa() {
        return resultProcessed() ? (studentRecord.getCGPA().toString().length() > 5
            ? studentRecord.getCGPA().toString().substring(0, 5) : studentRecord.getCGPA().toString()) : "N/A";
      }

      @Override
      public CourseList<UGRegistrationResult> getRegularCourses() {
        if(resultProcessed()) {
          return new CourseList<UGRegistrationResult>() {
            @Override
            public List<UGRegistrationResult> getTheoryCourses() {
              return getCourses(semesterResults, CourseType.THEORY, CourseRegType.REGULAR);
            }

            @Override
            public List<UGRegistrationResult> getSessionalCourses() {
              return getCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.REGULAR);
            }
          };
        }
        else {
          return new CourseList<UGRegistrationResult>() {
            @Override
            public List<UGRegistrationResult> getTheoryCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.THEORY, CourseRegType.REGULAR);
            }

            @Override
            public List<UGRegistrationResult> getSessionalCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.REGULAR);
            }
          };
        }
      }

      @Override
      public CourseList<UGRegistrationResult> getImprovementCourses() {
        if(resultProcessed()) {
          return new CourseList<UGRegistrationResult>() {
            @Override
            public List<UGRegistrationResult> getTheoryCourses() {
              return getCourses(semesterResults, CourseType.THEORY, CourseRegType.IMPROVEMENT);
            }

            @Override
            public List<UGRegistrationResult> getSessionalCourses() {
              return getCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.IMPROVEMENT);
            }
          };
        }
        else {
          return new CourseList<UGRegistrationResult>() {
            @Override
            public List<UGRegistrationResult> getTheoryCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.THEORY, CourseRegType.IMPROVEMENT);
            }

            @Override
            public List<UGRegistrationResult> getSessionalCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.IMPROVEMENT);
            }
          };
        }
      }

      @Override
      public CourseList<UGRegistrationResult> getClearanceCourses() {
        if(resultProcessed()) {
          return new CourseList<UGRegistrationResult>() {
            @Override
            public List<UGRegistrationResult> getTheoryCourses() {
              return getCourses(semesterResults, CourseType.THEORY, CourseRegType.CLEARANCE);
            }

            @Override
            public List<UGRegistrationResult> getSessionalCourses() {
              return getCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.CLEARANCE);
            }
          };
        }
        else {
          return new CourseList<UGRegistrationResult>() {
            @Override
            public List<UGRegistrationResult> getTheoryCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.THEORY, CourseRegType.CLEARANCE);
            }

            @Override
            public List<UGRegistrationResult> getSessionalCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.CLEARANCE);
            }
          };
        }
      }

      @Override
      public CourseList<CarryRegistrationResult> getCarryCourses() {
        if(resultProcessed()) {
          return new CourseList<CarryRegistrationResult>() {
            @Override
            public List<CarryRegistrationResult> getTheoryCourses() {
              return getCourses(semesterResults, CourseType.THEORY, CourseRegType.CARRY).stream().map((result) -> {
                List<UGRegistrationResult> regularCourse =
                    allResults.stream().filter((pResult) -> pResult.getCourseId().equalsIgnoreCase(result.getCourseId())
                        && pResult.getType() == CourseRegType.REGULAR).collect(Collectors.toList());
                return getCarryRegistrationResult(result, regularCourse);
              }).collect(Collectors.toList());
            }

            @Override
            public List<CarryRegistrationResult> getSessionalCourses() {
              return getCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.CARRY).stream().map((result) -> {
                List<UGRegistrationResult> regularCourse =
                    allResults.stream().filter((pResult) -> pResult.getCourseId().equalsIgnoreCase(result.getCourseId())
                        && pResult.getType() == CourseRegType.REGULAR).collect(Collectors.toList());
                return getCarryRegistrationResult(result, regularCourse);
              }).collect(Collectors.toList());
            }
          };
        }
        else {
          return new CourseList<CarryRegistrationResult>() {
            @Override
            public List<CarryRegistrationResult> getTheoryCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.THEORY, CourseRegType.CARRY).stream()
                  .map((result) -> {
                    List<UGRegistrationResult> regularCourse = allResults.stream()
                        .filter((pResult) -> pResult.getCourseId().equalsIgnoreCase(result.getCourseId())
                            && pResult.getType() == CourseRegType.REGULAR)
                        .collect(Collectors.toList());
                    return getCarryRegistrationResult(result, regularCourse);
                  }).collect(Collectors.toList());
            }

            @Override
            public List<CarryRegistrationResult> getSessionalCourses() {
              return getCoEAcceptedCourses(semesterResults, CourseType.SESSIONAL, CourseRegType.CARRY).stream()
                  .map((result) -> {
                    List<UGRegistrationResult> regularCourse = allResults.stream()
                        .filter((pResult) -> pResult.getCourseId().equalsIgnoreCase(result.getCourseId())
                            && pResult.getType() == CourseRegType.REGULAR)
                        .collect(Collectors.toList());
                    return getCarryRegistrationResult(result, regularCourse);
                  }).collect(Collectors.toList());
            }
          };
        }
      }

      @Override
      public Remarks getRemarks() {
        return new Remarks() {
          @Override
          public String getComment() {
            if(!resultProcessed()) {
              return "N/A";
            }
            String remarks = studentRecord.getTabulationSheetRemarks();
            String comment;
            if(remarks.contains("with")) {
              comment = remarks.substring(0, remarks.indexOf("with")).trim();
            }
            else {
              comment = remarks.trim();
            }
            return comment;
          }

          @Override
          public String[] getCarryCourses() {
            if(!resultProcessed()) {
              return new String[]{};
            }
            String remarks = studentRecord.getTabulationSheetRemarks();
            String[] carry = null;
            if(remarks.contains("with")) {
              carry = remarks.substring(remarks.indexOf("with")).replace("with carry over in", "").split(",");
            }
            if(carry != null && carry.length > 0) {
              JsonArrayBuilder carryArray = Json.createArrayBuilder();
              Arrays.stream(carry).forEach((course) -> carryArray.add(course.trim()));
            }
            return carry;
          }
        };
      }

      @Override
      public Long getId() {
        return mIdGenerator.getNumericId();
      }

      @Override
      public MutableGradesheetModel edit() {
        throw new NotImplementedException();
      }

      @Override
      public boolean isResultProcessed() {
        return resultProcessed();
      }

      private boolean resultProcessed() {
        return resultProcessed.containsKey("resultProcessed") && resultProcessed.get("resultProcessed") == Boolean.TRUE;
      }

      private List<UGRegistrationResult> getCourses(final List<UGRegistrationResult> pResults, CourseType pCourseType,
          CourseRegType pCourseRegType) {
        return pResults.stream()
            .filter((result -> result.getCourse().getCourseType() == pCourseType && result.getType() == pCourseRegType))
            .collect(Collectors.toList());
      }

      private List<UGRegistrationResult> getCoEAcceptedCourses(final List<UGRegistrationResult> pResults,
          final CourseType pCourseType, final CourseRegType pCourseRegType) {
        return pResults.stream()
            .filter(
                (result -> result.getCourse().getCourseType() == pCourseType && result.getType() == pCourseRegType
                    && mMarksSubmissionStatusManager
                        .get(result.getSemesterId(), result.getCourseId(), result.getExamType())
                        .getStatus() == CourseMarksSubmissionStatus.ACCEPTED_BY_COE))
            .collect(Collectors.toList());
      }

      private CarryRegistrationResult getCarryRegistrationResult(UGRegistrationResult result,
          List<UGRegistrationResult> regularCourse) {
        return new CarryRegistrationResult() {
          @Override
          public MutableUGRegistrationResult edit() {
            return null;
          }

          @Override
          public String getLastModified() {
            return result.getLastModified();
          }

          @Override
          public Long getId() {
            return result.getId();
          }

          @Override
          public String getRegularYear() {
            if(regularCourse.size() > 0) {
              StudentRecord studentRecord = mStudentRecordManager.getStudentRecord(regularCourse.get(0).getStudentId(),
                  regularCourse.get(0).getSemesterId());
              return studentRecord.getYear().toString();
            }
            return "N/A";
          }

          @Override
          public String getRegularSemester() {
            if(regularCourse.size() > 0) {
              StudentRecord studentRecord = mStudentRecordManager.getStudentRecord(regularCourse.get(0).getStudentId(),
                  regularCourse.get(0).getSemesterId());
              return studentRecord.getAcademicSemester().toString();
            }
            return "N/A";
          }

          @Override
          public String getCourseId() {
            return result.getCourseId();
          }

          @Override
          public Course getCourse() {
            return result.getCourse();
          }

          @Override
          public Integer getSemesterId() {
            return result.getSemesterId();
          }

          @Override
          public Semester getSemester() {
            return result.getSemester();
          }

          @Override
          public String getStudentId() {
            return result.getStudentId();
          }

          @Override
          public Student getStudent() {
            return result.getStudent();
          }

          @Override
          public String getGradeLetter() {
            return result.getGradeLetter();
          }

          @Override
          public ExamType getExamType() {
            return result.getExamType();
          }

          @Override
          public CourseRegType getType() {
            return result.getType();
          }

          @Override
          public String getCourseNo() {
            return result.getCourseNo();
          }

          @Override
          public String getCourseTitle() {
            return result.getCourseTitle();
          }

          @Override
          public String getExamDate() {
            return result.getExamDate();
          }

          @Override
          public String getMessage() {
            return result.getMessage();
          }
        };
      }
    };
  }
}
