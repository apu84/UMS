package org.ums.decorator;

import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.manager.EmpExamReserveDateManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamReserveDateDaoDecorator extends
    ContentDaoDecorator<EmpExamReserveDate, MutableEmpExamReserveDate, Long, EmpExamReserveDateManager> implements
    EmpExamReserveDateManager {
  @Override
  public List<EmpExamReserveDate> getBySemesterAndExamType(Integer pSemesterId, Integer pExamType) {
    return getBySemesterAndExamType(pSemesterId, pExamType);
  }
}
