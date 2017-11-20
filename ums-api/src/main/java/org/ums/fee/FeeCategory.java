package org.ums.fee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface FeeCategory extends Serializable, EditType<MutableFeeCategory>, LastModifier, Identifier<String> {

  String getFeeId();

  FeeType getType();

  Integer getFeeTypeId();

  String getName();

  String getDescription();

  String getDependencies();

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
    DROP_PENALTY,
    CERTIFICATE_CHARACTER,
    CERTIFICATE_MIGRATION,
    CERTIFICATE_STUDENTSHIP,
    CERTIFICATE_LANGUAGE_PROFICIENCY,
    TESTIMONIAL_DEPARTMENT;
  }
}
