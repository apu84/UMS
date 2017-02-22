package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum RecordStatus {

  ENTRY_MODE(0, "Entry Mode"),
  AVAILABLE(2, "'Available'");

  private String label;
  private int id;

  private RecordStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, RecordStatus> lookup = new HashMap<>();

  static {
    for(RecordStatus c : EnumSet.allOf(RecordStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static RecordStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
