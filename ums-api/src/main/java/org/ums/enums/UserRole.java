package org.ums.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 22-Sep-16.
 */
public enum UserRole {

  HEAD(1, "Head"),
  COE(2, "CoE"),
  VC(3, "VC");

  private String label;
  private int id;

  private UserRole(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, UserRole> lookup = new HashMap<>();

  static {
    for(UserRole c : EnumSet.allOf(UserRole.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static UserRole get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

  public static void main(String[] args) {
    System.out.println(UserRole.COE.getId() + UserRole.COE.getLabel() + UserRole.get(1).getLabel());
  }

}
