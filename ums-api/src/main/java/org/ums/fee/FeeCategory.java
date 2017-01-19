package org.ums.fee;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface FeeCategory extends Serializable, EditType<MutableFeeCategory>, LastModifier,
    Identifier<String> {

  FeeCategory.Type getType();

  String getName();

  String getDescription();

  enum Type {
    PRIMARY(1),
    OTHERS(0);

    private static final Map<Integer, FeeCategory.Type> lookup = new HashMap<>();

    static {
      for(FeeCategory.Type c : EnumSet.allOf(FeeCategory.Type.class)) {
        lookup.put(c.getValue(), c);
      }
    }

    private Integer typeCode;

    Type(final Integer pStatusCode) {
      this.typeCode = pStatusCode;
    }

    public static FeeCategory.Type get(final Integer pStatusCode) {
      return lookup.get(pStatusCode);
    }

    public Integer getValue() {
      return this.typeCode;
    }
  }

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
    CERTIFICATE_DUPLICATE;
  }
}
