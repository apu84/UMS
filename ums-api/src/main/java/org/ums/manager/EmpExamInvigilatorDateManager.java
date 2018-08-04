package org.ums.manager;

import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public interface EmpExamInvigilatorDateManager extends
    ContentManager<EmpExamInvigilatorDate, MutableEmpExamInvigilatorDate, Long> {
  List<EmpExamInvigilatorDate> getBySemesterAndExamType(final Integer pSemesterId, final Integer pExamType);

}
