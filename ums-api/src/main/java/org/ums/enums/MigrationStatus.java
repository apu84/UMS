package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public enum MigrationStatus {
  NOT_MIGRATED(1, "Not Migrated"),
  MIGRATION_ABLE(2, "Can migrate"),
  MIGRATED(3, "Migrated");

  private String label;
  private int id;

  private MigrationStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, MigrationStatus> lookup = new HashMap<>();

  static {
    for(MigrationStatus c : EnumSet.allOf(MigrationStatus.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static MigrationStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

}
