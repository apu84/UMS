package org.ums.manager;

import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.SubGroupCCI;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.domain.model.mutable.MutableSubGroupCCI;

import java.util.List;

/**
 * Created by My Pc on 7/23/2016.
 */
public interface SubGroupCCIManager extends ContentManager<SubGroupCCI, MutableSubGroupCCI, Integer> {
  List<SubGroupCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate);

  Integer checkOccuranceBySemesterAndExamDate(Integer pSemesterId, String pExamDate);

  Integer deleteBySemesterAndExamDate(Integer pSemesterId, String pExamDate);

  Integer checkSubGroupNumber(Integer pSemesterId, String pExamDate);

  Integer checkForHalfFinishedSubGroup(Integer pSemesterId, String pExamDate);
}
