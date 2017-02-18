package org.ums.fee;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.immutable.Semester;

public interface Fee extends Serializable, EditType<MutableFee>, LastModifier, Identifier<Long> {
  String getFeeCategoryId();

  FeeCategory getFeeCategory();

  Integer getSemesterId();

  Semester getSemester();

  Integer getFacultyId();

  Faculty getFaculty();

  Integer getProgramTypeId();

  ProgramType getProgramType();

  ProgramCategory getProgramCategory();

  Double getAmount();

  enum ProgramCategory {
    REGULAR(0),
    EVENING(1);

    private static final Map<Integer, Fee.ProgramCategory> lookup = new HashMap<>();

    static {
      for(Fee.ProgramCategory c : EnumSet.allOf(Fee.ProgramCategory.class)) {
        lookup.put(c.getValue(), c);
      }
    }

    private Integer categoryCode;

    ProgramCategory(final Integer pCategoryCode) {
      this.categoryCode = pCategoryCode;
    }

    public static Fee.ProgramCategory get(final Integer pCategoryCode) {
      return lookup.get(pCategoryCode);
    }

    public Integer getValue() {
      return this.categoryCode;
    }
  }
}
