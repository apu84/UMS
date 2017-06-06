package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public enum LeaveApplicationApprovalStatus {
  WAITING_FOR_HEAD_APPROVAL(1, "Waiting For Head's Approval"),
  WAITING_FOR_REGISTRARS_APPROVAL(2, "Waiting For Registrar's Approval"),
  REJECTED_BY_DEPT_HEAD(3, "Application Rejected By Head"),
  WAITING_FOR_VC_APPROVAL(4, "Waiting For VC's Approval"),
  REJECTED_BY_REGISTRAR(5, "Application Rejected By Registrar"),
  REJECTED_BY_VC(6, "Application Rejected By VC"),
  APPLICATION_APPROVED(7, "Application Approved"),
  ALL(8, "All Status");

  private String label;
  private int id;

  private LeaveApplicationApprovalStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, LeaveApplicationApprovalStatus> lookup = new HashMap<>();

  static {
    for(LeaveApplicationApprovalStatus c : EnumSet.allOf(LeaveApplicationApprovalStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static LeaveApplicationApprovalStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
