package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.DepartmentSelectionType;
import org.ums.enums.MigrationStatus;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public interface AdmissionStudentManager extends
    ContentManager<AdmissionStudent, MutableAdmissionStudent, String> {

  List<AdmissionStudent> getTaletalkData(final int pSemesterId, ProgramType pProgramType);

  List<AdmissionStudent> getTaletalkData(final int pSemesterId, final QuotaType pQuotaType,
      String unit, ProgramType pProgramType);

  int saveTaletalkData(final List<MutableAdmissionStudent> students);

  int getDataSize(final int pSemesterId, ProgramType pProgramType);

  List<AdmissionStudent> getMeritList(final int pSemesterId, final QuotaType pQuotaType,
      String pUnit, ProgramType pProgramType);

  int updateStudentsAllocatedProgram(final AdmissionStudent pAdmissionStudent,
      final MigrationStatus pMigrationStatus);

  AdmissionStudent getAdmissionStudent(final int pSemesterId, ProgramType pProgramType,
      String pReceiptId);

  int saveMeritList(final List<MutableAdmissionStudent> pStudents);

  int updateDepartmentSelection(final MutableAdmissionStudent pStudent,
      DepartmentSelectionType pDepartmentSelectionType);

  AdmissionStudent getNextStudentForDepartmentSelection(final int pSemesterId,
      final ProgramType pProgramType, final String pUnit, final String pQuota);

  // kawsurilu

  AdmissionStudent getByStudentId(final String pStudentId);

  int setVerificationStatusAndUndertakenDate(final MutableAdmissionStudent pStudent);

  // int setVerificationStatus(final MutableAdmissionStudent pStudent);

  // int setUndertakenDate(final MutableAdmissionStudent pStudent);

  List<AdmissionStudent> getAllCandidates(final ProgramType pProgramType, final int pSemesterId);

  List<AdmissionStudent> getNewStudentByReceiptId(final int pSemesterId, final String receiptId);

  //
}
