package org.ums.cache;

import org.ums.domain.model.immutable.StudentRecord;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.manager.CacheManager;
import org.ums.manager.StudentRecordManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class StudentRecordCache extends ContentCache<StudentRecord, MutableStudentRecord, Integer, StudentRecordManager>
    implements StudentRecordManager {
  private CacheManager<StudentRecord> mCacheManager;

  public StudentRecordCache(final CacheManager<StudentRecord> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<StudentRecord> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(StudentRecord.class, pId);
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId) throws Exception {
    List<StudentRecord> readOnlys = getManager().getStudentRecords(pProgramId, pSemesterId);
    for (StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId,
                                               StudentRecord.Type pType) throws Exception {
    List<StudentRecord> readOnlys = getManager().getStudentRecords(pProgramId, pSemesterId, pType);
    for (StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
                                               Integer pAcademicSemester) throws Exception {
    List<StudentRecord> readOnlys = getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester);
    for (StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<StudentRecord> getStudentRecords(Integer pProgramId, Integer pSemesterId, Integer pYear,
                                               Integer pAcademicSemester, StudentRecord.Type pType) throws Exception {
    List<StudentRecord> readOnlys = getManager().getStudentRecords(pProgramId, pSemesterId, pYear, pAcademicSemester, pType);
    for (StudentRecord readOnly : readOnlys) {
      getCacheManager().put(getCacheKey(readOnly.getId()), readOnly);
    }
    return readOnlys;
  }
}
