package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.DepartmentSelectionType;
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

  AdmissionStudent getAdmissionStudent(final int pSemesterId, ProgramType pProgramType,
      String pReceiptId);

  int saveMeritList(final List<MutableAdmissionStudent> pStudents);

  List<AdmissionStudent> getNewStudentByReceiptId(final String pProgramType, final int pSemesterId,
      final String receiptId);
  int updateDepartmentSelection(final MutableAdmissionStudent pStudent,
      DepartmentSelectionType pDepartmentSelectionType);

  List<AdmissionStudent> getNewStudentByReceiptId(final int pSemesterId, final String receiptId);

  int saveVerificationStatus(final MutableAdmissionStudent pStudent);

  AdmissionStudent getByStudentId(final String pStudentId);
  List<AdmissionStudentCertificate> getAdmissionStudentCertificateLists();

  AdmissionStudent getNextStudentForDepartmentSelection(final int pSemesterId,
      final ProgramType pProgramType, final String pUnit, final String pQuota);
}
