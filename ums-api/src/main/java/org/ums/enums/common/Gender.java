package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum Gender {

  MALE(1, "Male"),
  FEMALE(2, "Female'");

  private String label;
  private int id;

  private Gender(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, Gender> lookup = new HashMap<>();

  static {
    for(Gender c : EnumSet.allOf(Gender.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static Gender get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
