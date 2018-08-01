package org.ums.manager;

import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public interface EmpExamAttendanceManager extends ContentManager<EmpExamAttendance, MutableEmpExamAttendance, Long> {
  List<EmpExamAttendance> getInfoBySemesterAndExamType(final Integer pSemesterId, final Integer pExamType);

  List<EmpExamAttendance> getInfoByInvigilatorDate(final Integer pSemesterId, final Integer pExamType,
      final String pExamDate);

  List<EmpExamAttendance> getInfoByReserveDate(final Integer pSemesterId, final Integer pExamType,
      final String pExamDate);
}
