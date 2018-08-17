package org.ums.manager;

import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public interface EmpExamReserveDateManager extends ContentManager<EmpExamReserveDate, MutableEmpExamReserveDate, Long> {
  List<EmpExamReserveDate> getBySemesterAndExamType(final Integer pSemesterId, final Integer pExamType);
}
