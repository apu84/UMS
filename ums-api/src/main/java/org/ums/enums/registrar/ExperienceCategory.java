package org.ums.enums.registrar;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ExperienceCategory {
  JOB(10, "Job"),
  RESEARCH(20, "Research");

  private static final Map<Integer, ExperienceCategory> lookup = new HashMap<>();

  static {
    for(ExperienceCategory c : EnumSet.allOf(ExperienceCategory.class)) {
      lookup.put(c.getId(), c);
    }
  }

  private String label;
  private int id;

  ExperienceCategory(int id, String label) {
    this.id = id;
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }

  public static ExperienceCategory get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }
}
