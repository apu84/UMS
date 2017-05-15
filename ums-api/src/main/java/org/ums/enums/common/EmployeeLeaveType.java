package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 03-May-17.
 */
public enum EmployeeLeaveType {
  COMMON_LEAVE(1, "Leave for all employees"),
  TEACHERS_LEAVE(2, "Leave only applicable for teachers"),
  FEMALE_LEAVE(3, "Leave only applicable for female employees");

  private String label;
  private int id;

  private EmployeeLeaveType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, EmployeeLeaveType> lookup = new HashMap<>();

  static {
    for (EmployeeLeaveType c : EnumSet.allOf(EmployeeLeaveType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static EmployeeLeaveType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
