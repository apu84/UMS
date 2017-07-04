package org.ums.domain.model.immutable.registrar;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.common.*;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;

import java.io.Serializable;
import java.util.Date;

public interface PersonalInformation extends Serializable, EditType<MutablePersonalInformation>, Identifier<String>,
    LastModifier {

  String getFirstName();

  String getLastName();

  String getGender();

  BloodGroup getBloodGroup();

  String getFatherName();

  String getMotherName();

  Nationality getNationality();

  Religion getReligion();

  Date getDateOfBirth();

  Integer getNationalId();

  MaritalStatus getMaritalStatus();

  String getSpouseName();

  Integer getSpouseNationalId();

  String getWebsite();

  String getOrganizationalEmail();

  String getPersonalEmail();

  Integer getMobileNumber();

  Integer getPhoneNumber();

  String getPresentAddressHouse();

  String getPresentAddressRoad();

  Country getPresentAddressCountry();

  Division getPresentAddressDivision();

  District getPresentAddressDistrict();

  Thana getPresentAddressThana();

  Integer getPresentAddressZip();

  String getPermanentAddressHouse();

  String getPermanentAddressRoad();

  Country getPermanentAddressCountry();

  Division getPermanentAddressDivision();

  District getPermanentAddressDistrict();

  Thana getPermanentAddressThana();

  Integer getPermanentAddressZip();

  String getEmergencyContactName();

  RelationType getEmergencyContactRelation();

  Integer getEmergencyContactPhone();

  String getEmergencyContactAddress();
}
