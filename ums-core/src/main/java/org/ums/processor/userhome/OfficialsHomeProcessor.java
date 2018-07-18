package org.ums.processor.userhome;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.manager.EmployeeManager;
import org.ums.usermanagement.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficialsHomeProcessor extends AbstractUserHomeProcessor {
  @Autowired
  EmployeeManager employeeManager;

  @Override
  public UserInfo process(Subject pCurrentSubject) {
    String userId = pCurrentSubject.getPrincipal().toString();
    User user = mUserManager.get(userId);
    String deptOffice;
    String designationName;

    UserInfo userInfo = new UserInfo();
    List<Map<String, String>> profileContent = new ArrayList<>();

    Map<String, String> userName = new HashMap<>();
    userName.put("key", "Name");
    userName.put("value", user.getName());
    profileContent.add(userName);

    if(user.getName().equals("Admin User")) {
      deptOffice = "ICT";
      designationName = "System Admin";
    }
    else {
      deptOffice = user.getDepartment().getLongName();
      designationName = employeeManager.get(user.getId()).getDesignation().getDesignationName();
    }
    Map<String, String> department = new HashMap<>();
    department.put("key", "Department/ Office");
    department.put("value", deptOffice);
    profileContent.add(department);

    Map<String, String> designation = new HashMap<>();
    designation.put("key", "Designation");
    designation.put("value", designationName);
    profileContent.add(designation);

    Map<String, String> employeeId = new HashMap<>();
    employeeId.put("key", "Employee ID");
    employeeId.put("value", user.getId());
    profileContent.add(employeeId);

    userInfo.setInfoList(profileContent);
    userInfo.setUserRole(user.getPrimaryRole().getName());

    return userInfo;
  }
}
