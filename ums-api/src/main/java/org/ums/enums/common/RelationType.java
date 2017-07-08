package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RelationType {
  Spouse(1, "Spouse"),
  Aunt(2, "Aunt"),
  Brother(3, "Brother"),
  BrotherInLaw(4, "Brother-in-law"),
  Colleague(5, "Colleague"),
  Cousin(6, "Cousin"),
  Daughter(7, "Daughter"),
  DaughterInLaw(8, "Daughter-in-law"),
  Employee(9, "Employee"),
  Father(10, "Father"),
  FatherInLaw(11, "Father-in-law"),
  Friend(12, "Friend"),
  GrandFather(13, "Grand Father"),
  GrandMother(14, "Grand Mother"),
  GrandSon(15, "Grand Son"),
  Mother(16, "Mother"),
  MotherInLaw(17, "Mother-in-law"),
  Nephew(18, "Nephew"),
  Niece(19, "Niece"),
  Sister(20, "Sister"),
  SisterInLaw(21, "Sister-in-law"),
  Son(22, "Son"),
  SonInLaw(23, "Son-in-law"),
  Others(99, "Others");

  private String label;
  private Integer id;

  private RelationType(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, RelationType> lookup = new HashMap<>();

  static {
    for(RelationType c : EnumSet.allOf(RelationType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static RelationType get(final Integer pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public Integer getId() {
    return this.id;
  }
}
