package org.ums.manager;

import org.ums.domain.model.immutable.DepartmentSelectionDeadline;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
public interface DepartmentSelectionDeadlineManager extends
    ContentManager<DepartmentSelectionDeadline, MutableDepartmentSelectionDeadline, Integer> {
  List<DepartmentSelectionDeadline> getDeadline(int pSemesterId, String pUnit, String pQuota);
}
