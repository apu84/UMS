package org.ums.manager;


import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.immutable.StudentRecord;

import java.util.List;

public interface StudentRecordManager extends ContentManager<StudentRecord, MutableStudentRecord, Integer> {
  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId) throws Exception;

  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId,
                                        final StudentRecord.Type pType) throws Exception;

  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId,
                                        final Integer pYear, final Integer pAcademicSemester) throws Exception;

  List<StudentRecord> getStudentRecords(final Integer pProgramId, final Integer pSemesterId,
                                        final Integer pYear, final Integer pAcademicSemester,
                                        final StudentRecord.Type pType) throws Exception;
}
