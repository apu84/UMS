package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 12-Jun-17.
 */
public enum DepartmentType {
  MPE("07", "Department of Mechanical & Production Engineering"),
  TE("06", "Department of Textile Engineering"),
  CE("03", "Department of Civil Engineering"),
  CSE("04", "Department of Computer Science and Engineering"),
  EEE("05", "Department of Electrical and Electronic Engineering"),
  SCHOOL_OF_BUSINESS("02", "School of Business"),
  ARC("01", "Department of Architecture"),
  COE("73", "Office of the Controller of Examinations"),
  RO("72", "Office of the Registrar"),
  TO("71", "Office of the Treasurer"),
  AoSW("76", "Office of the Advisor of Student's Welfare"),
  KFRL("74", "Kazi Fazlur Rahman Library"),
  UE("77", "Office of the University Engineer"),
  PO("75", "Proctor Office"),
  AS("15", "Department of Arts and Sciences"),
  OVC("70", "Office of VC"),
  ALL("9999", "All Departments");

  private String label;
  private String id;

  private DepartmentType(String id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<String, DepartmentType> lookup = new HashMap<>();

  static {
    for(DepartmentType c : EnumSet.allOf(DepartmentType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static DepartmentType get(final String pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public String getId() {
    return this.id;
  }
}
