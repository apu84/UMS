package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 15-May-17.
 */
public enum LeaveApplicationStatus {
  SAVED(1, "Saved"),
  ACCEPTED(3, "Accepted"),
  PENDING(2, "Pending"),
  REJECTED(4, "Rejected"),
  APPLIED(5, "Applied");

  private String label;
  private int id;

  private LeaveApplicationStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, LeaveApplicationStatus> lookup = new HashMap<>();

  static {
    for(LeaveApplicationStatus c : EnumSet.allOf(LeaveApplicationStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static LeaveApplicationStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
