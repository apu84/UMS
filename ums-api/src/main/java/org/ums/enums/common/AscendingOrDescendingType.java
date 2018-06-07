package org.ums.enums.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AscendingOrDescendingType {

  ASCENDING(1),
  DESCENDING(2);

  private static final Map<Integer, AscendingOrDescendingType> Lookup = new HashMap<>();

  static {
    for(AscendingOrDescendingType c : EnumSet.allOf(AscendingOrDescendingType.class))
      Lookup.put(c.getValue(), c);
  }

  private Integer typeValue;

  AscendingOrDescendingType(Integer pTypeValue) {
    typeValue = pTypeValue;
  }

  public static AscendingOrDescendingType get(final Integer pTypeValue) {
    return Lookup.get(pTypeValue);
  }

  @JsonValue
  public Integer getValue() {
    return this.typeValue;
  }
}
