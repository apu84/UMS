package org.ums.dummy.shared.model;

public class UserRole {
  private static final long serialVersionUID = 8432414340180447723L;

  public Integer getUseId() {
    return useId;
  }

  public void setUseId(Integer useId) {
    this.useId = useId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private Integer useId;

  private String roleName;

  private String email;

}
