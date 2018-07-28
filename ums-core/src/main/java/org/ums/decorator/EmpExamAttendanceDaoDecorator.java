package org.ums.decorator;

import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.manager.EmpExamAttendanceManager;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamAttendanceDaoDecorator extends
    ContentDaoDecorator<EmpExamAttendance, MutableEmpExamAttendance, Long, EmpExamAttendanceManager> implements
    EmpExamAttendanceManager {
}
