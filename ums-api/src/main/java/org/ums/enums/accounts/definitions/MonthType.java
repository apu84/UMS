package org.ums.enums.accounts.definitions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public enum MonthType {
  JANUARY(1),
  FEBRUARY(2),
  MARCH(3),
  APRIL(4),
  MAY(5),
  JUNE(6),
  JULY(7),
  AUGUST(8),
  SEPTEMBER(9),
  OCTOBER(10),
  NOVEMBER(11),
  DECEMBER(12);

  private static final Map<Integer, MonthType> Lookup = new HashMap<>();

  static {
    for(MonthType m : EnumSet.allOf(MonthType.class))
      Lookup.put(m.getValue(), m);
  }

  private Integer typeValue;

  MonthType(Integer pTypeValue) {
    typeValue = pTypeValue;
  }

  public static MonthType get(final Integer pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public Integer getValue() {
    return this.typeValue;
  }
}
