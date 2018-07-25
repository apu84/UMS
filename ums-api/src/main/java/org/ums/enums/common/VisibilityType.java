package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 25-Jul-18.
 */
public enum VisibilityType {
  VISIBLE("Y", "VISIBLE"),
  INVISIBLE("N", "INVISIBLE");

  private String label;
  private String id;

  private VisibilityType(String id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<String, VisibilityType> lookup = new HashMap<>();

  static {
    for(VisibilityType c : EnumSet.allOf(VisibilityType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static VisibilityType get(final String pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public String getId() {
    return this.id;
  }
}
