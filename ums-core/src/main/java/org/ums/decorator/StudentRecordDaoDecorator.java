package org.ums.decorator;

import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.manager.StudentRecordManager;

import java.util.List;

public class StudentRecordDaoDecorator
    extends ContentDaoDecorator<StudentRecord, MutableStudentRecord, Integer, StudentRecordManager>
    implements StudentRecordManager {
  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId) throws Exception {
    return getManager().getStudentRecords(pProgramId, pSemesterId);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
                                               Integer pAcademicSemester) throws Exception {
    return getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId,
                                               StudentRecord.Type pType) throws Exception {
    return getManager().getStudentRecords(pProgramId, pSemesterId, pType);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
                                               Integer pAcademicSemester, StudentRecord.Type pType) throws Exception {
    return getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester, pType);
  }

  @Override
  public List<StudentRecord> getStudentRecords(String pStudentId, Integer pSemesterId, Integer pYear, Integer pAcademicSemester) throws Exception {
    return getManager().getStudentRecords(pStudentId,pSemesterId,pYear,pAcademicSemester);
  }
}
