package org.ums.enums.tes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 4/25/2018.
 */
public enum ObservationType {
  CLASSROOM_OBSERVATION(1),
  NON_CLASSROOM_OBSERVATION(2),
  NON_TEACHING_OBSERVATION(3);

  private static final Map<Integer, ObservationType> lookup = new HashMap<>();

  static {
    for(ObservationType c : EnumSet.allOf(ObservationType.class))
      lookup.put(c.getValue(), c);
  }

  private Integer typeCode;

  private ObservationType(Integer pTypeCode) {
    this.typeCode = pTypeCode;
  }

  public static ObservationType get(final int pTypeCode) {
    return lookup.get(pTypeCode);
  }

  public Integer getValue() {
    return this.typeCode;
  }
}
