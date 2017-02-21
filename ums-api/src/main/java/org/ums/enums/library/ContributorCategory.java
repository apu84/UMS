package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum ContributorCategory {
  TEACHER(1, "TEACHER"),
  STUDENT(2, "STUDENT"),
  OTHERS(2, "OTHERS");

  private String label;
  private int id;

  private ContributorCategory(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, ContributorCategory> lookup = new HashMap<>();

  static {
    for(ContributorCategory c : EnumSet.allOf(ContributorCategory.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ContributorCategory get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
