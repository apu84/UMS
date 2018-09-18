package org.ums.enums.routine;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DayType {
  SATURDAY(1, "SATURDAY"),
  SUNDAY(2, "SUNDAY"),
  MONDAY(3, "MONDAY"),
  TUESDAY(4, "TUESDAY"),
  WEDNESDAY(5, "WEDNESDAY"),
  THURSDAY(6, "THURSDAY"),
  FRIDAY(7, "FRIDAY");

  private static final Map<Integer, DayType> Lookup = new HashMap<>();
  private static final Map<String, DayType> lookUpByLabel = new HashMap<>();

  static {
    for(DayType c : EnumSet.allOf(DayType.class)) {
      Lookup.put(c.getValue(), c);
      lookUpByLabel.put(c.getLabel(), c);
    }
  }

  private int typeCode;
  private String label;

  private DayType(int pTypeCode, String pLabel) {
    this.typeCode = pTypeCode;
    this.label = pLabel;
  }

  public static DayType get(final int pTypeCode) {
    return Lookup.get(pTypeCode);
  }

  public static DayType getByLabel(final String pLabel) {
    return lookUpByLabel.get(pLabel);
  }

  public int getValue() {
    return this.typeCode;
  }

  public String getLabel() {
    return this.label;
  }
}
