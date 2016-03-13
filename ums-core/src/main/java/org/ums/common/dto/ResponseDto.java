package org.ums.common.dto;

import com.google.gson.Gson;

public class ResponseDto {
  private String code;
  private String message;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
