package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReligionType {
  Islam(1, "Islam"),
  Buddhism(2, "Buddhism"),
  Hinduism(3, "Hinduism"),
  Jainism(4, "Jainism"),
  Judaism(5, "Judaism"),
  Sikhism(6, "Sikhism"),
  Others(99, "Others");

  private Integer id;
  private String label;

  private ReligionType(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, ReligionType> lookup = new HashMap<>();

  static {
    for(ReligionType c : EnumSet.allOf(ReligionType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ReligionType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public Integer getId() {
    return this.id;
  }
}
