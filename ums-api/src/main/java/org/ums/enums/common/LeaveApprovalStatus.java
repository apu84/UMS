package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public enum LeaveApprovalStatus {
  WAITING_FOR_HEAD_APPROVAL(1, "Waiting For Head's Approval"),
  ACCEPTED_BY_DEPT_HEAD(2, "Application Approved By Head"),
  REJECTED_BY_DEPT_HEAD(3, "Application Rejected By Head"),
  WAITING_FOR_VC_APPROVAL(4, "Waiting For VC's Approval"),
  ACCEPTED_BY_VC(5, "Application Accepted By VC"),
  REJECTED_BY_VC(6, "Application Rejected By VC"),
  WAITING_FOR_REGISTRAR_APPROVAL(7, "Waiting For Registrar's Approval"),
  ACCEPTED_BY_REGISTRAR(8, "Application Accepted By Registrar"),
  REJECTED_BY_REGISTRAR(9, "Application Rejected By Registrar");

  private String label;
  private int id;

  private LeaveApprovalStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, LeaveApprovalStatus> lookup = new HashMap<>();

  static {
    for(LeaveApprovalStatus c : EnumSet.allOf(LeaveApprovalStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static LeaveApprovalStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
