package org.ums.readmission.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.*;
import org.ums.enums.CourseType;
import org.ums.fee.semesterfee.SemesterAdmissionStatus;
import org.ums.fee.semesterfee.SemesterAdmissionStatusManager;
import org.ums.formatter.DateFormat;
import org.ums.manager.*;
import org.ums.readmission.MutableReadmissionApplication;
import org.ums.readmission.PersistentReadmissionApplication;
import org.ums.readmission.ReadmissionApplication;
import org.ums.readmission.ReadmissionApplicationManager;
import org.ums.services.academic.ProcessResult;

@Component
public class ReadmissionHelper {
  @Autowired
  private StudentRecordManager mStudentRecordManager;
  @Autowired
  private ReadmissionApplicationManager mReadmissionApplicationManager;
  @Autowired
  private SemesterAdmissionStatusManager mSemesterAdmissionStatusManager;
  @Autowired
  private SemesterManager mSemesterManager;
  @Autowired
  private StudentManager mStudentManager;
  @Autowired
  private UGRegistrationResultManager mUgRegistrationResultManager;
  @Autowired
  private DateFormat mDateFormat;
  @Autowired
  private ParameterSettingManager mParameterSettingManager;

  JsonObject appliedReadmissionCourses(String pStudentId, Integer pSemesterId) {
    List<ReadmissionApplication> applications = readmissionApplication(pStudentId, pSemesterId);
    Optional<List<UGRegistrationResult>> failedCourses = lastSemesterFailedCourses(pStudentId, pSemesterId);
    JsonObjectBuilder objectBuilder;
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    if(failedCourses.isPresent()) {
      for(final ReadmissionApplication application : applications) {
        objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("courseId", application.getCourseId());
        objectBuilder.add("courseNo", application.getCourse().getNo());
        objectBuilder.add("courseTitle", application.getCourse().getTitle());
        objectBuilder.add("courseCrHr", application.getCourse().getCrHr());
        List<UGRegistrationResult> failed = failedCourses.get();
        Optional<UGRegistrationResult> failedCourse = failed.stream()
            .filter(result -> result.getCourseId().equalsIgnoreCase(application.getCourseId())).findFirst();
        if(failedCourse.isPresent()) {
          objectBuilder.add("lastAppeared", failedCourse.get().getSemester().getName());
          objectBuilder.add("appliedOn", mDateFormat.format(application.getAppliedOn()));
        }
        arrayBuilder.add(objectBuilder);
      }
    }
    objectBuilder = Json.createObjectBuilder();
    objectBuilder.add("entries", arrayBuilder);
    return objectBuilder.build();
  }

  JsonObject failedCoursesForApplication(String pStudentId, Integer pSemesterId) {
    Optional<List<UGRegistrationResult>> failedCourses = lastSemesterFailedCourses(pStudentId, pSemesterId);
    JsonObjectBuilder objectBuilder = null;
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    if(failedCourses.isPresent()) {
      for(UGRegistrationResult result : failedCourses.get()) {
        objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("courseId", result.getCourseId());
        objectBuilder.add("courseNo", result.getCourse().getNo());
        objectBuilder.add("courseTitle", result.getCourse().getTitle());
        objectBuilder.add("courseCrHr", result.getCourse().getCrHr());
        objectBuilder.add("lastAppeared", result.getSemester().getName());
        arrayBuilder.add(objectBuilder);
      }
    }
    objectBuilder = Json.createObjectBuilder();
    objectBuilder.add("entries", arrayBuilder);
    return objectBuilder.build();
  }

  ReadmissionApplicationStatus readmissionApplicationStatus(String pStudentId, Integer pSemesterId) {
    if(isReadmissionApplicable(pStudentId, pSemesterId)) {
      if(alreadyAppliedForReadmission(pStudentId, pSemesterId)) {
        return ReadmissionApplicationStatus.APPLIED;
      }
      else {
        if(withinReadmissionApplicationSlot(pSemesterId)) {
          return ReadmissionApplicationStatus.ALLOWED;
        }
        else {
          return ReadmissionApplicationStatus.NOT_IN_READMISSION_SLOT;
        }
      }
    }
    else {
      return ReadmissionApplicationStatus.NOT_ALLOWED;
    }
  }

  ReadmissionApplicationStatus apply(String pStudentId, Integer pSemesterId, JsonObject pJsonObject) {
    ReadmissionHelper.ReadmissionApplicationStatus status = readmissionApplicationStatus(pStudentId, pSemesterId);
    if(status == ReadmissionHelper.ReadmissionApplicationStatus.ALLOWED) {
      if(!withinReadmissionApplicationSlot(pSemesterId)) {
        return ReadmissionApplicationStatus.NOT_IN_READMISSION_SLOT;
      }
      ReadmissionApplicationStatus validationStatus = validateApplication(pStudentId, pSemesterId, pJsonObject);
      if(validationStatus == ReadmissionApplicationStatus.ALLOWED) {
        if(pJsonObject != null && pJsonObject.containsKey("entries")) {
          JsonArray entries = pJsonObject.getJsonArray("entries");
          List<MutableReadmissionApplication> applications = new ArrayList<>();
          for(int i = 0; i < entries.size(); i++) {
            MutableReadmissionApplication application = new PersistentReadmissionApplication();
            application.setSemesterId(pSemesterId);
            application.setStudentId(pStudentId);
            application.setCourseId(entries.getJsonObject(i).getString("courseId"));
            application.setAppliedOn(new Date());
            application.setApplicationStatus(ReadmissionApplication.Status.APPLIED);
            applications.add(application);
          }
          mReadmissionApplicationManager.create(applications);
          return ReadmissionApplicationStatus.APPLIED;
        }
        else {
          return ReadmissionApplicationStatus.NOT_TAKEN_MINIMUM_NO_OF_COURSE;
        }
      }
      else {
        return validationStatus;
      }
    }
    return status;
  }

  private boolean isReadmissionApplicable(String pStudentId, Integer pSemesterId) {
    StudentRecord studentRecord = mStudentRecordManager.getStudentRecord(pStudentId, pSemesterId);
    return studentRecord.getType().equals(StudentRecord.Type.READMISSION_REQUIRED);
  }

  private boolean alreadyAppliedForReadmission(String pStudentId, Integer pSemesterId) {
    List<ReadmissionApplication> readmissionApplicationList = readmissionApplication(pStudentId, pSemesterId);
    return readmissionApplicationList != null && readmissionApplicationList.size() > 0;
  }

  private Optional<List<UGRegistrationResult>> resultInLastAppearedSemester(String pStudentId, Integer pSemesterId) {
    Student student = mStudentManager.get(pStudentId);
    List<Semester> previousSemesters =
        mSemesterManager.getPreviousSemesters(pSemesterId, student.getProgram().getProgramTypeId());
    for(Semester semester : previousSemesters) {
      Optional<SemesterAdmissionStatus> admissionStatus =
          mSemesterAdmissionStatusManager.getAdmissionStatus(pStudentId, semester.getId());
      if(admissionStatus.isPresent() && admissionStatus.get().isAdmitted()) {
        return Optional.of(mUgRegistrationResultManager.getResults(pStudentId, semester.getId()));
      }
    }
    return Optional.empty();
  }

  private List<ReadmissionApplication> readmissionApplication(String pStudentId, Integer pSemesterId) {
    return mReadmissionApplicationManager.getReadmissionApplication(pSemesterId, pStudentId);
  }

  private Optional<List<UGRegistrationResult>> lastSemesterFailedCourses(String pStudentId, Integer pSemesterId) {
    return failedCourses(pStudentId, pSemesterId, true);
  }

  private Optional<List<UGRegistrationResult>> failedCourses(String pStudentId, Integer pSemesterId, boolean all) {
    Optional<List<UGRegistrationResult>> results = resultInLastAppearedSemester(pStudentId, pSemesterId);
    if(results.isPresent()) {
      List<UGRegistrationResult> registrationResults =
          results.get().stream().filter(result -> result.getGradeLetter().equalsIgnoreCase("F")).collect(Collectors.toList());
      return Optional.of(registrationResults);
    }

    return Optional.empty();
  }

  private boolean withinReadmissionApplicationSlot(Integer pSemesterId) {
    ParameterSetting parameterSetting =
        mParameterSettingManager.getByParameterAndSemesterId(
            Parameter.ParameterName.APPLICATION_READMISSION.getLabel(), pSemesterId);
    Date now = new Date();
    return parameterSetting.getStartDate().before(now) && parameterSetting.getEndDate().after(now);
  }

  private ReadmissionApplicationStatus validateApplication(String pStudentId, Integer pSemesterId,
      JsonObject pJsonObject) {
    Optional<List<UGRegistrationResult>> allFailedCourses = failedCourses(pStudentId, pSemesterId, true);
    Optional<List<UGRegistrationResult>> semesterFailedCourses = lastSemesterFailedCourses(pStudentId, pSemesterId);
    if(pJsonObject != null && pJsonObject.containsKey("entries")) {
      JsonArray entries = pJsonObject.getJsonArray("entries");
      int noOfAppliedCourse = entries.size();
      if(allFailedCourses.isPresent()
          && (allFailedCourses.get().size() - noOfAppliedCourse) > ProcessResult.MAX_NO_FAILED_COURSE) {
        return ReadmissionApplicationStatus.NOT_TAKEN_MINIMUM_NO_OF_COURSE;
      }

      if(semesterFailedCourses.isPresent()) {
        if((semesterFailedCourses.get().size()
            - noOfAppliedCourse) > ProcessResult.MAX_NO_FAILED_COURSE_CURRENT_SEMESTER) {
          return ReadmissionApplicationStatus.NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE;
        }

        List<UGRegistrationResult> sessionalCourses = semesterFailedCourses.get().stream()
            .filter(result -> result.getCourse().getCourseType() == CourseType.SESSIONAL).collect(Collectors.toList());
        if(sessionalCourses != null && sessionalCourses.size() > 0) {
          for(UGRegistrationResult sessional : sessionalCourses) {
            if(!containsCourse(sessional, entries)) {
              return ReadmissionApplicationStatus.REQUIRES_SESSIONAL;
            }
          }
        }

        for(int i = 0; i < entries.size(); i++) {
          if(!containsCourse(entries.getJsonObject(i).getString("courseId"), semesterFailedCourses.get())) {
            return ReadmissionApplicationStatus.CONTAINS_INVALID_COURSE;
          }
        }
      }
    }
    return ReadmissionApplicationStatus.ALLOWED;
  }

  private boolean containsCourse(UGRegistrationResult pCourse, JsonArray pAppliedCourses) {
    for(int i = 0; i < pAppliedCourses.size(); i++) {
      if(pCourse.getCourse().getId().equalsIgnoreCase(pAppliedCourses.getJsonObject(i).getString("courseId"))) {
        return true;
      }
    }
    return false;
  }

  private boolean containsCourse(String pAppliedCourseId, List<UGRegistrationResult> pFailedCourses) {
    for(UGRegistrationResult result : pFailedCourses) {
      if(result.getCourse().getId().equalsIgnoreCase(pAppliedCourseId)) {
        return true;
      }
    }
    return false;
  }

  enum ReadmissionApplicationStatus {
    APPLIED,
    ALLOWED,
    NOT_ALLOWED,
    NOT_IN_READMISSION_SLOT,
    REQUIRES_SESSIONAL,
    NOT_TAKEN_MINIMUM_NO_OF_LAST_SEMESTER_FAILED_COURSE,
    NOT_TAKEN_MINIMUM_NO_OF_COURSE,
    CONTAINS_INVALID_COURSE;
  }
}
