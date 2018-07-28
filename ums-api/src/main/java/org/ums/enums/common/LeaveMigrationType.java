package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public enum LeaveMigrationType {


  DATA_MIGRATION(1, "DATA MIGRATION"),
  SYSTEM_GENERATED(2, "SYSTEM GENERATED"),
  LEAVE_APPLICATION(3, "LEAVE APPLICATION");

  private String label;
  private int id;

  private LeaveMigrationType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, LeaveMigrationType> lookup = new HashMap<>();

  static {
    for(LeaveMigrationType c : EnumSet.allOf(LeaveMigrationType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static LeaveMigrationType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
  
}
