package org.ums.manager;

import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;

import java.util.List;

/**
 * Created by My Pc on 7/11/2016.
 */
public interface ApplicationCCIManager extends ContentManager<ApplicationCCI,MutableApplicationCCI,Integer>  {
  List<ApplicationCCI> getByStudentIdAndSemesterAndType(final String pStudentId, final int pSemesterId, final int pExamType);
  List<ApplicationCCI> getBySemesterAndType(final int pSemesterId, final int pExamType);
  List<ApplicationCCI> getByProgramAndSemesterAndType(final int pProgramId,final int pSemesterId,final int pExamType);
  List<ApplicationCCI> getByStudentIdAndSemester(final String pStudentId, final int pSemesterId);

  List<ApplicationCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate) throws Exception;
  int deleteByStudentId(final String pStudentId);

  List<ApplicationCCI> getByStudentIdAndSemesterForSeatPlanView(String pStudentId,Integer pSemesterId);
}
