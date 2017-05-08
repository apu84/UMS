package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public enum LeaveApprovalStatus {
  ACCEPTED_BY_DEPT_HEAD(1, "ACCEPTED BY DEPARTMENTAL HEAD"),
  REJECTED_BY_DEPT_HEAD(2, "Application Rejected By Head"),
  ACCEPTED_BY_VC(3, "Application Accepted By VC"),
  REJECTED_BY_VC(4, "Application Rejected By VC"),
  ACCEPTED_BY_REGISTRAR(5, "Application Accepted By Registrar"),
  REJECTED_BY_REGISTRAR(6, "Application Rejected By Registrar");

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
