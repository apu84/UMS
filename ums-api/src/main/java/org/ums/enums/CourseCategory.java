package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 08-Jan-16.
 */
public enum CourseCategory {
  MANDATORY(1),
  OPTIONAL(2),
  NONE(0);

  private static final Map<Integer, CourseCategory> lookup
      = new HashMap<>();

  static {
    for (CourseCategory c : EnumSet.allOf(CourseCategory.class))
      lookup.put(c.getValue(), c);
  }


  private int typeCode;

  private CourseCategory(int pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static CourseCategory get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public int getValue() {
    return this.typeCode;
  }
}