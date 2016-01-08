package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 08-Jan-16.
 */
public enum CourseType {
  THEORY(1),
  SESSIONAL(2),
  THESIS_PROJECT(3),
  NONE(0);

  private static final Map<Integer, CourseType> lookup
      = new HashMap<>();

  static {
    for (CourseType c : EnumSet.allOf(CourseType.class)) {
      lookup.put(c.getValue(), c);
    }
  }


  private int typeCode;

  private CourseType(int pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static CourseType get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public int getValue() {
    return this.typeCode;
  }
}