package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum ContributorRole {
  AUTHOR(1, "Author"),
  CO_AUTHOR(2, "Co-Author"),
  EDITOR(2, "Editor");

  private String label;
  private int id;

  private ContributorRole(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, ContributorRole> lookup = new HashMap<>();

  static {
    for(ContributorRole c : EnumSet.allOf(ContributorRole.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ContributorRole get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
