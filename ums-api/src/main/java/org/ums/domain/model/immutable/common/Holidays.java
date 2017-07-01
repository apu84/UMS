package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableHolidays;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public interface Holidays extends Serializable, LastModifier, EditType<MutableHolidays>, Identifier<Long> {

  static enum HolidayEnableStatus {
    ENABLED(1, "ENABLED"),
    DISABLED(0, "DISABLED");

    private String label;
    private int id;

    private HolidayEnableStatus(int id, String label) {
      this.id = id;
      this.label = label;
    }

    private static final Map<Integer, HolidayEnableStatus> lookup = new HashMap<>();

    static {
      for(HolidayEnableStatus c : EnumSet.allOf(HolidayEnableStatus.class)) {
        lookup.put(c.getId(), c);
      }
    }

    public static HolidayEnableStatus get(final int pId) {
      return lookup.get(pId);
    }

    public String getLabel() {
      return this.label;
    }

    public int getId() {
      return this.id;
    }

    public boolean getBoolValue() {
      if(this.id == 1)
        return true;
      else
        return false;
    }
  }

  HolidayType getHolidayType();

  int getYear();

  Date getFromDate();

  Date getToDate();

  HolidayEnableStatus getEnableStatus();
}
