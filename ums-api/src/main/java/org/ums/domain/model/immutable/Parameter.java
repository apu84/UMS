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
public interface Parameter extends Serializable, LastModifier, EditType<MutableParameter>, Identifier<String> {
  String getParameter();

  String getShortDescription();

  String getLongDescription();

  int getType();

  enum ParameterName {
    ORIENTATION("1"),
    CLASS_BF_MIDTERM("2"),
    MIDTERM_BREAK("3"),
    CLASS_AFTER_MIDTERM("4"),
    FAREWELL("5"),
    PL("6"),
    EXAM_RESULT("7"),
    CCI("8"),
    APPLICATION_SEMESTER_WITHDRAW("9"),
    APPLICATION_CCI("10"),
    APPLICATION_READMISSION("11"),
    REGUALR_ADMISSION("12"),
    REGULAR_FIRST_INSTALLMENT("13"),
    REGULAR_SECOND_INSTALLMENT("14"),
    READMISSION("15"),
    READMISSION_FIRST_INSTALLMENT("16"),
    READMISSION_SECOND_INSTALLMENT("17"),
    NONE("0");

    private String id;

    ParameterName(String id) {
      this.id = id;
    }

    private static final Map<String, ParameterName> lookup = new HashMap<>();

    static {
      for(ParameterName c : EnumSet.allOf(ParameterName.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static ParameterName get(final String pId) {
      return lookup.get(pId);
    }

    public String getId() {
      return this.id;
    }
  }
}
