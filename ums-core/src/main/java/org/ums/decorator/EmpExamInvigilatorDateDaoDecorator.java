package org.ums.decorator;

import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.manager.EmpExamInvigilatorDateManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamInvigilatorDateDaoDecorator extends
    ContentDaoDecorator<EmpExamInvigilatorDate, MutableEmpExamInvigilatorDate, Long, EmpExamInvigilatorDateManager>
    implements EmpExamInvigilatorDateManager {
  @Override
  public List<EmpExamInvigilatorDate> getBySemesterAndExamType(Integer pSemesterId, Integer pExamType) {
    return getManager().getBySemesterAndExamType(pSemesterId, pExamType);
  }
}
