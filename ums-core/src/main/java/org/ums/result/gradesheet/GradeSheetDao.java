package org.ums.result.gradesheet;

import java.util.List;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.generator.IdGenerator;
import org.ums.manager.StudentManager;
import org.ums.manager.StudentRecordManager;
import org.ums.manager.UGRegistrationResultManager;

public class GradeSheetDao extends GradeSheetDaoDecorator {
  private StudentManager mStudentManager;
  private StudentRecordManager mStudentRecordManager;
  private UGRegistrationResultManager mUGRegistrationResultManager;
  private IdGenerator mIdGenerator;

  public GradeSheetDao(StudentManager pStudentManager, StudentRecordManager pStudentRecordManager,
      UGRegistrationResultManager pUGRegistrationResultManager, IdGenerator pIdGenerator) {
    mStudentManager = pStudentManager;
    mStudentRecordManager = pStudentRecordManager;
    mUGRegistrationResultManager = pUGRegistrationResultManager;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public GradesheetModel get(String pStudentId, Integer pSemesterId) {
    return new GradesheetModel() {
      @Override
      public Student getStudent() {
        return mStudentManager.get(pStudentId);
      }

      @Override
      public StudentRecord getStudentRecord() {
        return mStudentRecordManager.getStudentRecord(pStudentId, pSemesterId);
      }

      @Override
      public List<UGRegistrationResult> getSemesterResults() {
        return mUGRegistrationResultManager.getSemesterResult(pStudentId, pSemesterId);
      }

      @Override
      public List<UGRegistrationResult> getAllResults() {
        return mUGRegistrationResultManager.getResultUpToSemester(pStudentId, pSemesterId, getStudent().getProgram()
            .getProgramTypeId());
      }

      @Override
      public Long getId() {
        return mIdGenerator.getNumericId();
      }

      @Override
      public MutableGradesheetModel edit() {
        return null;
      }
    };
  }
}
