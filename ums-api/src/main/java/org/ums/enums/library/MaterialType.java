package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum MaterialType {

  BOOKS(1, "Book"),
  JOURNALS(2, "Journal"),
  THESIS_PROJECT(3, "Thesis/Project");

  private String label;
  private int id;

  private MaterialType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, MaterialType> lookup = new HashMap<>();

  static {
    for(MaterialType c : EnumSet.allOf(MaterialType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static MaterialType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
