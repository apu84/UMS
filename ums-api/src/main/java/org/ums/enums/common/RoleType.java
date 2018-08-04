package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 21-May-17.
 */
public enum RoleType {
  REGISTRAR(7201, "Registrar"),
  STUDENT(1011, "Student"),
  S_ADMIN(9999, "Sadmin"),
  COE(7301, "COE"),
  IUMS_SECRETARY(9011, "Iums-Secretary"),
  TEACHER(1021, "Teacher"),
  ASSISTANT_ADMINISTRATIVE_OFFICER(1041, "Assistant Administrative Officer"),
  ASSISTANT_ADMINISTRATIVE_OFFICER_COE(7303, "Assistant Administrative Officer COE"),
  ASSISTANT_ADMINISTRATIVE_OFFICER_REG(7204, "Assistant Administrative Officer REG"),
  ASSISTANT_ADMINISTRATIVE_OFFICER_LIB(7403, "Assistant Administrative Officer Library"),
  ASSISTANT_ADMINISTRATIVE_OFFICER_SW(7602, "Assistant Administrative Officer Students Welfare"),
  DP_REGISTRAR(1022, "Deputy Registrar"),
  DEPT_HEAD(1022, "Department Head"),
  VC(7001, "Vice Chancellor"),
  DP_COE(7302, "Assistant Controller of Examinations"),
  UG_ADMISSION_CHAIRMAN(9961, "Undergraduate Admission Chairman"),
  DEAN(1023, "Dean"),
  PROCTOR(7401, "Proctor"),
  TREASURER(7101, "Treasurer"),
  UNIVERSITY_ENGINEER(7801, "University Engineer"),
  LIBRARIAN(7701, "Librarian");

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
