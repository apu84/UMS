package org.ums.response.type;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface GenericResponse<T> extends Serializable {

  ResponseType getResponseType();

  String getMessage();

  T getResponse();

  enum ResponseType {
    SUCCESSFUL(1),
    FAILED(0),
    INVALID_PARAMETER(2);

    private static final Map<Integer, ResponseType> lookup = new HashMap<>();

    static {
      for (ResponseType responseType : EnumSet.allOf(ResponseType.class)) {
        lookup.put(responseType.getValue(), responseType);
      }
    }

    private int typeCode;

    ResponseType(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static ResponseType get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }
}
