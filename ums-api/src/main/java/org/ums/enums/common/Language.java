package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum Language {

  BANGLA(1, "Bangla"),
  ENGLISH(2, "English'");

  private String label;
  private int id;

  private Language(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, Language> lookup = new HashMap<>();

  static {
    for(Language c : EnumSet.allOf(Language.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static Language get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
