package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EmploymentType {
  PERMANENT(1, "1"),
  CONTRACTUAL(2, "2"),
  PROVISIONAL(3, "3"),
  PARTTIME(4, "4");

  private String label;
  private int id;

  private EmploymentType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, EmploymentType> lookup = new HashMap<>();

  static {
    for(EmploymentType e : EnumSet.allOf(EmploymentType.class)) {
      lookup.put(e.getId(), e);
    }
  }

  public static EmploymentType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

}
