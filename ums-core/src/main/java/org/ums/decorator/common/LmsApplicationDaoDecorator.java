package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.manager.common.LmsApplicationManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class LmsApplicationDaoDecorator extends
    ContentDaoDecorator<LmsApplication, MutableLmsApplication, Long, LmsApplicationManager> implements
    LmsApplicationManager {

  @Override
  public List<LmsApplication> getLmsApplication(String pEmployeeId, int pYear) {
    return getManager().getLmsApplication(pEmployeeId, pYear);
  }

  @Override
  public List<LmsApplication> getPendingLmsApplication(String pEmployeeId) {
    return getManager().getPendingLmsApplication(pEmployeeId);
  }

  @Override
  public int updateApplicationStatus(Long pApplicationid, LeaveApplicationApprovalStatus pLeaveApplicationStatus) {
    return updateApplicationStatus(pApplicationid, pLeaveApplicationStatus);
  }
}
