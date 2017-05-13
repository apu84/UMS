package org.ums.fee;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface FeeCategory extends Serializable, EditType<MutableFeeCategory>, LastModifier, Identifier<String> {

  String getFeeId();

  FeeType getType();

  Integer getFeeTypeId();

  String getName();

  String getDescription();

  enum Categories {
    ADMISSION,
    REGISTRATION,
    ESTABLISHMENT,
    TUITION,
    LABORATORY,
    READMISSION,
    IMPROVEMENT,
    CARRY,
    THEORY_REPEATER,
    SESSIONAL_REPEATER,
    THEORY,
    SESSIONAL,
    INSTALLMENT_CHARGE,
    GRADESHEET_PROVISIONAL,
    GRADESHEET_DUPLICATE,
    PROVISIONAL_CERTIFICATE_INITIAL,
    PROVISIONAL_CERTIFICATE_DUPLICATE,
    TRANSCRIPT_INITIAL,
    TRANSCRIPT_DUPLICATE,
    CERTIFICATE_CONVOCATION,
    CERTIFICATE_DUPLICATE,
    LATE_FEE,
    DROP_PENALTY;
  }
}
