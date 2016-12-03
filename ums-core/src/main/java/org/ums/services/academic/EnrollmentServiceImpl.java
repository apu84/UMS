package org.ums.services.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.*;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;
import org.ums.manager.*;
import org.ums.message.MessageResource;
import org.ums.persistent.model.*;
import org.ums.response.type.GenericMessageResponse;
import org.ums.response.type.GenericResponse;
import org.ums.util.UmsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
  @Autowired
  StudentRecordManager mStudentRecordManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  EnrollmentFromToManager mEnrollmentFromToManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  ProgramManager mProgramManager;

  @Autowired
  SemesterEnrollmentManager mSemesterEnrollmentManager;

  @Autowired
  MessageResource mMessageResource;

  @Autowired
  SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  @Autowired
  CourseManager mCourseManager;

  @Autowired
  UGRegistrationResultManager mUGRegistrationResultManager;

  @Autowired
  UGTheoryMarksManager mUGTheoryMarksManager;

  @Autowired
  UGSessionalMarksManager mUGSessionalMarksManager;

  /***
   * Enroll students of particular program to new Semester
   * 
   * @param pType
   * @param pNewSemesterId
   * @param pProgramId
   * @param pToYear
   * @param pToAcademicSemester
   * @throws Exception
   */
  @Transactional
  public GenericResponse<Map> saveEnrollment(final SemesterEnrollment.Type pType,
      final Integer pNewSemesterId, final Integer pProgramId, final Integer pToYear,
      final Integer pToAcademicSemester) {

    GenericResponse<Map> enrollmentStatus =
        enrollmentStatus(pType, pNewSemesterId, pProgramId, pToYear, pToAcademicSemester);
    if(enrollmentStatus.getResponseType() == GenericResponse.ResponseType.ERROR) {
      return enrollmentStatus;
    }

    Program program = mProgramManager.get(pProgramId);
    Semester previousSemester =
        mSemesterManager.getPreviousSemester(pNewSemesterId, program.getProgramType().getId());

    List<EnrollmentFromTo> enrollmentFromToList =
        mEnrollmentFromToManager.getEnrollmentFromTo(pProgramId);

    EnrollmentFromTo currentEnrollmentFromTo = null;
    for(EnrollmentFromTo enrollment : enrollmentFromToList) {
      if(enrollment.getToYear().intValue() == pToYear
          && enrollment.getToSemester().intValue() == pToAcademicSemester) {
        currentEnrollmentFromTo = enrollment;
        break;
      }
    }

    int totalEnrolledStudent = 0;

    if(currentEnrollmentFromTo != null) {

      Syllabus syllabus =
          mSemesterSyllabusMapManager.getSyllabusForSemester(pNewSemesterId, pProgramId, pToYear,
              pToAcademicSemester);
      List<Course> mandatoryCourseList = null, mandatoryTheoryCourse = null, mandatorySessionalCourse =
          null;
      if(syllabus != null) {
        mandatoryCourseList =
            mCourseManager.getMandatoryCourses(syllabus.getId(), pToYear, pToAcademicSemester);
        mandatoryTheoryCourse =
            mCourseManager
                .getMandatoryTheoryCourses(syllabus.getId(), pToYear, pToAcademicSemester);
        mandatorySessionalCourse =
            mCourseManager.getMandatorySesssionalCourses(syllabus.getId(), pToYear,
                pToAcademicSemester);
      }

      if(pType == SemesterEnrollment.Type.TEMPORARY) {

        MutableSemesterEnrollment semesterEnrollment = new PersistentSemesterEnrollment();
        semesterEnrollment.setType(pType);
        semesterEnrollment.setProgramId(pProgramId);
        semesterEnrollment.setYear(pToYear);
        semesterEnrollment.setAcademicSemester(pToAcademicSemester);
        semesterEnrollment.setSemesterId(pNewSemesterId);
        semesterEnrollment.commit(false);

        List<StudentRecord> studentRecords =
            mStudentRecordManager.getStudentRecords(pProgramId, previousSemester.getId(),
                currentEnrollmentFromTo.getFromYear(), currentEnrollmentFromTo.getFromSemester());

        if(studentRecords.size() > 0) {
          List<MutableStudent> mutableStudents = new ArrayList<>();
          List<MutableStudentRecord> mutableStudentRecords = new ArrayList<>();
          List<MutableUGRegistrationResult> registrationResults = new ArrayList<>();
          List<MutableUGTheoryMarks> ugTheoryMarks = new ArrayList<>();
          List<MutableUGSessionalMarks> ugSessionalMarks = new ArrayList<>();

          for(StudentRecord studentRecord : studentRecords) {
            MutableStudentRecord mutableStudentRecord = new PersistentStudentRecord();
            mutableStudentRecord.setStudentId(studentRecord.getStudentId());
            mutableStudentRecord.setSemesterId(pNewSemesterId);
            mutableStudentRecord.setProgramId(pProgramId);
            mutableStudentRecord.setYear(currentEnrollmentFromTo.getToYear());
            mutableStudentRecord.setAcademicSemester(currentEnrollmentFromTo.getToSemester());
            mutableStudentRecord.setType(StudentRecord.Type.TEMPORARY);
            mutableStudentRecord.setStatus(StudentRecord.Status.UNKNOWN);
            mutableStudentRecords.add(mutableStudentRecord);

            MutableStudent mutableStudent =
                mStudentManager.get(studentRecord.getStudentId()).edit();
            mutableStudent.setEnrollmentType(Student.EnrollmentType.TEMPORARY);
            mutableStudent.setCurrentYear(currentEnrollmentFromTo.getToYear());
            mutableStudent.setCurrentAcademicSemester(currentEnrollmentFromTo.getToSemester());
            mutableStudents.add(mutableStudent);

            insertRegistrationResult(mandatoryCourseList, pNewSemesterId,
                studentRecord.getStudentId(), ExamType.SEMESTER_FINAL, CourseRegType.REGULAR,
                registrationResults);

            insertTheoryMarks(mandatoryTheoryCourse, pNewSemesterId, studentRecord.getStudentId(),
                ExamType.SEMESTER_FINAL, CourseRegType.REGULAR, ugTheoryMarks);

            insertSessionalMarks(mandatorySessionalCourse, pNewSemesterId,
                studentRecord.getStudentId(), ExamType.SEMESTER_FINAL, CourseRegType.REGULAR,
                ugSessionalMarks);

            totalEnrolledStudent++;
          }

          mStudentRecordManager.create(mutableStudentRecords);
          mStudentManager.update(mutableStudents);
          mUGRegistrationResultManager.create(registrationResults);
          mUGTheoryMarksManager.create(ugTheoryMarks);
          mUGSessionalMarksManager.create(ugSessionalMarks);
        }
      }
      else {
        /*
         * Done: 1. Find students eligible for permanent enrollment, that is who has
         * StudentRecord.Type.TEMPORARY as type. 2. Find StudentRecord for all students for previous
         * semester. Create a map of studentId as key and student record as value 3. Update
         * StudentRecord set type = StudentRecord.Type.REGULAR for students who has status ==
         * StudentRecord.Status.PASSED in the previous semester 4. Update StudentRecord set type =
         * StudentRecord.Type.READMISSION_REQUIRED for students who has status ==
         * StudentRecord.Status.ERROR in the previous semester 4.1 Update StudentRecord set year and
         * semester from previous semester
         */
        SemesterEnrollment semesterEnrollment =
            mSemesterEnrollmentManager.getEnrollmentStatus(SemesterEnrollment.Type.TEMPORARY,
                pProgramId, pNewSemesterId, pToYear, pToAcademicSemester);

        MutableSemesterEnrollment mutableSemesterEnrollment = semesterEnrollment.edit();
        mutableSemesterEnrollment.setType(SemesterEnrollment.Type.PERMANENT);
        mutableSemesterEnrollment.commit(true);

        List<StudentRecord> temporaryEnrolledStudentRecords =
            mStudentRecordManager.getStudentRecords(pProgramId, pNewSemesterId, pToYear,
                pToAcademicSemester, StudentRecord.Type.TEMPORARY);

        if(pToYear == UmsUtils.FIRST && pToAcademicSemester == UmsUtils.FIRST
            && temporaryEnrolledStudentRecords.size() > 0) {
          permanentEnrollmentNewStudents(temporaryEnrolledStudentRecords);
          totalEnrolledStudent += temporaryEnrolledStudentRecords.size();

        }
        else {
          List<MutableUGRegistrationResult> registrationResults = new ArrayList<>();
          List<MutableUGTheoryMarks> ugTheoryMarks = new ArrayList<>();
          List<MutableUGSessionalMarks> ugSessionalMarks = new ArrayList<>();

          List<StudentRecord> previousSemesterStudentRecords =
              mStudentRecordManager.getStudentRecords(pProgramId, previousSemester.getId(),
                  currentEnrollmentFromTo.getFromYear(), currentEnrollmentFromTo.getFromSemester());
          if(previousSemesterStudentRecords.size() > 0) {
            Map<String, StudentRecord> studentRecordMap = toMap(previousSemesterStudentRecords);

            List<MutableStudentRecord> mutableStudentRecords = new ArrayList<>();
            List<MutableStudent> mutableStudents = new ArrayList<>();

            for(StudentRecord studentRecord : temporaryEnrolledStudentRecords) {

              Student student = mStudentManager.get(studentRecord.getStudentId());
              MutableStudent mutableStudent = student.edit();

              MutableStudentRecord mutableStudentRecord = studentRecord.edit();
              StudentRecord previousSemesterRecord =
                  studentRecordMap.get(studentRecord.getStudentId());

              if(previousSemesterRecord.getStatus() == StudentRecord.Status.PASSED) {
                mutableStudentRecord.setType(StudentRecord.Type.REGULAR);
                mutableStudent.setEnrollmentType(Student.EnrollmentType.ACTUAL);
              }
              else if(previousSemesterRecord.getStatus() == StudentRecord.Status.FAILED) {
                mutableStudentRecord.setType(StudentRecord.Type.READMISSION_REQUIRED);
                mutableStudentRecord.setYear(previousSemesterRecord.getYear());
                mutableStudentRecord.setAcademicSemester(previousSemesterRecord
                    .getAcademicSemester());

                mutableStudent.setEnrollmentType(Student.EnrollmentType.ACTUAL);
                mutableStudent.setCurrentYear(previousSemesterRecord.getYear());
                mutableStudent.setCurrentAcademicSemester(previousSemesterRecord
                    .getAcademicSemester());

                // remove from ug_registration_result, ug_theory_marks, ug_sessional_marks
                MutableUGRegistrationResult registrationResult =
                    new PersistentUGRegistrationResult();
                registrationResult.setStudentId(studentRecord.getStudentId());
                registrationResult.setSemesterId(pNewSemesterId);
                registrationResult.setExamType(ExamType.SEMESTER_FINAL);
                registrationResult.setType(CourseRegType.REGULAR);
                registrationResults.add(registrationResult);

                MutableUGTheoryMarks theoryMarks = new PersistentUGTheoryMarks();
                theoryMarks.setStudentId(studentRecord.getStudentId());
                theoryMarks.setSemesterId(pNewSemesterId);
                theoryMarks.setExamType(ExamType.SEMESTER_FINAL);
                theoryMarks.setType(CourseRegType.REGULAR);
                ugTheoryMarks.add(theoryMarks);

                MutableUGSessionalMarks sessionalMarks = new PersistentUGSessionalMarks();
                sessionalMarks.setStudentId(studentRecord.getStudentId());
                sessionalMarks.setSemesterId(pNewSemesterId);
                sessionalMarks.setExamType(ExamType.SEMESTER_FINAL);
                sessionalMarks.setType(CourseRegType.REGULAR);
                ugSessionalMarks.add(sessionalMarks);
              }

              mutableStudentRecords.add(mutableStudentRecord);
              mutableStudents.add(mutableStudent);
              totalEnrolledStudent++;
            }

            mStudentRecordManager.update(mutableStudentRecords);
            mStudentManager.update(mutableStudents);
            if(registrationResults.size() > 0) {
              mUGRegistrationResultManager.delete(registrationResults);
              mUGSessionalMarksManager.delete(ugSessionalMarks);
              mUGTheoryMarksManager.delete(ugTheoryMarks);
            }

          }
        }
      }
    }
    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS,
        mMessageResource.getMessage("enrollment.successful", UmsUtils.getNumberWithSuffix(pToYear),
            UmsUtils.getNumberWithSuffix(pToAcademicSemester), totalEnrolledStudent));
  }

  @Override
  @Transactional
  public GenericResponse<Map> saveEnrollment(SemesterEnrollment.Type pType, Integer pNewSemesterId,
      Integer pProgramId) {
    List<EnrollmentFromTo> enrollmentFromToList =
        mEnrollmentFromToManager.getEnrollmentFromTo(pProgramId);
    for(EnrollmentFromTo enrollment : enrollmentFromToList) {
      GenericResponse<Map> response =
          saveEnrollment(pType, pNewSemesterId, pProgramId, enrollment.getToYear(),
              enrollment.getToSemester());
      if(response.getResponseType() == GenericResponse.ResponseType.ERROR) {
        return response;
      }
    }
    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS,
        mMessageResource.getMessage("enrollment.successful"));
  }

  private Map<String, StudentRecord> toMap(List<StudentRecord> pStudentRecordList) {
    Map<String, StudentRecord> studentRecordMap = new HashMap<>();
    for(StudentRecord studentRecord : pStudentRecordList) {
      studentRecordMap.put(studentRecord.getStudentId(), studentRecord);
    }
    return studentRecordMap;
  }

  private GenericResponse<Map> enrollmentStatus(final SemesterEnrollment.Type pType,
      final Integer pNewSemesterId, final Integer pProgramId, final Integer pToYear,
      final Integer pToAcademicSemester) {
    // if temporary enrollment, then check whether enrollment is already done. if permanent
    // enrollment then check whether
    // temporary is done first.
    SemesterEnrollment semesterEnrollmentStatus =
        mSemesterEnrollmentManager.getEnrollmentStatus(pType, pProgramId, pNewSemesterId, pToYear,
            pToAcademicSemester);

    if(semesterEnrollmentStatus != null) {

      return new GenericMessageResponse(GenericResponse.ResponseType.ERROR,
          mMessageResource.getMessage("semester.enrollment.already.done", StringUtils
              .capitalize(pType.toString().toLowerCase()), UmsUtils.getNumberWithSuffix(pToYear),
              UmsUtils.getNumberWithSuffix(pToAcademicSemester), semesterEnrollmentStatus
                  .getSemester().getName()));

    }
    else if(pType == SemesterEnrollment.Type.TEMPORARY) {

      SemesterEnrollment permanentEnrollmentStatus =
          mSemesterEnrollmentManager.getEnrollmentStatus(SemesterEnrollment.Type.PERMANENT,
              pProgramId, pNewSemesterId, pToYear, pToAcademicSemester);

      if(permanentEnrollmentStatus != null) {
        return new GenericMessageResponse(GenericResponse.ResponseType.ERROR,
            mMessageResource.getMessage("semester.enrollment.already.done", StringUtils
                .capitalize(SemesterEnrollment.Type.PERMANENT.toString().toLowerCase()), UmsUtils
                .getNumberWithSuffix(pToYear), UmsUtils.getNumberWithSuffix(pToAcademicSemester),
                permanentEnrollmentStatus.getSemester().getName()));
      }
    }
    else if(pType == SemesterEnrollment.Type.PERMANENT) {
      SemesterEnrollment temporaryEnrollmentStatus =
          mSemesterEnrollmentManager.getEnrollmentStatus(SemesterEnrollment.Type.TEMPORARY,
              pProgramId, pNewSemesterId, pToYear, pToAcademicSemester);

      if(temporaryEnrollmentStatus == null) {
        return new GenericMessageResponse(GenericResponse.ResponseType.ERROR,
            mMessageResource.getMessage("semester.temporary.enrollment.required",
                UmsUtils.getNumberWithSuffix(pToYear),
                UmsUtils.getNumberWithSuffix(pToAcademicSemester)));
      }
    }

    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS);
  }

  private void permanentEnrollmentNewStudents(final List<StudentRecord> pStudentRecordList) {

    List<MutableStudentRecord> mutableStudentRecords = new ArrayList<>();
    List<MutableStudent> mutableStudents = new ArrayList<>();

    for(StudentRecord studentRecord : pStudentRecordList) {

      Student student = mStudentManager.get(studentRecord.getStudentId());
      MutableStudent mutableStudent = student.edit();

      MutableStudentRecord mutableStudentRecord = studentRecord.edit();

      mutableStudentRecord.setType(StudentRecord.Type.REGULAR);
      mutableStudent.setEnrollmentType(Student.EnrollmentType.ACTUAL);

      mutableStudentRecords.add(mutableStudentRecord);
      mutableStudents.add(mutableStudent);
    }

    mStudentRecordManager.update(mutableStudentRecords);
    mStudentManager.update(mutableStudents);
  }

  private void insertRegistrationResult(List<Course> pCourses, Integer pSemesterId,
      String pStudentId, ExamType pExamType, CourseRegType pCourseRegType,
      List<MutableUGRegistrationResult> pRegistrationResults) {
    for(Course course : pCourses) {
      MutableUGRegistrationResult registrationResult = new PersistentUGRegistrationResult();
      registrationResult.setStudentId(pStudentId);
      registrationResult.setCourseId(course.getId());
      registrationResult.setSemesterId(pSemesterId);
      registrationResult.setExamType(pExamType);
      registrationResult.setType(pCourseRegType);
      pRegistrationResults.add(registrationResult);
    }
  }

  private void insertTheoryMarks(List<Course> pCourses, Integer pSemesterId, String pStudentId,
      ExamType pExamType, CourseRegType pCourseRegType,
      List<MutableUGTheoryMarks> pRegistrationResults) {
    for(Course course : pCourses) {
      MutableUGTheoryMarks registrationResult = new PersistentUGTheoryMarks();
      registrationResult.setStudentId(pStudentId);
      registrationResult.setCourseId(course.getId());
      registrationResult.setSemesterId(pSemesterId);
      registrationResult.setExamType(pExamType);
      registrationResult.setType(pCourseRegType);
      pRegistrationResults.add(registrationResult);
    }
  }

  private void insertSessionalMarks(List<Course> pCourses, Integer pSemesterId, String pStudentId,
      ExamType pExamType, CourseRegType pCourseRegType,
      List<MutableUGSessionalMarks> pRegistrationResults) {
    for(Course course : pCourses) {
      MutableUGSessionalMarks registrationResult = new PersistentUGSessionalMarks();
      registrationResult.setStudentId(pStudentId);
      registrationResult.setCourseId(course.getId());
      registrationResult.setSemesterId(pSemesterId);
      registrationResult.setExamType(pExamType);
      registrationResult.setType(pCourseRegType);
      pRegistrationResults.add(registrationResult);
    }
  }
}
