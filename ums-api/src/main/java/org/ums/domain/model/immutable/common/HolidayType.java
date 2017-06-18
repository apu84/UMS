package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableHolidayType;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public interface HolidayType extends Serializable, LastModifier, EditType<MutableHolidayType>, Identifier<Long> {
  static enum SubjectToMoon {
    No(0, "NO"),
    Yes(1, "YES");

    private String label;
    private int id;

    private SubjectToMoon(int id, String label) {
      this.id = id;
      this.label = label;
    }

    private static final Map<Integer, SubjectToMoon> lookup = new HashMap<>();

    static {
      for(SubjectToMoon c : EnumSet.allOf(SubjectToMoon.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static SubjectToMoon get(final int pId) {
      return lookup.get(pId);
    }

    public String getLabel() {
      return this.label;
    }

    public int getId() {
      return this.id;
    }
  }

  String getHolidayName();

  SubjectToMoon getMoonDependency();
}
