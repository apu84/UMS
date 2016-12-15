package org.ums.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public enum MigrationStatus {
  NOT_MIGRATED(0, "Not Migrated"),
  MIGRATED(1, "Migrated");

  private String label;
  private int id;

  private static final Map<Integer, MigrationStatus> lookup = new HashMap<>();

  private MigrationStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  public static MigrationStatus get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }

}
