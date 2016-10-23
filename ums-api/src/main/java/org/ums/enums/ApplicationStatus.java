package org.ums.enums;

/**
 * Created by My Pc on 3/22/2016.
 */
public enum ApplicationStatus {
  WRITTEN(0, "Saved"), SUBMITTED(1, "Submitted"), WAITING_HEADS_APPROVAL(2,
      "Waiting for head's approval"), HEADS_ACCEPTED(3, "Allowed by the head"), HEADS_REJECTED(4,
      "Rejected by the head"), AAO_ACCEPTED(5, "Allowed by the AAO"), AAO_REJECTED(6,
      "Rejected by the AAO");

  private String label;
  private int id;

  private ApplicationStatus(int id, String label) {
    this.id = id;
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public int getId() {
    return id;
  }
}
