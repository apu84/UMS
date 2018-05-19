package org.ums.manager;

import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.immutable.StudentRecord;

import java.util.List;

public interface StudentRecordManager extends ContentManager<StudentRecord, MutableStudentRecord, Long> {
  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId);

  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId,
      final StudentRecord.Type pType);

  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final Integer pAcademicSemester);

  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final Integer pAcademicSemester, final StudentRecord.Type pType);

  List<StudentRecord> getStudentRecords(final String pStudentId, final Integer pSemesterId, final Integer pYear,
      final Integer pAcademicSemester);

  StudentRecord getStudentRecord(final String pStudentId, final Integer pSemesterId);

  List<StudentRecord> getStudentRecord(final String pStudentId);

  boolean exists(final String pStudentId, final int pSemesterId);
}
