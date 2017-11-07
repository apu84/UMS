package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AcademicDegreeType {
  SSC(1, "SSC/O-Level"),
  HSC(2, "HSC/A-Level"),
  BACHELOR(3, "Bachelor"),
  MASTERS(4, "Masters"),
  PhD(5, "PhD");

  private Integer id;
  private String name;

  private AcademicDegreeType(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  private static final Map<Integer, AcademicDegreeType> lookup = new HashMap<>();

  static {
    for(AcademicDegreeType c : EnumSet.allOf(AcademicDegreeType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static AcademicDegreeType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.name;
  }

  public Integer getId() {
    return this.id;
  }
}
