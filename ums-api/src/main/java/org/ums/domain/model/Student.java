package org.ums.domain.model;

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
