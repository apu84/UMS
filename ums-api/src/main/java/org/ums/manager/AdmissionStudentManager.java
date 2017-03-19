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
public interface AdmissionStudentManager extends ContentManager<AdmissionStudent, MutableAdmissionStudent, String> {

  List<AdmissionStudent> getTaletalkData(final int pSemesterId, ProgramType pProgramType);

  List<AdmissionStudent> getTaletalkData(final int pSemesterId, final QuotaType pQuotaType, String unit,
      ProgramType pProgramType);

  List<AdmissionStudent> getTaletalkData(final int pSemesterId, final QuotaType pQuotaType, int fromMeritSerialNumber,
      int toMeritSerialNumber);

  int saveTaletalkData(final List<MutableAdmissionStudent> students);

  int getDataSize(final int pSemesterId, ProgramType pProgramType);

  List<AdmissionStudent> getMeritList(final int pSemesterId, final QuotaType pQuotaType, String pUnit,
      ProgramType pProgramType);

  List<AdmissionStudent> getAdmissionStudent(int pSemesterId, MigrationStatus pMigrationStatus);

  int updateStudentsAllocatedProgram(final AdmissionStudent pAdmissionStudent, final MigrationStatus pMigrationStatus);

  AdmissionStudent getAdmissionStudent(final int pSemesterId, ProgramType pProgramType, String pReceiptId);

  AdmissionStudent getAdmissionStudent(final int pSemesterId, QuotaType pQuotaType, int pMeritSerialNo);

  int saveMeritList(final List<MutableAdmissionStudent> pStudents);

  int updateDepartmentSelection(final MutableAdmissionStudent pStudent, DepartmentSelectionType pDepartmentSelectionType);

  int updateAdmissionMigrationStatus(List<MutableAdmissionStudent> pStudents);

  AdmissionStudent getNextStudentForDepartmentSelection(final int pSemesterId, final ProgramType pProgramType,
      final String pUnit, final String pQuota, int pMeritSerialNo);

  // kawsurilu

  AdmissionStudent getByStudentId(final String pStudentId);

  int setVerificationStatusAndUndertakenDate(final MutableAdmissionStudent pStudent);

  List<AdmissionStudent> getAllCandidates(final ProgramType pProgramType, final int pSemesterId);

  List<AdmissionStudent> getNewStudentByReceiptId(final int pSemesterId, final String receiptId);

  //
}
