package org.ums.fee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.math.BigDecimal;

public interface MutableUGFee extends UGFee, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setFeeCategoryId(String pFeeCategoryId);

  void setFeeCategory(FeeCategory pFeeCategory);

  void setSemesterId(Integer pSemesterId);

  void setSemester(Semester pSemester);

  void setFacultyId(Integer pFacultyId);

  void setFaculty(Faculty pFaculty);

  void setAmount(BigDecimal pAmount);
}
