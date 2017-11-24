package org.ums.enums.registrar;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TrainingCategory {
  LOCAL(10, "Local"),
  Foreign(20, "Foreign");

  private static final Map<Integer, TrainingCategory> lookup = new HashMap<>();

  static {
    for(TrainingCategory c : EnumSet.allOf(TrainingCategory.class)) {
      lookup.put(c.getId(), c);
    }
  }

  private String label;
  private int id;

  TrainingCategory(int id, String label) {
    this.id = id;
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }

  public static TrainingCategory get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }
}
