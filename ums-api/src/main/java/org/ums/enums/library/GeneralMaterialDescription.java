package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GeneralMaterialDescription {
  GMD(1, "GMD"),
  SMD(2, "SMD");

  private String label;
  private int id;

  private GeneralMaterialDescription(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, GeneralMaterialDescription> lookup = new HashMap<>();

  static {
    for(GeneralMaterialDescription c : EnumSet.allOf(GeneralMaterialDescription.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static GeneralMaterialDescription get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
