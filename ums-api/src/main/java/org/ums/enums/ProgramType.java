package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 24-Mar-16.
 */
public enum ProgramType {
  UG(11), //
  PG(22);//

  private static final Map<Integer, ProgramType> lookup = new HashMap<>();

  static {
    for(ProgramType programType : EnumSet.allOf(ProgramType.class))
      lookup.put(programType.getValue(), programType);
  }

  private int typeCode;

  private ProgramType(int pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static ProgramType get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public int getValue() {
    return this.typeCode;
  }
}
