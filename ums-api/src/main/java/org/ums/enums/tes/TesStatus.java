package org.ums.enums.tes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 4/24/2018.
 */
public enum TesStatus {
  ALL_DEPARTMENT("08"),
  MAXIMUM_SCORE_HOLDER("09"),
  MINIMUM_SCORE_HOLDER("10"),
  FACULTY_OF_ENGINEERING("11"),
  FACULTY_OF_BUSINESS_AND_SOCIAL_SCIENCE("12"),
  FACULTY_OF_ARCHITECTURE("13");

  private static final Map<String, TesStatus> lookup = new HashMap<>();

  static {
    for(TesStatus c : EnumSet.allOf(TesStatus.class))
      lookup.put(c.getValue(), c);
  }

  private String typeCode;

  private TesStatus(String pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static TesStatus get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public String getValue() {
    return this.typeCode;
  }
}
