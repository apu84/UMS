package org.ums.response.type;

import java.util.Map;

public class GenericMessageResponse implements GenericResponse<Map> {
  private ResponseType mResponseType;
  private String mMessage;
  private Map mMap;

  public GenericMessageResponse(final ResponseType pResponseType) {
    this(pResponseType, pResponseType.toString());
  }

  public GenericMessageResponse(final ResponseType pResponseType,
                                final String pMessage) {
    mResponseType = pResponseType;
    mMessage = pMessage;
  }

  @Override
  public ResponseType getResponseType() {
    return mResponseType;
  }

  @Override
  public String getMessage() {
    return mMessage;
  }

  @Override
  public Map getResponse() {
    return mMap;
  }

  @Override
  public void setResponseType(ResponseType pResponseType) {
    mResponseType = pResponseType;
  }

  @Override
  public void setMessage(String pMessage) {
    mMessage = pMessage;
  }

  @Override
  public void setResponse(Map pResponse) {
    mMap = pResponse;
  }
}
