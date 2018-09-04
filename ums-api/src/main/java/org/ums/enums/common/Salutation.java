package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Salutation {
  Mr(1, "Mr."),
  Ms(2, "Ms."),
  Dr(3, "Dr."),
  Prof_Dr(4, "Prof Dr.");

  private String label;
  private Integer id;

  private Salutation(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, Salutation> lookup = new HashMap<>();

  static {
    for(Salutation c : EnumSet.allOf(Salutation.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static Salutation get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public Integer getId() {
    return this.id;
  }
}
