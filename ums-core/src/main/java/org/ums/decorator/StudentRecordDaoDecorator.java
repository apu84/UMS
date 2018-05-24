package org.ums.decorator;

import java.util.List;

import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.manager.StudentRecordManager;

public class StudentRecordDaoDecorator extends
    ContentDaoDecorator<StudentRecord, MutableStudentRecord, Long, StudentRecordManager> implements
    StudentRecordManager {

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId) {
    return getManager().getStudentRecords(pProgramId, pSemesterId);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
      Integer pAcademicSemester) {
    return getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, StudentRecord.Type pType) {
    return getManager().getStudentRecords(pProgramId, pSemesterId, pType);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
      Integer pAcademicSemester, StudentRecord.Type pType) {
    return getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester, pType);
  }

  @Override
  public List<StudentRecord> getStudentRecords(String pStudentId, Integer pSemesterId, Integer pYear,
      Integer pAcademicSemester) {
    return getManager().getStudentRecords(pStudentId, pSemesterId, pYear, pAcademicSemester);
  }

  @Override
  public StudentRecord getStudentRecord(String pStudentId, Integer pSemesterId) {
    return getManager().getStudentRecord(pStudentId, pSemesterId);
  }

  @Override
  public List<StudentRecord> getStudentRecord(String pStudentId) {
    return getManager().getStudentRecord(pStudentId);
  }

  @Override
  public boolean exists(String pStudentId, int pSemesterId) {
    return getManager().exists(pStudentId, pSemesterId);
  }
}
