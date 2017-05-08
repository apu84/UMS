package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 07-May-17.
 */
public enum SalaryType {
  FULL_PAY(1, "Full Pay"),
  HALF_PAY(2, "Half Pay"),
  WITHOUT_PAY(3, "Without Pay");

  private String label;
  private int id;

  private SalaryType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, SalaryType> lookup = new HashMap<>();

  static {
    for(SalaryType c : EnumSet.allOf(SalaryType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static SalaryType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
