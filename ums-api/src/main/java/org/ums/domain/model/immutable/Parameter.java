package org.ums.domain.model.immutable;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableParameter;

/**
 * Created by My Pc on 3/13/2016.
 */
public interface Parameter extends Serializable, LastModifier, EditType<MutableParameter>, Identifier<Long> {
  String getParameter();

  String getShortDescription();

  String getLongDescription();

  int getType();

  enum ParameterName {
    ORIENTATION(1L, "orientation"),
    CLASS_BF_MIDTERM(2L, "cls_bf_mid"),
    MIDTERM_BREAK(3L, "midterm_break"),
    CLASS_AFTER_MIDTERM(4L, "cls_af_mid"),
    FAREWELL(5L, "farewell"),
    PL(6L, "pre_leave"),
    EXAM_RESULT(7L, "ex_res"),
    CCI(8L, "clr_imp_co"),
    APPLICATION_SEMESTER_WITHDRAW(9L, "application_semester_withdraw"),
    APPLICATION_CCI(10L, "application_cci"),
    APPLICATION_READMISSION(11L, "application_readmission"),
    REGUALR_ADMISSION(12L, "semester_admission"),
    REGULAR_FIRST_INSTALLMENT(13L, "first_installment_for_regular_admission"),
    REGULAR_SECOND_INSTALLMENT(14L, "second_installment_for_regular_admission"),
    READMISSION(15L, "readmission"),
    READMISSION_FIRST_INSTALLMENT(16L, "first_installment_for_readmission"),
    READMISSION_SECOND_INSTALLMENT(17L, "second_installment_for_readmission"),
    NONE(0L, "");

    private String label;
    private Long id;

    ParameterName(long id, String label) {
      this.id = id;
      this.label = label;
    }

    private static final Map<Long, ParameterName> lookup = new HashMap<>();

    static {
      for(ParameterName c : EnumSet.allOf(ParameterName.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static ParameterName get(final int pId) {
      return lookup.get(pId);
    }

    public String getLabel() {
      return this.label;
    }

    public Long getId() {
      return this.id;
    }

  }
}
