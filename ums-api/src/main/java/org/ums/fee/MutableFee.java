package org.ums.fee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableFee extends Fee, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setFeeCategoryId(String pFeeCategoryId);

  void setFeeCategory(FeeCategory pFeeCategory);

  void setSemesterId(Integer pSemesterId);

  void setSemester(Semester pSemester);

  void setFacultyId(Integer pFacultyId);

  void setFaculty(Faculty pFaculty);

  void setProgramTypeId(Integer pProgramTypeId);

  void setProgramType(ProgramType pProgramType);

  void setProgramCategory(ProgramCategory pProgramCategory);

  void setAmount(Double pAmount);
}
