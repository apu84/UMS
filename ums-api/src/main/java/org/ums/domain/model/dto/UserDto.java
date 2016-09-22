package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by Ifti on 22-Sep-16.
 */
public class UserDto {

  private String userId;
  private String roleName;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
