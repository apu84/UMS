package org.ums.manager;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface BinaryContentManager<T> {

  T get(final String pId);

  void put(T pData, String pId) throws Exception;

  void delete(String pId) throws Exception;

  String create(T pData, String pIdentifier) throws Exception;

  enum Domain {
    PICTURE(1);

    private static final Map<Integer, Domain> lookup
        = new HashMap<>();

    static {
      for (Domain c : EnumSet.allOf(Domain.class))
        lookup.put(c.getValue(), c);
    }

    private int typeCode;

    private Domain(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static Domain get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }
}
