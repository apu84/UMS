package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 07-May-17.
 */
public enum DurationType {
  DAY(1, "Daily"),
  MONTH(2, "Monthly"),
  YEAR(3, "Yearly");

  private String label;
  private int id;

  private DurationType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, DurationType> lookup = new HashMap<>();

  static {
    for(DurationType c : EnumSet.allOf(DurationType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static DurationType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
