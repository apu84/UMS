package org.ums.processor.userhome;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserInfo {
  private List<Map<String, String>> infoList;
  private String userRole;

  public List<Map<String, String>> getInfoList() {
    return infoList;
  }

  public void setInfoList(List<Map<String, String>> infoList) {
    this.infoList = infoList;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "UserInfo{" + "infoList=" + infoList + ", userRole='" + userRole + '\'' + '}';
  }
}
