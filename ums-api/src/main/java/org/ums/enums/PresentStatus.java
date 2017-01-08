package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 07-Jan-17.
 */
public enum PresentStatus {
  ABSENT(0, "ABSENT"),
  PRESENT(1, "PRESENT");

  private String label;
  private int id;

  private PresentStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, PresentStatus> lookup = new HashMap<>();

  static {
    for(PresentStatus c : EnumSet.allOf(PresentStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static PresentStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
