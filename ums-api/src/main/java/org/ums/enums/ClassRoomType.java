package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ClassRoomType {
  THEORY(1),
  SESSIONAL(2),
  NONE(0);

  private static final Map<Integer, ClassRoomType> lookup
      = new HashMap<>();

  static {
    for (ClassRoomType c : EnumSet.allOf(ClassRoomType.class))
      lookup.put(c.getValue(), c);
  }


  private int typeCode;

  private ClassRoomType(int pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static ClassRoomType get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public int getValue() {
    return this.typeCode;
  }
}