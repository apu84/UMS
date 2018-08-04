package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 17-Feb-17.
 */
public enum JournalFrequency {
  SELECT_FREQUENCY(101101, "Select Frequency"),
  WEEKLY(1, "Weekly"),
  BI_WEEKLY(15, "Bi-Weekly"),
  MONTHLY(30, "Monthly"),
  YEARLY(360, "Yearly");

  private String label;
  private int id;

  private JournalFrequency(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, JournalFrequency> lookup = new HashMap<>();

  static {
    for(JournalFrequency c : EnumSet.allOf(JournalFrequency.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static JournalFrequency get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
