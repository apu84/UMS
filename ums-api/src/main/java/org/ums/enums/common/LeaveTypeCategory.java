package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 24-Jul-18.
 */
public enum LeaveTypeCategory {
  PRIMARY_LEAVE(1, "PRIMARY LEAVE"),
  SECONDARY_LEAVE(2, "SECONDARY LEAVE"),
  EXTRA_ORDINARY_LEAVE(3, "EXTRA ORDINARY LEAVE");

  private String label;
  private int id;

  private LeaveTypeCategory(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, LeaveTypeCategory> lookup = new HashMap<>();

  static {
    for(LeaveTypeCategory c : EnumSet.allOf(LeaveTypeCategory.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static LeaveTypeCategory get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
