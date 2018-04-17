package org.ums.domain.model.dto;

public class NewIUMSAccountDto {
  private String name;
  private String userId;
  private String password;

  public NewIUMSAccountDto(String name, String userId, String password) {
    this.name = name;
    this.userId = userId;
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword() {
    this.password = password;
  }
}
