package org.ums.enums;

import org.apache.commons.collections.map.HashedMap;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Morshed on 3/22/2016.
 */

public enum ApplicationStatus {
  WRITTEN(0, "Saved"),
  SUBMITTED(1, "Submitted"),
  WAITING_HEADS_APPROVAL(2, "Waiting for head's approval"),
  HEADS_ACCEPTED(3, "Allowed by the head"),
  HEADS_REJECTED(4, "Rejected by the head"),
  AAO_ACCEPTED(5, "Allowed by the AAO"),
  AAO_REJECTED(6, "Rejected by the AAO"),
  WAITING_FOR_PAYMENT(7, "Waiting for payment"),
  APPROVED(8, "Approved"),
  REJECTED(9, "Rejected"),
  DEFAULT_LOAD_STATUS(10, "False"),
  TEST_ENUM(19, "HELLO WOLRD");

  private String label;
  private int id;

  private static final Map<Integer, ApplicationStatus> LookUp = new HashMap<>();

  static {
    for(ApplicationStatus a : EnumSet.allOf(ApplicationStatus.class)) {
      LookUp.put(a.getId(), a);
    }
  }

  private ApplicationStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  public static ApplicationStatus get(final int pId) {
    return LookUp.get(pId);
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }
}
