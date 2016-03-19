package org.ums.response.type;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface GenericResponse<T> extends Serializable {

  ResponseType getResponseType();

  void setResponseType(ResponseType pResponseType);

  String getMessage();

  void setMessage(final String pMessage);

  T getResponse();

  void setResponse(T pResponse);

  enum ResponseType {
    SUCCESS(1),
    ERROR(0),
    INFO(2),
    WARN(3);

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
