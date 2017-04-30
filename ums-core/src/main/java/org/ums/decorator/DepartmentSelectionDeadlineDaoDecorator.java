package org.ums.decorator;

import org.ums.domain.model.immutable.DepartmentSelectionDeadline;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;
import org.ums.manager.DepartmentSelectionDeadlineManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
public class DepartmentSelectionDeadlineDaoDecorator
    extends
    ContentDaoDecorator<DepartmentSelectionDeadline, MutableDepartmentSelectionDeadline, Integer, DepartmentSelectionDeadlineManager>
    implements DepartmentSelectionDeadlineManager {
  @Override
  public List<DepartmentSelectionDeadline> getDeadline(int pSemesterId, String pUnit, String pQuota) {
    return getManager().getDeadline(pSemesterId, pUnit, pQuota);
  }
}
