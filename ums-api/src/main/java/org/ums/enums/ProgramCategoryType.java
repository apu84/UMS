package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 11-Jun-18.
 */
public enum ProgramCategoryType {

  ENGINEERING(1),
  SCHOOL(2);
  private static final Map<Integer, ClassRoomType> lookup = new HashMap<>();

  static {
    for(ClassRoomType c : EnumSet.allOf(ClassRoomType.class))
      lookup.put(c.getValue(), c);
  }

  private int typeCode;

  private ProgramCategoryType(int pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static ClassRoomType get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public int getValue() {
    return this.typeCode;
  }
}
