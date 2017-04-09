package org.ums.domain.model.immutable.registrar.Employee;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.registrar.Employee.MutablePersonalInformation;

import java.io.Serializable;
import java.util.Date;

public interface PersonalInformation extends Serializable, EditType<MutablePersonalInformation>, Identifier<Integer>,
    LastModifier {

  int getEmployeeId();

  String getFirstName();

  String getLastName();

  String getGender();

  String getBloodGroup();

  String getFatherName();

  String getMotherName();

  String getNationality();

  String getReligion();

  String getDateOfBirth();

  int getNationalId();

  int getMaritalStatus();

  String getSpouseName();

  int getSpouseNationalId();

  String getWebsite();

  String getOrganizationalEmail();

  String getPersonalEmail();

  String getMobileNumber();

  String getPhoneNumber();

  String getPresentAddressHouse();

  String getPresentAddressRoad();

  String getPresentAddressThana();

  String getPresentAddressDistrict();

  String getPresentAddressZip();

  String getPresentAddressDivision();

  String getPresentAddressCountry();

  String getPermanentAddressHouse();

  String getPermanentAddressRoad();

  String getPermanentAddressThana();

  String getPermanentAddressDistrict();

  String getPermanentAddressZip();

  String getPermanentAddressDivision();

  String getPermanentAddressCountry();

  String getEmergencyContactName();

  String getEmergencyContactRelation();

  String getEmergencyContactPhone();

  String getEmergencyContactAddress();

}
