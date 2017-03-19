package org.ums.fee.latefee;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableUGLateFee extends UGLateFee, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setFrom(Date pFrom);

  void setTo(Date pTo);

  void setFee(Integer pFee);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);
}
