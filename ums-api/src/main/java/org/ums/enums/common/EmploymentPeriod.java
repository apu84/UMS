package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EmploymentPeriod {
  CONTRACTUAL(1, "Contractual"),
  PROBATION(2, "Probation"),
  PERMANENT(3, "Permanent"),
  CONTRACT(4, "Contract");

  private int id;
  private String label;

  private EmploymentPeriod(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, EmploymentPeriod> lookup = new HashMap<>();

  static {
    for(EmploymentPeriod e : EnumSet.allOf(EmploymentPeriod.class)) {
      lookup.put(e.getId(), e);
    }
  }

  public static EmploymentPeriod get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
