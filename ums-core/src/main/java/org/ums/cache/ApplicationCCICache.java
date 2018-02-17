package org.ums.cache;

import javafx.application.Application;
import org.springframework.context.ApplicationContextInitializer;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 7/11/2016.
 */
public class ApplicationCCICache extends
    ContentCache<ApplicationCCI, MutableApplicationCCI, Long, ApplicationCCIManager> implements ApplicationCCIManager {

  CacheManager<ApplicationCCI, Long> mCacheManager;

  @Override
  public Integer getAllReords(String pApprovalStatus, String empDeptId) {
    return getManager().getAllReords(pApprovalStatus, empDeptId);
  }

  @Override
  public String getApplicationCCIForCarryLastfdate(Integer pSemesterId) {
    return getManager().getApplicationCCIForCarryLastfdate(pSemesterId);
  }

  @Override
  public int updatebank(MutableStudentPayment MutableStudentPayment) {
    return getManager().updatebank(MutableStudentPayment);
  }

  @Override
  public List<ApplicationCCI> getApplicationCCIForImprovementLimit(String pStudentId) {
    return getManager().getApplicationCCIForImprovementLimit(pStudentId);
  }

  @Override
  public List<ApplicationCCI> getApplicationCarryForHeadsApprovalAndAppiled(String pStudentId, Integer pSemesterId) {
    return getManager().getApplicationCarryForHeadsApprovalAndAppiled(pStudentId, pSemesterId);
  }

  @Override
  public List<ApplicationCCI> getByStudentId(String pApprovalStatus, String pStudentId, Integer pSemesterId,
      String empDeptId) {
    return getManager().getByStudentId(pApprovalStatus, pStudentId, pSemesterId, empDeptId);
  }

  @Override
  public List<ApplicationCCI> getTotalCarry(String pStudentId, Integer pSemesterId) {
    return getManager().getTotalCarry(pStudentId, pSemesterId);
  }

  @Override
  public List<ApplicationCCI> getApplicationCarryForHeadsApproval(String pApprovalStatus, Integer pCurentpage,
      Integer pItemPerpage, String empDeptId) {
    return getManager().getApplicationCarryForHeadsApproval(pApprovalStatus, pCurentpage, pItemPerpage, empDeptId);
  }

  public ApplicationCCICache(CacheManager<ApplicationCCI, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterAndType(String pStudentId, int pSemesterId, int pExamType) {
    return getManager().getByStudentIdAndSemesterAndType(pStudentId, pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndType(int pSemesterId, int pExamType) {
    return getManager().getBySemesterAndType(pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getByProgramAndSemesterAndType(int pProgramId, int pSemesterId, int pExamType) {
    return getManager().getByProgramAndSemesterAndType(pProgramId, pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemester(String pStudentId, int pSemesterId) {
    return getManager().getByStudentIdAndSemester(pStudentId, pSemesterId);
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    return getManager().getBySemesterAndExamDate(pSemesterId, pExamDate);
  }

  @Override
  public int deleteByStudentId(String pStudentId) {
    return getManager().deleteByStudentId(pStudentId);
  }

  @Override
  protected CacheManager<ApplicationCCI, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterForSeatPlanView(String pStudentId, Integer pSemesterId) {
    return getManager().getByStudentIdAndSemesterForSeatPlanView(pStudentId, pSemesterId);
  }
}
