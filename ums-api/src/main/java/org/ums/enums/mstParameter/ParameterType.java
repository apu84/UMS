package org.ums.enums.mstParameter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 4/24/2018.
 */
public enum ParameterType {
  APPLICATION_FOR_CARRY_CLEARANCE_IMPROVEMENT("10"),
  TES_HEAD_COURSE_ASSIGN_DATE("11"),
  TES_STUDENT_EVALUATION_TIME_PERIOD("12"),
  TES_QUESTION_SET_DATE("13");

  private static final Map<String, ParameterType> lookup = new HashMap<>();

  static {
    for(ParameterType c : EnumSet.allOf(ParameterType.class))
      lookup.put(c.getValue(), c);
  }

  private String typeCode;

  private ParameterType(String pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static ParameterType get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public String getValue() {
    return this.typeCode;
  }
}
