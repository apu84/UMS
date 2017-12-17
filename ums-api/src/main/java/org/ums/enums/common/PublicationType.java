package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PublicationType {
  CONFERENCE(1, "Conference Proceedings"),
  JOURNAL(2, "Journal Article"),
  PROCEEDINGS(3, "Book"),
  OTHERS(4, "Others");

  private Integer id;
  private String name;

  private PublicationType(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  private static final Map<Integer, PublicationType> lookup = new HashMap<>();

  static {
    for(PublicationType c : EnumSet.allOf(PublicationType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static PublicationType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.name;
  }

  public Integer getId() {
    return this.id;
  }
}
