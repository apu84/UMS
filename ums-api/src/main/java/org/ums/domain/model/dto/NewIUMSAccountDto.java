package org.ums.domain.model.dto;

public class NewIUMSAccountDto {

  private String id;
  private String name;
  private String userId;
  private String password;

  public NewIUMSAccountDto(String id, String name, String userId, String password) {
    this.id = id;
    this.name = name;
    this.userId = userId;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
