package org.ums.cache;

import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterWithDrawalManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 3/23/2016.
 */
public class SemesterWithdrawalCache extends
    ContentCache<SemesterWithdrawal, MutableSemesterWithdrawal, Integer, SemesterWithDrawalManager>
    implements SemesterWithDrawalManager {

  CacheManager<SemesterWithdrawal, Integer> mCacheManager;

  public SemesterWithdrawalCache(CacheManager<SemesterWithdrawal, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SemesterWithdrawal, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SemesterWithdrawal.class, pId);
  }

  @Override
  public List<SemesterWithdrawal> getByDeptForEmployee(String deptId) {
    return getManager().getByDeptForEmployee(deptId);
  }

  @Override
  public SemesterWithdrawal getStudentsRecord(String studentId, int semesterId, int year,
      int semester) {
    return getManager().getStudentsRecord(studentId, semesterId, year, semester);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForHead(String teacherId) {
    return getManager().getSemesterWithdrawalForHead(teacherId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForAAO(String employeeId) {
    return getManager().getSemesterWithdrawalForAAO(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForVC(String employeeId) {
    return getManager().getSemesterWithdrawalForVC(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForRegistrar(String employeeId) {
    return getManager().getSemesterWithdrawalForRegistrar(employeeId);
  }

  @Override
  public List<SemesterWithdrawal> getSemesterWithdrawalForDeputyRegistrar(String employeeId) {
    return getManager().getSemesterWithdrawalForDeputyRegistrar(employeeId);
  }
}
