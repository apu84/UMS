package org.ums.processor.userhome;

import org.apache.shiro.subject.Subject;
import org.ums.domain.model.immutable.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficialsHomeProcessor extends AbstractUserHomeProcessor {
  @Override
  public List<Map<String, String>> process(Subject pCurrentSubject) throws Exception {
    String userId = pCurrentSubject.getPrincipal().toString();
    User user = mUserManager.get(userId);

    List<Map<String, String>> profileContent = new ArrayList<>();

    Map<String, String> userName = new HashMap<>();
    userName.put("key", "Name");
    userName.put("value", user.getName());
    profileContent.add(userName);

    Map<String, String> department = new HashMap<>();
    department.put("key", "Department/ Office");
    department.put("value", user.getDepartment().getLongName());
    profileContent.add(department);

    return profileContent;
  }
}
