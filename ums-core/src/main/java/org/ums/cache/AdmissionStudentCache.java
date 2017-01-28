package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.DepartmentSelectionType;
import org.ums.enums.MigrationStatus;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class AdmissionStudentCache extends
    ContentCache<AdmissionStudent, MutableAdmissionStudent, String, AdmissionStudentManager>
    implements AdmissionStudentManager {

  private CacheManager<AdmissionStudent, String> mCacheManager;

  public AdmissionStudentCache(CacheManager<AdmissionStudent, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdmissionStudent, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(AdmissionStudent.class, pId);
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId, ProgramType pProgramType) {
    return getManager().getTaletalkData(pSemesterId, pProgramType);
  }

  @Override
  public int saveTaletalkData(List<MutableAdmissionStudent> students) {
    return getManager().saveTaletalkData(students);
  }

  @Override
  public int getDataSize(int pSemesterId, ProgramType pProgramType) {
    return getManager().getDataSize(pSemesterId, pProgramType);
  }

  @Override
  public List<AdmissionStudent> getMeritList(int pSemesterId, QuotaType pQuotaType, String pUnit,
      ProgramType pProgramType) {
    return getManager().getMeritList(pSemesterId, pQuotaType, pUnit, pProgramType);
  }

  @Override
  public List<AdmissionStudent> getTaletalkData(int pSemesterId, QuotaType pQuotaType, String unit,
      ProgramType pProgramType) {
    return getManager().getTaletalkData(pSemesterId, pQuotaType, unit, pProgramType);
  }

  @Override
  public int saveMeritList(List<MutableAdmissionStudent> pStudents) {
    return getManager().saveMeritList(pStudents);
  }

  // kawsurilu

  @Override
  public AdmissionStudent getNewStudentByReceiptId(String pProgramType, int pSemsterId,
      String pReceiptId) {
    return getManager().getNewStudentByReceiptId(pProgramType, pSemsterId, pReceiptId);
  }

  @Override
  public int setVerificationStatus(MutableAdmissionStudent pStudent) {
    return getManager().setVerificationStatus(pStudent);
  }

  @Override
  public List<AdmissionStudent> getAllNewCandidates(String pProgramType, int pSemesterId) {
    return getManager().getAllNewCandidates(pProgramType, pSemesterId);
  }

  @Override
  public AdmissionStudent getByStudentId(String pStudentId) {
    return getManager().getByStudentId(pStudentId);
  }

  //

  @Override
  public AdmissionStudent getAdmissionStudent(int pSemesterId, ProgramType pProgramType,
      String pReceiptId) {
    return getManager().getAdmissionStudent(pSemesterId, pProgramType, pReceiptId);
  }

  @Override
  public int updateDepartmentSelection(MutableAdmissionStudent pStudent,
      DepartmentSelectionType pDepartmentSelectionType) {
    return getManager().updateDepartmentSelection(pStudent, pDepartmentSelectionType);
  }

  @Override
  public AdmissionStudent getNextStudentForDepartmentSelection(int pSemesterId,
      ProgramType pProgramType, String pUnit, String pQuota) {
    return getManager().getNextStudentForDepartmentSelection(pSemesterId, pProgramType, pUnit,
        pQuota);
  }

  @Override
  public List<AdmissionStudent> getNewStudentByReceiptId(int pSemesterId, String receiptId) {
    return getNewStudentByReceiptId(pSemesterId, receiptId);
  }

  @Override
  public int updateStudentsAllocatedProgram(AdmissionStudent pAdmissionStudent,
      MigrationStatus pMigrationStatus) {
    return getManager().updateStudentsAllocatedProgram(pAdmissionStudent, pMigrationStatus);
  }
}
