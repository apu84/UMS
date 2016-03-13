package org.ums.services.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.manager.*;
import org.ums.message.MessageResource;
import org.ums.persistent.model.PersistentSemesterEnrollment;
import org.ums.persistent.model.PersistentStudentRecord;
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
  ContentManager<Program, MutableProgram, Integer> mProgramManager;

  @Autowired
  SemesterEnrollmentManager mSemesterEnrollmentManager;

  @Autowired
  MessageResource mMessageResource;

  /***
   * Enroll students of particular program to new Semester
   * @param pType
   * @param pNewSemesterId
   * @param pProgramId
   * @param pToYear
   * @param pToAcademicSemester
   * @throws Exception
   */
  @Transactional
  public GenericResponse<Map> saveEnrollment(final SemesterEnrollment.Type pType,
                      final Integer pNewSemesterId,
                      final Integer pProgramId,
                      final Integer pToYear,
                      final Integer pToAcademicSemester) throws Exception {

    GenericResponse<Map> enrollmentStatus = enrollmentStatus(pType, pNewSemesterId, pProgramId, pToYear, pToAcademicSemester);
    if (enrollmentStatus.getResponseType() == GenericResponse.ResponseType.FAILED) {
      return enrollmentStatus;
    }

    Program program = mProgramManager.get(pProgramId);
    Semester previousSemester = mSemesterManager.getPreviousSemester(pNewSemesterId, program.getProgramType().getId());

    List<EnrollmentFromTo> enrollmentFromToList = mEnrollmentFromToManager.getEnrollmentFromTo(pProgramId);

    EnrollmentFromTo currentEnrollmentFromTo = null;
    for (EnrollmentFromTo enrollment : enrollmentFromToList) {
      if (enrollment.getToYear().intValue() == pToYear
          && enrollment.getToSemester().intValue() == pToAcademicSemester) {
        currentEnrollmentFromTo = enrollment;
        break;
      }
    }

    if (currentEnrollmentFromTo != null) {

      if (pType == SemesterEnrollment.Type.TEMPORARY) {

        MutableSemesterEnrollment semesterEnrollment = new PersistentSemesterEnrollment();
        semesterEnrollment.setType(pType);
        semesterEnrollment.setProgramId(pProgramId);
        semesterEnrollment.setYear(pToYear);
        semesterEnrollment.setAcademicSemester(pToAcademicSemester);
        semesterEnrollment.setSemesterId(pNewSemesterId);
        semesterEnrollment.commit(false);

        List<StudentRecord> studentRecords = mStudentRecordManager.getStudentRecords(pProgramId, previousSemester.getId(),
            currentEnrollmentFromTo.getFromYear(), currentEnrollmentFromTo.getFromSemester());

        if (studentRecords.size() > 0) {
          List<MutableStudent> mutableStudents = new ArrayList<>();
          List<MutableStudentRecord> mutableStudentRecords = new ArrayList<>();

          for (StudentRecord studentRecord : studentRecords) {
            MutableStudentRecord mutableStudentRecord = new PersistentStudentRecord();
            mutableStudentRecord.setStudentId(studentRecord.getStudentId());
            mutableStudentRecord.setSemesterId(pNewSemesterId);
            mutableStudentRecord.setProgramId(pProgramId);
            mutableStudentRecord.setYear(currentEnrollmentFromTo.getToYear());
            mutableStudentRecord.setAcademicSemester(currentEnrollmentFromTo.getToSemester());
            mutableStudentRecord.setType(StudentRecord.Type.TEMPORARY);
            mutableStudentRecord.setStatus(StudentRecord.Status.UNKNOWN);
            mutableStudentRecords.add(mutableStudentRecord);

            MutableStudent mutableStudent = mStudentManager.get(studentRecord.getStudentId()).edit();
            mutableStudent.setEnrollmentType(Student.EnrollmentType.TEMPORARY);
            mutableStudent.setCurrentYear(currentEnrollmentFromTo.getToYear());
            mutableStudent.setCurrentAcademicSemester(currentEnrollmentFromTo.getToSemester());
            mutableStudents.add(mutableStudent);
          }

          mStudentRecordManager.create(mutableStudentRecords);
          mStudentManager.update(mutableStudents);
        }
      } else {
         /*
          *  Done:
          *   1. Find students eligible for permanent enrollment, that is who has StudentRecord.Type.TEMPORARY as type.
          *   2. Find StudentRecord for all students for previous semester. Create a map of studentId as key and student record as value
          *   3. Update StudentRecord set type = StudentRecord.Type.REGULAR for students who has status == StudentRecord.Status.PASSED in the previous semester
          *   4. Update StudentRecord set type = StudentRecord.Type.READMISSION_REQUIRED for students who has status == StudentRecord.Status.FAILED in the previous semester
          *     4.1 Update StudentRecord set year and semester from previous semester
          */
        SemesterEnrollment semesterEnrollment = mSemesterEnrollmentManager.getEnrollmentStatus(SemesterEnrollment.Type.TEMPORARY, pProgramId,
            pNewSemesterId, pToYear, pToAcademicSemester);
        MutableSemesterEnrollment mutableSemesterEnrollment = semesterEnrollment.edit();
        mutableSemesterEnrollment.setType(SemesterEnrollment.Type.PERMANENT);
        mutableSemesterEnrollment.commit(true);

        List<StudentRecord> temporaryEnrolledStudentRecords = mStudentRecordManager.getStudentRecords(pProgramId,
            pNewSemesterId, pToYear, pToAcademicSemester, StudentRecord.Type.TEMPORARY);

        if (pToYear == UmsUtils.FIRST && pToAcademicSemester == UmsUtils.FIRST
            && temporaryEnrolledStudentRecords.size() > 0) {
          permanentEnrollmentNewStudents(temporaryEnrolledStudentRecords);

        } else {
          List<StudentRecord> previousSemesterStudentRecords = mStudentRecordManager.getStudentRecords(pProgramId,
              previousSemester.getId(), currentEnrollmentFromTo.getFromYear(), currentEnrollmentFromTo.getFromSemester());
          if (previousSemesterStudentRecords.size() > 0) {
            Map<String, StudentRecord> studentRecordMap = toMap(previousSemesterStudentRecords);

            List<MutableStudentRecord> mutableStudentRecords = new ArrayList<>();
            List<MutableStudent> mutableStudents = new ArrayList<>();

            for (StudentRecord studentRecord : temporaryEnrolledStudentRecords) {

              Student student = mStudentManager.get(studentRecord.getStudentId());
              MutableStudent mutableStudent = student.edit();

              MutableStudentRecord mutableStudentRecord = studentRecord.edit();
              StudentRecord previousSemesterRecord = studentRecordMap.get(studentRecord.getStudentId());

              if (previousSemesterRecord.getStatus() == StudentRecord.Status.PASSED) {
                mutableStudentRecord.setType(StudentRecord.Type.REGULAR);
                mutableStudent.setEnrollmentType(Student.EnrollmentType.ACTUAL);
              } else if (previousSemesterRecord.getStatus() == StudentRecord.Status.FAILED) {
                mutableStudentRecord.setType(StudentRecord.Type.REGULAR);
                mutableStudentRecord.setYear(previousSemesterRecord.getYear());
                mutableStudentRecord.setAcademicSemester(previousSemesterRecord.getAcademicSemester());

                mutableStudent.setEnrollmentType(Student.EnrollmentType.ACTUAL);
                mutableStudent.setCurrentYear(previousSemesterRecord.getYear());
                mutableStudent.setCurrentAcademicSemester(previousSemesterRecord.getAcademicSemester());
              }

              mutableStudentRecords.add(mutableStudentRecord);
              mutableStudents.add(mutableStudent);
            }

            mStudentRecordManager.update(mutableStudentRecords);
            mStudentManager.update(mutableStudents);
          }
        }
      }
    }
    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESSFUL, "Semester enrolled successfully");
  }

  @Override
  @Transactional
  public GenericResponse<Map> saveEnrollment(SemesterEnrollment.Type pType, Integer pNewSemesterId, Integer pProgramId) throws Exception {
    List<EnrollmentFromTo> enrollmentFromToList = mEnrollmentFromToManager.getEnrollmentFromTo(pProgramId);
    for (EnrollmentFromTo enrollment : enrollmentFromToList) {
      GenericResponse<Map> response = saveEnrollment(pType, pNewSemesterId, pProgramId, enrollment.getToYear(), enrollment.getToSemester());
      if (response.getResponseType() == GenericResponse.ResponseType.FAILED) {
        return response;
      }
    }
    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESSFUL, mMessageResource.getMessage("enrollment.successful"));
  }

  private Map<String, StudentRecord> toMap(List<StudentRecord> pStudentRecordList) {
    Map<String, StudentRecord> studentRecordMap = new HashMap<>();
    for (StudentRecord studentRecord : pStudentRecordList) {
      studentRecordMap.put(studentRecord.getStudentId(), studentRecord);
    }
    return studentRecordMap;
  }

  private GenericResponse<Map> enrollmentStatus(final SemesterEnrollment.Type pType,
                                                final Integer pNewSemesterId,
                                                final Integer pProgramId,
                                                final Integer pToYear,
                                                final Integer pToAcademicSemester) throws Exception {
    //if temporary enrollment, then check whether enrollment is already done. if permanent enrollment then check whether
    //temporary is done first.
    SemesterEnrollment semesterEnrollmentStatus = mSemesterEnrollmentManager.getEnrollmentStatus(pType, pProgramId,
        pNewSemesterId, pToYear, pToAcademicSemester);

    if (semesterEnrollmentStatus != null) {

      return new GenericMessageResponse(GenericResponse.ResponseType.FAILED,
          mMessageResource.getMessage("semester.enrollment.already.done",
              StringUtils.capitalize(pType.toString().toLowerCase()),
              UmsUtils.getNumberWithSuffix(pToYear),
              UmsUtils.getNumberWithSuffix(pToAcademicSemester),
              semesterEnrollmentStatus.getSemester().getName()));

    } else if (pType == SemesterEnrollment.Type.TEMPORARY) {

      SemesterEnrollment permanentEnrollmentStatus
          = mSemesterEnrollmentManager.getEnrollmentStatus(SemesterEnrollment.Type.PERMANENT,
          pProgramId,
          pNewSemesterId,
          pToYear,
          pToAcademicSemester);

      if (permanentEnrollmentStatus != null) {
        return new GenericMessageResponse(GenericResponse.ResponseType.FAILED,
            mMessageResource.getMessage("semester.enrollment.already.done",
                StringUtils.capitalize(SemesterEnrollment.Type.PERMANENT.toString().toLowerCase()),
                UmsUtils.getNumberWithSuffix(pToYear),
                UmsUtils.getNumberWithSuffix(pToAcademicSemester),
                permanentEnrollmentStatus.getSemester().getName()));
      }
    } else if (pType == SemesterEnrollment.Type.PERMANENT) {
      SemesterEnrollment temporaryEnrollmentStatus
          = mSemesterEnrollmentManager.getEnrollmentStatus(SemesterEnrollment.Type.TEMPORARY,
          pProgramId,
          pNewSemesterId,
          pToYear,
          pToAcademicSemester);

      if (temporaryEnrollmentStatus == null) {
        return new GenericMessageResponse(GenericResponse.ResponseType.FAILED,
            mMessageResource.getMessage("semester.temporary.enrollment.required",
                UmsUtils.getNumberWithSuffix(pToYear),
                UmsUtils.getNumberWithSuffix(pToAcademicSemester)));
      }
    }

    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESSFUL);
  }

  private void permanentEnrollmentNewStudents(final List<StudentRecord> pStudentRecordList) throws Exception {

    List<MutableStudentRecord> mutableStudentRecords = new ArrayList<>();
    List<MutableStudent> mutableStudents = new ArrayList<>();

    for (StudentRecord studentRecord : pStudentRecordList) {

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
}
