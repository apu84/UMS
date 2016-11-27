package org.ums.cachewarmer;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.ums.domain.model.common.LastModifier;

class CacheWarmer implements Serializable, LastModifier {
  enum State {
    IN_PROGRESS(1),
    WARMED(2),
    NONE(0);

    private static final Map<Integer, State> lookup = new HashMap<>();

    static {
      for(State c : EnumSet.allOf(State.class))
        lookup.put(c.getValue(), c);
    }

    private int typeCode;

    State(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static State get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }

  State mState;

  CacheWarmer(State pState) {
    mState = pState;
  }

  State getState() {
    return mState;
  }

  @Override
  public String getLastModified() {
    return null;
  }
}
