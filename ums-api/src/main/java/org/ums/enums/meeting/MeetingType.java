package org.ums.enums.meeting;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MeetingType {
  BOT(10, "Board Of Trustees Meeting"),
  SYNDICATE(20, "Syndicate Meeting"),
  FINANCE(30, "Finance Committee Meeting"),
  ACADEMIC(40, "Academic Council Meeting"),
  HEADS(50, "Heads Meeting Meeting");

  private int id;
  private String name;

  private MeetingType(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  private static final Map<Integer, MeetingType> lookup = new HashMap<>();

  static {
    for(MeetingType c : EnumSet.allOf(MeetingType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static MeetingType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.name;
  }

  public Integer getId() {
    return this.id;
  }
}
