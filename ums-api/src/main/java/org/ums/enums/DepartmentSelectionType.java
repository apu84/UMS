package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 09-Jan-17.
 */
public enum DepartmentSelectionType {
  ABSENT(0, "ABSENT"),
  MERIT_PROGRAM_SELECTED(1, "ONLY MERIT PROGRAM SELECTED"),
  MERIT_WAITING_PROGRAMS_SELECTED(2, "BOTH MERIT AND WAITING PROGRAM SELECTED"),
  WAITING_PROGRAM_SELECTED(3, "ONLY WAITING PROGRAM SELECTED");

  private String label;
  private int id;

  private DepartmentSelectionType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, DepartmentSelectionType> lookup = new HashMap<>();

  static {
    for(DepartmentSelectionType c : EnumSet.allOf(DepartmentSelectionType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static DepartmentSelectionType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
