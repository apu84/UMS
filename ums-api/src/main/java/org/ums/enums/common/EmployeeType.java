package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 07-May-17.
 */
public enum EmployeeType {
  TEACHER(1, "TEACHER"),
  OFFICER(2, "OFFICER"),
  STAFF(3, "STAFF"),
  MANAGEMENT(4, "MANAGEMENT");

  private String label;
  private int id;

  private EmployeeType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, EmployeeType> lookup = new HashMap<>();

  static {
    for(EmployeeType c : EnumSet.allOf(EmployeeType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static EmployeeType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
