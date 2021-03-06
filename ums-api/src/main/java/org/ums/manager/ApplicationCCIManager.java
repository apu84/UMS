package org.ums.manager;

import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.fee.payment.MutableStudentPayment;

import java.util.List;

/**
 * Created by My Pc on 7/11/2016.
 */
public interface ApplicationCCIManager extends ContentManager<ApplicationCCI, MutableApplicationCCI, Long> {

  List<ApplicationCCI> getByStudentIdAndSemesterAndType(final String pStudentId, final int pSemesterId,
      final int pExamType);

  List<ApplicationCCI> getBySemesterAndType(final int pSemesterId, final int pExamType);

  ApplicationCCI getByTransactionId(final String ptransactionId);

  List<ApplicationCCI> getByProgramAndSemesterAndType(final int pProgramId, final int pSemesterId, final int pExamType);

  List<ApplicationCCI> getByStudentIdAndSemester(final String pStudentId, final int pSemesterId);

  List<ApplicationCCI> getApplicationCCIForImprovementLimit(final String pStudentId);

  List<ApplicationCCI> getApprovedImprovemntInfo(final String pStudentId);

  int updatebank(MutableStudentPayment MutableStudentPayment);

  String getApplicationCCIForCarryLastfdate(final Integer pSemesterId);

  String getStartdate(final Integer pSemesterId);

  Integer getAllReords(final String pApprovalStatus, final String empDeptId);

  List<ApplicationCCI> getApplicationCarryForHeadsApproval(final String pApprovalStatus, final Integer pCurentpage,
      final Integer pItemPerpage, final String empDeptId);// getByStudentId

  List<ApplicationCCI> getByStudentId(final String pApprovalStatus, final String pStudentId, final Integer pSemesterId,
      final String empDeptId);

  List<ApplicationCCI> getApplicationCarryForHeadsApprovalAndAppiled(final String pStudentId, final Integer pSemesterId);

  List<ApplicationCCI> getTotalCarry(final String pStudentId, final Integer pSemesterId);

  List<ApplicationCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate);

  int deleteByStudentId(final String pStudentId);

  List<ApplicationCCI> getByStudentIdAndSemesterForSeatPlanView(String pStudentId, Integer pSemesterId);
}
