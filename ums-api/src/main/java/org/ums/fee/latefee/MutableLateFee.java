package org.ums.fee.latefee;

import java.math.BigDecimal;
import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableLateFee extends LateFee, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setFrom(Date pFrom);

  void setTo(Date pTo);

  void setFee(BigDecimal pFee);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setAdmissionType(AdmissionType pAdmissionType);
}
