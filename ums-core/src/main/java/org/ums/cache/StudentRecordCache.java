package org.ums.cache;

import java.util.List;

import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.manager.CacheManager;
import org.ums.manager.StudentRecordManager;
import org.ums.util.CacheUtil;

public class StudentRecordCache extends
    ContentCache<StudentRecord, MutableStudentRecord, Integer, StudentRecordManager> implements
    StudentRecordManager {
  private CacheManager<StudentRecord, Integer> mCacheManager;

  public StudentRecordCache(final CacheManager<StudentRecord, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<StudentRecord, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(StudentRecord.class, pId);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId) {
    List<StudentRecord> readOnlys = getManager().getStudentRecords(pProgramId, pSemesterId);
    for(StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId,
      StudentRecord.Type pType) {
    List<StudentRecord> readOnlys = getManager().getStudentRecords(pProgramId, pSemesterId, pType);
    for(StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId,
      Integer pYear, Integer pAcademicSemester) {
    List<StudentRecord> readOnlys =
        getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester);
    for(StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId,
      Integer pYear, Integer pAcademicSemester, StudentRecord.Type pType) {
    List<StudentRecord> readOnlys =
        getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester, pType);
    for(StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<StudentRecord> getStudentRecords(String pStudentId, Integer pSemesterId,
      Integer pYear, Integer pAcademicSemester) {
    List<StudentRecord> readOnlys =
        getManager().getStudentRecords(pStudentId, pSemesterId, pYear, pAcademicSemester);
    for(StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public StudentRecord getStudentRecord(final String pStudentId, final Integer pSemesterId) {
    String cacheKey = getCacheKey(StudentRecord.class.toString(), pStudentId, pSemesterId);
    return cachedEntity(cacheKey, () -> getManager().getStudentRecord(pStudentId, pSemesterId));
  }
}
