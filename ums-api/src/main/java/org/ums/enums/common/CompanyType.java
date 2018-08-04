package org.ums.enums.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 23-Jun-18.
 */
public enum CompanyType {
  AUST_TECHNICAL("01"),
  AUST_NON_TECHNICAL("02");

  private static final Map<String, CompanyType> Lookup = new HashMap<>();

  static {
    for(CompanyType c : EnumSet.allOf(CompanyType.class))
      Lookup.put(c.getValue(), c);
  }

  private String typeValue;

  CompanyType(String pTypeValue) {
    typeValue = pTypeValue;
  }

  @JsonValue
  public static CompanyType get(final String pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  public String getValue() {
    return this.typeValue;
  }
}
