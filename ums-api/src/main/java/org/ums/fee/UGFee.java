package org.ums.fee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.immutable.Semester;

public interface UGFee extends Serializable, EditType<MutableUGFee>, LastModifier, Identifier<Long> {
  String getFeeCategoryId();

  FeeCategory getFeeCategory();

  Integer getSemesterId();

  Semester getSemester();

  Integer getFacultyId();

  Faculty getFaculty();

  BigDecimal getAmount();
}
