package org.ums.dummy.shared.model;

public class RolesPermission {
  private static final long serialVersionUID = -2070357513686679164L;

  private Integer userId;

  private String permission;
  private String roleName;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
}
