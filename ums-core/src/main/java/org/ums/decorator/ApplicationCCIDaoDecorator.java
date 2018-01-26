package org.ums.decorator;

import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.manager.ApplicationCCIManager;

import java.util.List;

/**
 * Created by My Pc on 7/11/2016.
 */
public class ApplicationCCIDaoDecorator extends
    ContentDaoDecorator<ApplicationCCI, MutableApplicationCCI, Long, ApplicationCCIManager> implements
    ApplicationCCIManager {
  @Override
  public List<ApplicationCCI> getApplicationCarryForHeadsApprovalAndAppiled() {
    return getManager().getApplicationCarryForHeadsApprovalAndAppiled();
  }

  // carryHeadsApproval
  @Override
  public List<ApplicationCCI> getApplicationCarryForHeadsApproval() {
    return getManager().getApplicationCarryForHeadsApproval();
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterAndType(String pStudentId, int pSemesterId, int pExamType) {
    return getManager().getByStudentIdAndSemesterAndType(pStudentId, pSemesterId, pExamType);
  }

  @Override
  public int deleteByStudentId(String pStudentId) {
    return getManager().deleteByStudentId(pStudentId);
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
  public List<ApplicationCCI> getTotalCarry(String pStudentId, Integer pSemesterId) {
    return getManager().getTotalCarry(pStudentId, pSemesterId);
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    return getManager().getBySemesterAndExamDate(pSemesterId, pExamDate);
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterForSeatPlanView(String pStudentId, Integer pSemesterId) {
    return getManager().getByStudentIdAndSemesterForSeatPlanView(pStudentId, pSemesterId);
  }
}
