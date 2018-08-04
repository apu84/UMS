package org.ums.enums.routine;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DayType {
  SATURDAY(1),
  SUNDAY(2),
  MONDAY(3),
  TUESDAY(4),
  WEDNESDAY(5),
  THURSDAY(6),
  FRIDAY(7);

  private static final Map<Integer, DayType> Lookup = new HashMap<>();

  static {
    for(DayType c : EnumSet.allOf(DayType.class))
      Lookup.put(c.getValue(), c);
  }

  private int typeCode;

  private DayType(int pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static DayType get(final int pTypeCode) {
    return Lookup.get(pTypeCode);
  }

  public int getValue() {
    return this.typeCode;
  }
}
