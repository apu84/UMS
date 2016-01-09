package org.ums.domain.model.regular;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableStudent;

import java.io.Serializable;
import java.util.Date;

public interface Student extends Serializable, EditType<MutableStudent>, Identifier<String>, LastModifier {
  User getUser();

  String getFullName();

  Integer getDepartmentId();

  Department getDepartment() throws Exception;

  Integer getSemesterId();

  Semester getSemester() throws Exception;

  Integer getProgramId();

  Program getProgram() throws Exception;

  String getFatherName();

  String getMotherName();

  Date getDateOfBirth();

  String getGender();

  String getPresentAddress();

  String getPermanentAddress();

  String getMobileNo();

  String getPhoneNo();

  String getBloodGroup();

  String getEmail();

  String getGuardianName();

  String getGuardianMobileNo();

  String getGuardianPhoneNo();

  String getGuardianEmail();
}
