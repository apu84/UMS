package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DegreeLevel {
  PSC(10, "PSC/5 Pass"),
  JSC(20, "JSC/JDC/8 Pass"),
  SSC(110, "Secondary"),
  HSC(120, "Higher Secondary"),
  DIPLOMA(1010, "Diploma"),
  BACHELOR(1110, "Bachelor/Honors"),
  MASTERS(10010, "Masters"),
  PhD(100010, "PhD (Doctor of Philosophy)");

  private Integer id;
  private String name;

  private DegreeLevel(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  private static final Map<Integer, DegreeLevel> lookup = new HashMap<>();

  static {
    for(DegreeLevel c : EnumSet.allOf(DegreeLevel.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static DegreeLevel get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.name;
  }

  public Integer getId() {
    return this.id;
  }
}
