package org.ums.enums.accounts.definitions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public enum MonthType {
  JANUARY(1, "January"),
  FEBRUARY(2, "February"),
  MARCH(3, "March"),
  APRIL(4, "April"),
  MAY(5, "May"),
  JUNE(6, "June"),
  JULY(7, "July"),
  AUGUST(8, "August"),
  SEPTEMBER(9, "September"),
  OCTOBER(10, "October"),
  NOVEMBER(11, "November"),
  DECEMBER(12, "December");
  private String label;
  private int id;

  private MonthType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, MonthType> lookup = new HashMap<>();

  static {
    for(MonthType c : EnumSet.allOf(MonthType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static MonthType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
