package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum NationalityType {
  Bangladeshi(1, "Bangladeshi"),
  Others(99, "Others");

  private String label;
  private Integer id;

  private NationalityType(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, NationalityType> lookup = new HashMap<>();

  static {
    for(NationalityType c : EnumSet.allOf(NationalityType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static NationalityType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public Integer getId() {
    return this.id;
  }
}
