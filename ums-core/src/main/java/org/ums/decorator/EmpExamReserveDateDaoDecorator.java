package org.ums.decorator;

import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.manager.EmpExamReserveDateManager;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamReserveDateDaoDecorator extends
    ContentDaoDecorator<EmpExamReserveDate, MutableEmpExamReserveDate, Long, EmpExamReserveDateManager> implements
    EmpExamReserveDateManager {
}
