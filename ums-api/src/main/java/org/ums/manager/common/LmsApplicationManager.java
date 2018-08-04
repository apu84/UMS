package org.ums.manager.common;

import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.manager.ContentManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface LmsApplicationManager extends ContentManager<LmsApplication, MutableLmsApplication, Long> {
  List<LmsApplication> getLmsApplication(String pEmployeeId, int pYear);

  List<LmsApplication> getPendingLmsApplication(String pEmployeeId);

  int updateApplicationStatus(Long pApplicationid, LeaveApplicationApprovalStatus pLeaveApplicationStatus);

  List<LmsApplication> getApplicationsWithinRange(String pEmployeeId, String startDate, String endDate);

  List<LmsApplication> getWithinDateRange(Date pStartDate, Date pEndDate,
      LeaveApplicationApprovalStatus pLeaveApplicationStatus);
}
