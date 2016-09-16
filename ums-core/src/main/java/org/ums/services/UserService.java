package org.ums.services;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserService {

  public String getUser() {
    String userId = "";
    Subject subject = SecurityUtils.getSubject();
    if (subject != null)
      userId = subject.getPrincipal().toString();
    return userId;
  }

}
