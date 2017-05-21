package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 21-May-17.
 */
public enum RoleType {
  REGISTRAR(81, "Registrar"),
  STUDENT(11, "Student"),
  S_ADMIN(999, "Sadmin"),
  COE(71, "COE"),
  IUMS_SECRATARY(31, "Iums-Secratary"),
  TEACHER(21, "Teacher"),
  DEPT_OFFICE(41, "Dept Office"),
  DP_REGISTRAR(82, "Deputy Registrar"),
  DEPT_HEAD(22, "Department Head"),
  VC(99, "Vice Chancellor"),
  DP_COE(72, "Deputy Comptroller"),
  UG_ADMISSION_CHAIRMAN(72, "Undergraduate Admission Chairman"),
  LIBRARIAN(3000, "Librarian");

  private String label;
  private int id;

  private RoleType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, RoleType> lookup = new HashMap<>();

  static {
    for(RoleType c : EnumSet.allOf(RoleType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static RoleType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
