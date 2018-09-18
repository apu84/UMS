package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;
import org.ums.usermanagement.userView.MutableUserView;
import org.ums.usermanagement.userView.UserView;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;

@Component
public class UserViewBuilder implements Builder<UserView, MutableUserView> {

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  DesignationManager mDesignationManager;

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, UserView pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("userId", pReadOnly.getId());
    pBuilder.add("userName", pReadOnly.getUserName());
    pBuilder.add("gender", pReadOnly.getGender());
    pBuilder.add("dateOfBirth",
        pReadOnly.getDateOfBirth() == null ? "" : mDateFormat.format(pReadOnly.getDateOfBirth()));
    pBuilder.add("bloodGroup", pReadOnly.getBloodGroup() == null ? "" : pReadOnly.getBloodGroup());
    pBuilder.add("fatherName", pReadOnly.getFatherName() == null ? "" : pReadOnly.getFatherName());
    pBuilder.add("motherName", pReadOnly.getMotherName() == null ? "" : pReadOnly.getMotherName());
    pBuilder.add("mobileNumber", pReadOnly.getMobileNumber() == null ? "" : pReadOnly.getMobileNumber());
    pBuilder.add("emailAddress", pReadOnly.getEmailAddress() == null ? "" : pReadOnly.getEmailAddress());
    pBuilder.add("department", mDepartmentManager.get(pReadOnly.getDepartment()).getLongName());
    pBuilder
        .add("designation", pReadOnly.getDesignation() == 0 ? "" : mDesignationManager.get(pReadOnly.getDesignation())
            .getDesignationName());
    pBuilder.add("category", pReadOnly.getRoleId() == 11 ? "Student" : pReadOnly.getRoleId() == 21 ? "Teacher"
        : "Management");
  }

  @Override
  public void build(MutableUserView pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
