package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by My Pc on 7/11/2016.
 */
public enum ApplicationType {
  REGULAR(1),
  CLEARANCE(2),
  CARRY(3),
  SPECIAL_CARRY(4),
  IMPROVEMENT(5);

  private static final Map<Integer, ApplicationType> Lookup = new HashMap<>();

  static {
    for(ApplicationType c : EnumSet.allOf(ApplicationType.class))
      Lookup.put(c.getValue(), c);
  }

  private int typeCode;

  private ApplicationType(int pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static ApplicationType get(final int pTypeCode) {
    return Lookup.get(pTypeCode);
  }

  public int getValue() {
    return this.typeCode;
  }

}
