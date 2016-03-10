package org.ums.common.response.type;

import org.ums.response.type.GenericResponse;

import javax.json.JsonObject;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageResponse implements GenericResponse<JsonObject> {
  private ResponseType mResponseType;
  private String mMessage;
  private JsonObject mJsonObject;

  public MessageResponse(final ResponseType pResponseType,
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
  public JsonObject getResponse() {
    return mJsonObject;
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
  public void setResponse(JsonObject pResponse) {
    mJsonObject = pResponse;
  }
}
