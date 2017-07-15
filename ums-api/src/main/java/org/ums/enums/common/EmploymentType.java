package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EmploymentType {
  REGULAR(1, "Regular"),
  CONTRACT(2, "Contract"),
  ADD_HOC(3, "Add-Hoc"),
  PART_TIME(4, "Part-Time(PT)"),
  PART_TIME_FULL_LOAD(5, "Part-Time Full Load(PTFL)");

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
