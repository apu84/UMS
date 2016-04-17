package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableEmployee;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


public interface Employee extends Serializable,LastModifier,EditType<MutableEmployee>,Identifier<String>{
  String getEmployeeName();
  int getDesignation();
  String getEmploymentType();
  Department getDepartment() throws Exception;
  String getFatherName();
  String getMotherName();
  String getBirthDate();
  char getGender();
  String getBloodGroup();
  String getPresentAddress();
  String getPermanentAddress();
  String getMobileNumber();
  String getPhoneNumber();
  String getEmailAddress();
  String getJoiningDate();
  String getJobPermanentDate();
  int getStatus();
}
