package org.ums.enums.accounts.definitions.voucher.number.control;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 07-Jan-18.
 */
public enum ResetBasis {

  MONTHLY("M", "MONTHLY"),
  DAILY("D", "DAILY"),
  YEARLY("Y", "YEARLY"),
  WEEKLY("W", "WEEKLY"),
  CARRY_FORWARD("C", "CARRY_FORWARD");

  private String label;
  private String id;

  private ResetBasis(String id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<String, ResetBasis> lookup = new HashMap<>();

  static {
    for(ResetBasis c : EnumSet.allOf(ResetBasis.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static ResetBasis get(final String pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  @JsonValue
  public String getId() {
    return this.id;
  }
}
