package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MaritalStatusType {
  Single(1, "Single"),
  Married(2, "Married"),
  Divorced(3, "Divorced"),
  Widowed(4, "Widowed");

  private String label;
  private Integer id;

  private MaritalStatusType(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, MaritalStatusType> lookup = new HashMap<>();

  static {
    for(MaritalStatusType c : EnumSet.allOf(MaritalStatusType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static MaritalStatusType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public Integer getId() {
    return this.id;
  }
}
