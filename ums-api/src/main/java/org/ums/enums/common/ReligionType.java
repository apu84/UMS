package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReligionType {
  ISLAM(1, "Islam"),
  BUDDHISM(2, "Buddhism"),
  HINDUISM(3, "Hinduism"),
  JAINISM(4, "Jainism"),
  JUDAISM(5, "Judaism"),
  SIKHISM(6, "Sikhism"),
  CHRISTIAN(7, "Christian"),
  OTHERS(99, "Others");

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
