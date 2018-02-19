package org.ums.usermanagement.userView;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Department;
import org.ums.usermanagement.role.Role;

import java.io.Serializable;
import java.util.Date;

public interface UserView extends Serializable, EditType<MutableUserView>, Identifier<String>, LastModifier {

  String getUserName();

  String getLoginId();

  String getGender();

  Date getDateOfBirth();

  String getBloodGroup();

  String getFatherName();

  String getMotherName();

  String getMobileNumber();

  String getEmailAddress();

  String getDepartment();

  int getDesignation();

  int getRoleId();

  int getStatus();
}
